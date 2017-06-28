package rocks.poopjournal.fucksgiven.util

/**
 * holds all constants used in app
 */

class Constants {

    object Delays {
        const val SPLASH_DELAY: Long = 2000
    }

    object Preferences {
        const val WAS_APP_OPENED: String = "was_app_opened"
    }

    object DateFormats {
        const val COMMON_DATE_TIME_FORMAT = "dd/MMM/yyyy hh:mm"
        const val DAY_DATE_MONTH_FORMAT = "EEEE, dd MMM"
        const val DATE_MONTH_FORMAT = "dd MMM"
        const val HOUR_MIN_FORMAT = "hh:mm a"
    }
}
