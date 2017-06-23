package rocks.poopjournal.fucksgiven

import android.app.Application

import timber.log.Timber

/**
 * Created by Experiments on 13-Mar-17.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Timber.e("onLowMemory: ")
    }

    companion object {
        var instance: App? = null
            private set
    }

}
