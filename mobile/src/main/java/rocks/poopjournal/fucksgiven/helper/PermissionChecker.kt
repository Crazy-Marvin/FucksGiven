package rocks.poopjournal.fucksgiven.helper

import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import java.util.*

/**
 * handles permission checking for marshmallow and above
 */
class PermissionChecker private constructor() {

    //avoiding direct instances. use factory method instead.
    companion object {
        fun create(): PermissionChecker {
            return PermissionChecker()
        }
    }

    /**
     * list of non granted permissions<br></br>
     * should be cleared every time checking new permissions
     */
    private val revokedPermissions = ArrayList<String>()

    /**
     * checks permission and asks if not granted

     * @param baseActivity       activity
     * @param requestCode        permission request code
     * @param permissionsToCheck permissions to check and ask
     * @return true if all asked permissionsToCheck are granted; false otherwise
     */
    fun askPermissionsIfNotGranted(baseActivity: AppCompatActivity, requestCode: Int, vararg permissionsToCheck: String): Boolean {
        revokedPermissions.clear()
        for (permission in permissionsToCheck) { //iterating through permission given in params
            if (ContextCompat.checkSelfPermission(baseActivity, permission) == android.support.v4.content.PermissionChecker.PERMISSION_GRANTED) { //if granted - skip iteration
                continue
            }
            revokedPermissions.add(permission) //otherwise add it to list
        }
        if (revokedPermissions.size > 0) { //request only non granted permissions
            ActivityCompat.requestPermissions(baseActivity, revokedPermissions.toTypedArray(), requestCode)
            return false
        } else {
            return true
        }
    }

    /**
     * checks permission and asks if not granted - same as its activity variant but intended for fragments
     * @param baseFragment       fragment
     * @param requestCode        permission request code
     * @param permissionsToCheck permissions to check and ask
     * @return true if all asked permissionsToCheck are granted; false otherwise
     */
    fun askPermissionsIfNotGranted(baseFragment: Fragment, requestCode: Int, vararg permissionsToCheck: String): Boolean {
        revokedPermissions.clear()
        for (permission in permissionsToCheck) { //iterating through permission given in params
            if (ContextCompat.checkSelfPermission(baseFragment.context, permission) == android.support.v4.content.PermissionChecker.PERMISSION_GRANTED) { //if granted - skip iteration
                continue
            }
            revokedPermissions.add(permission) //otherwise add it to list
        }
        if (revokedPermissions.size > 0) { //request only non granted permissions
            baseFragment.requestPermissions(revokedPermissions.toTypedArray(), requestCode)
            return false
        } else {
            return true
        }
    }

    /**
     * checks grant results from [ActivityCompat.OnRequestPermissionsResultCallback].
     * <br></br> Shows snake bar with rationale message if permission denied.
     * <br></br> Asks again if user allow on showing rationale
     * @param grantResults grant results from [ActivityCompat.OnRequestPermissionsResultCallback]
     * @return true if all permissions asked have been granted; false otherwise
     */
    fun checkGrantResults(grantResults: IntArray): Boolean {
        //initializing granted flag to false is mandatory since array of grant results can be empty
        var allPermissionGranted = false

        for (grantResult in grantResults) { //iterating through grant results
            if (grantResult != android.support.v4.content.PermissionChecker.PERMISSION_GRANTED) { //if granted - skip iteration
                allPermissionGranted = false //otherwise break loop and return false
                break
            } else {
                //if granted, don't forgot to dismiss any previous snake bar showing for rationale
                allPermissionGranted = true
            }
        }
        return allPermissionGranted
    }

}