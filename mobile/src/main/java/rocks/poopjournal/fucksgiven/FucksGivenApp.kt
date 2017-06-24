package rocks.poopjournal.fucksgiven

import android.app.Application

import timber.log.Timber

/**
 * application class
 */
class FucksGivenApp : Application() {

    companion object {
        lateinit var instance: FucksGivenApp
            private set
    }

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

}
