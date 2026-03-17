package rocks.poopjournal.fucksgiven.presentation.ui.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


fun LocalDate.toUtcEpochMillis(): Long {
    return this
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant()
        .toEpochMilli()
}

// Convert UTC epoch millis → LocalDate (from DatePicker)
fun utcMillisToLocalDate(millis: Long): LocalDate {
    return Instant.ofEpochMilli(millis)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()
}

fun millisToLocalDate(millis: Long): LocalDate {
    return ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(millis),
        ZoneId.systemDefault()
    ).toLocalDate()
}

fun LocalDate.toEpochMillis(): Long {
    return this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun formatDate(date: LocalDate): String {
    return date.format(DateTimeFormatter.ofPattern("MMMM d"))
}

fun formateDateWithYear(date : LocalDate) : String {
    val now = LocalDate.now()
    return when {
        date.year == now.year -> date.format(DateTimeFormatter.ofPattern("MMMM d"))
        else -> date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))
    }
}

fun isToday(date: LocalDate): Boolean {
    return date == LocalDate.now()
}