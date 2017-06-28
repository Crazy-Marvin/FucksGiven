package rocks.poopjournal.fucksgiven.util

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import rocks.poopjournal.fucksgiven.FucksGivenApp
import rocks.poopjournal.fucksgiven.helper.formatDateTime
import java.util.*


object Utils {


    fun generateRandomId(): String {
        val uniqueId = UUID.randomUUID().toString()
        return uniqueId.replace("-".toRegex(), "")
    }

    val currentTime: String
        get() = Calendar.getInstance().formatDateTime(Constants.DateFormats.COMMON_DATE_TIME_FORMAT)


    val appVersionCode: Int
        get() {
            val packageInfo = appPackageInfo
            return packageInfo?.versionCode ?: 0
        }

    val appVersionName: String
        get() {
            val packageInfo = appPackageInfo
            return packageInfo?.versionName ?: ""
        }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable && activeNetworkInfo.isConnected
    }

    private val appPackageInfo: PackageInfo?
        get() {
            val context = FucksGivenApp.instance
            try {
                return context?.packageManager?.getPackageInfo(context.packageName, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                return null
            }

        }
}
