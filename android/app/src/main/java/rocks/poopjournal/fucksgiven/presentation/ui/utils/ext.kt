package rocks.poopjournal.fucksgiven.presentation.ui.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


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

fun isToday(date: LocalDate): Boolean {
    return date == LocalDate.now()
}