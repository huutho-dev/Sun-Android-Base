package team.app.base

import androidx.multidex.MultiDexApplication
import com.squareup.leakcanary.LeakCanary
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import team.app.base.di.appModule
import team.app.base.di.netModule

class MyApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        if (!LeakCanary.isInAnalyzerProcess(this) && BuildConfig.DEBUG) LeakCanary.install(this)
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(listOf(appModule, netModule/*, add more module here*/))
        }
    }
}