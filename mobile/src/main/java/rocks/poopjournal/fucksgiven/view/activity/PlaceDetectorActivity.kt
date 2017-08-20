package rocks.poopjournal.fucksgiven.view.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.PlaceLikelihoodBuffer
import com.google.android.gms.location.places.Places
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.helper.PermissionChecker
import timber.log.Timber

abstract class PlaceDetectorActivity : ApiClientActivity() {
    protected val LOCATION_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    protected lateinit var permissionChecker: PermissionChecker
    private var locationRequest: LocationRequest? = null
    private var placeDetectionResultCallback: ResultCallback<PlaceLikelihoodBuffer>? = null
    private var locationSettingsResultCallback: ResultCallback<LocationSettingsResult>? = null

    override fun init() {
        super.init()
        permissionChecker = PermissionChecker.create()
    }


    override fun dispose() {
        super.dispose()
        locationSettingsResultCallback = null
        placeDetectionResultCallback = null
    }

    override fun addApis(builder: GoogleApiClient.Builder) {
        builder.addApi(LocationServices.API)
        builder.addApi(Places.GEO_DATA_API)
        builder.addApi(Places.PLACE_DETECTION_API)
    }

    override fun onConnected(bundle: Bundle?) {
        Timber.d("onConnected() called with: bundle = [$bundle]")

        val permissionsIfNotGranted = permissionChecker.askPermissionsIfNotGranted(this, REQUEST_PERMISSION_LOCATION, *LOCATION_PERMISSIONS)
        if (!permissionsIfNotGranted) return
        whenAliClientConnected()
    }

    private fun whenAliClientConnected() {
        createLocationRequest()
        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
        val result = LocationServices.SettingsApi.checkLocationSettings(apiClient(),
                builder.build())
        locationSettingsResultCallback = ResultCallback<LocationSettingsResult> { result ->
            val status = result.status
            onLocationSettingsResult(status)
        }
        result.setResultCallback(locationSettingsResultCallback!!)
    }

    private fun onLocationSettingsResult(status: Status) {
        when (status.statusCode) {
            LocationSettingsStatusCodes.SUCCESS -> fetchCurrentPlace()
            LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                status.startResolutionForResult(
                        this,
                        REQUEST_CHECK_SETTINGS
                )
            } catch (e: IntentSender.SendIntentException) {
                e.printStackTrace()
                showMessage(getString(R.string.unable_to_turn_on_location))
            }

            LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> showMessage(getString(R.string.unable_to_turn_on_location))
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> when (requestCode) {
                REQUEST_CHECK_SETTINGS -> fetchCurrentPlace()
            }
            Activity.RESULT_CANCELED -> showMessage(getString(R.string.unable_to_turn_on_location))
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION_LOCATION -> {
                val granted = permissionChecker.checkGrantResults(grantResults)
                if (granted) {
                    whenAliClientConnected()
                }
            }
        }
    }


    protected fun createLocationRequest() {
        if (locationRequest == null) {
            locationRequest = LocationRequest()
        }
        locationRequest!!.interval = DEFAULT_UPDATE_INTERVAL
        locationRequest!!.fastestInterval = DEFAULT_UPDATE_INTERVAL / 2
        locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    protected fun fetchCurrentPlace() {
        Timber.d("fetchCurrentPlace() called")

        if (!apiClient()!!.isConnected) return
        val result = Places.PlaceDetectionApi.getCurrentPlace(apiClient(), null)
        placeDetectionResultCallback = ResultCallback<PlaceLikelihoodBuffer> { placeLikelihoodBuffer ->
            onPlaceDetected(placeLikelihoodBuffer)
            placeLikelihoodBuffer.release()
        }
        result.setResultCallback(placeDetectionResultCallback!!)
    }

    private fun onPlaceDetected(placeLikelihoodBuffer: PlaceLikelihoodBuffer) {
        Timber.d("onPlaceDetected() called with: placeLikelihoodBuffer = [$placeLikelihoodBuffer]")
        if (placeLikelihoodBuffer.count == 0) return
        val placeLikelihood = placeLikelihoodBuffer.get(0).place
        onPlaceUpdated(placeLikelihood)
    }

    protected abstract fun onPlaceUpdated(place: Place)

    companion object {

        val REQUEST_CHECK_SETTINGS = 222
        val REQUEST_PERMISSION_LOCATION = 901
        private val DEFAULT_UPDATE_INTERVAL = (30 * 1000).toLong()
    }

}
