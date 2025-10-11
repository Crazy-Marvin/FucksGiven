package rocks.poopjournal.fucksgiven.presentation.ui.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

val dateComponentFormatter = DateTimeComponents.Format {
    monthName(MonthNames.ENGLISH_FULL)
    char(' ')
    day(Padding.ZERO)
}

@OptIn(ExperimentalTime::class)
fun getFormattedDate(timestamp: Long): String {
    // DatePicker gives date in milliseconds since epoch UTC)
    val localDate = Instant.fromEpochMilliseconds(timestamp).toLocalDateTime(TimeZone.UTC).date

    return localDate.atStartOfDayIn(TimeZone.currentSystemDefault()).format(dateComponentFormatter)
}

fun isToday(dateString: String): Boolean {
    val todayString = getFormattedDate(System.currentTimeMillis())
    return dateString == todayString
}