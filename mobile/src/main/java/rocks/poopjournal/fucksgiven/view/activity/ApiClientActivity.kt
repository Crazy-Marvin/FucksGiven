package rocks.poopjournal.fucksgiven.view.activity

import android.support.annotation.CallSuper

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient

import rocks.poopjournal.fucksgiven.R
import timber.log.Timber

abstract class ApiClientActivity : BaseActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private var googleApiClient: GoogleApiClient? = null

    @CallSuper
    override fun init() {
        if (googleApiClient == null) {
            val builder = GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
            addApis(builder)
            googleApiClient = builder.build()
        }
    }

    protected fun apiClient(): GoogleApiClient? {
        return googleApiClient
    }

    override fun onStart() {
        googleApiClient!!.connect()
        super.onStart()
    }

    override fun onStop() {
        googleApiClient!!.disconnect()
        super.onStop()
    }

    override fun onConnectionSuspended(cause: Int) {
        Timber.d("onConnectionSuspended() called with: cause = [$cause]")
        when (cause) {
            GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST -> showMessage(getString(R.string.connect_internet))
            GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED -> showMessage(getString(R.string.play_service_disconnected))
            else -> showMessage(getString(R.string.unknown_error))
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Timber.d("onConnectionFailed() called with: connectionResult = [$connectionResult]")
        showMessage(getString(R.string.play_service_failed))
    }

    protected abstract fun addApis(builder: GoogleApiClient.Builder)

    @CallSuper
    override fun dispose() {
        super.dispose()
        if (!googleApiClient!!.isConnected) return
        if (googleApiClient!!.isConnectionCallbacksRegistered(this)) {
            googleApiClient!!.unregisterConnectionCallbacks(this)
        }
        if (googleApiClient!!.isConnectionFailedListenerRegistered(this)) {
            googleApiClient!!.unregisterConnectionFailedListener(this)
        }
    }
}
