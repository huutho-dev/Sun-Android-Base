package team.app.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.*
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment<ViewBinding : ViewDataBinding, VM : ViewModel>
    : Fragment(), KoinComponent, CoroutineScope {

    open lateinit var binding: ViewBinding

    open val fragmentVisibleLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBindingLayout()
        onViewReady(view)
        onObserverListener()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * Set variable binding here
     */
    abstract fun setBindingLayout()

    /**
     * Handle view like: setOnClick, setAdapter, layoutManager, show, hide view....
     */
    abstract fun onViewReady(view: View)

    /**
     * Register listener observer liveData here
     */
    abstract fun onObserverListener()

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        fragmentVisibleLiveData.value = view != null && isVisibleToUser && isAdded && !isHidden
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clearFindViewByIdCache()
    }

    override fun onDestroy() {
        coroutineContext.cancel()
        super.onDestroy()
    }

    open fun onBackPressed() = true

    companion object {
        fun <T : Fragment> T.withArgs(
            argsBuilder: Bundle.() -> Unit
        ): T = this.apply {
            arguments = Bundle().apply(argsBuilder)
        }
    }
}