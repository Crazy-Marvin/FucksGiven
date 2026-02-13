package rocks.poopjournal.fucksgiven.util

import java.time.*
import java.time.format.DateTimeFormatter

object DateUtils {


    // Convert LocalDate → UTC epoch millis (for DatePicker)
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
}
