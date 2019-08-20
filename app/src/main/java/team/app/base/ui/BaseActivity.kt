package team.app.base.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.*
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity<ViewBinding : ViewDataBinding, VM : ViewModel>
    : AppCompatActivity(), KoinComponent, CoroutineScope {

    abstract val viewModel: VM

    @LayoutRes
    abstract fun getLayoutId(): Int

    open val binding: ViewBinding by lazy {
        DataBindingUtil.setContentView<ViewBinding>(this, getLayoutId())
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.WHITE

        if (!isTaskRoot && intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intent.action == Intent.ACTION_MAIN) {
            finish()
            return
        }

        binding.lifecycleOwner = this
        setBindingLayout()
        onViewReady()
        onObserverListener()
    }

    /**
     * Set variable binding here
     */
    abstract fun setBindingLayout()

    /**
     * Handle view like: setOnClick, setAdapter, layoutManager, show, hide view....
     */
    abstract fun onViewReady()

    /**
     * Register listener observer liveData here
     */
    abstract fun onObserverListener()

    override fun onBackPressed() {
        when (supportFragmentManager.backStackEntryCount > 1) {
            true -> {
                val currentVisibleFragment =
                    supportFragmentManager.fragments.last() as BaseFragment<*, *>
                val onBackPressFragment = currentVisibleFragment.onBackPressed()
                if (onBackPressFragment)
                    supportFragmentManager.popBackStack()  /*super.onBackPressed()*/
            }
            false -> finish() /*Show dialog quit app*/
        }
    }

    fun popBackStack(count: Int) {
        while (supportFragmentManager.backStackEntryCount > count) {
            supportFragmentManager.popBackStackImmediate()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
        clearFindViewByIdCache()
    }

    companion object {
        fun <T : AppCompatActivity> T.startActivityWithIntent(
            intentBuilder: Intent.() -> Unit
        ): T = this.apply {
            startActivity(Intent().apply(intentBuilder))
        }
    }
}