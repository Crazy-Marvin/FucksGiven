package rocks.poopjournal.fucksgiven.util

/**
 * holds all constants used in app
 */

class Constants {

    object Delays {
        const val SPLASH_DELAY: Long = 2000
    }

    object BundleExtras {
        const val INDEX = "index"
    }

    object Preferences {
        const val WAS_APP_OPENED: String = "was_app_opened"
    }

    object DateFormats {
        const val COMMON_DATE_TIME_FORMAT = "dd/MMM/yyyy hh:mm"
        const val DAY_DATE_MONTH_FORMAT = "EEEE, dd MMM"
        const val DATE_MONTH_FORMAT = "dd MMM"
        const val HOUR_MIN_FORMAT = "hh:mm a"
        const val MONTH_YEAR_FORMAT = "MMM yyyy"
        const val ENTRY_DATE_FORMAT = "EEEE d, MMM"
    }
}
