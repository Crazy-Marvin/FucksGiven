package rocks.poopjournal.fucksgiven.presentation.ui.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getFormattedDate(timestamp: Long): String {
    val date = Date(timestamp)
    val sdf = SimpleDateFormat("MMMM dd", Locale.getDefault())
    return sdf.format(date)
}

fun isToday(dateString: String): Boolean {
    val todayString = getFormattedDate(System.currentTimeMillis())
    return dateString == todayString
}