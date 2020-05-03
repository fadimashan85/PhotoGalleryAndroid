package se.mobileinteraction.photogallery

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import se.mobileinteraction.photogallery.di.appModule
import timber.log.Timber

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}
