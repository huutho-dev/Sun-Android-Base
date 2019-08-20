package team.app.base.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import team.app.base.data.local.AppSharePreference
/*import team.app.base.data.local.AppDatabase*/

val appModule = module {
    single { androidApplication() } /*=> should write in main-module*/
    single { AppSharePreference(get(), get()) }
    /*single { AppDatabase.createDatabase(get()) }*/
}