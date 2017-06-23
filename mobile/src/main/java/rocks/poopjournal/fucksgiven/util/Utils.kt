package rocks.poopjournal.fucksgiven.util

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import rocks.poopjournal.fucksgiven.FucksGivenApp
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Fenil on 21-Nov-16.
 */

object Utils {

    val DATE_FORMAT = "dd/MMM/yyyy hh:mm"

    fun generateRandomId(): String {
        val uniqueId = UUID.randomUUID().toString()
        return uniqueId.replace("-".toRegex(), "")
    }

    val currentTime: String
        get() {
            val mills = System.currentTimeMillis()
            val date = Date(mills)
            val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
            return simpleDateFormat.format(date)
        }


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
        return activeNetworkInfo.isAvailable && activeNetworkInfo.isConnected
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
