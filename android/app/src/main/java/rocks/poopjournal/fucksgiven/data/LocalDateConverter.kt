package rocks.poopjournal.fucksgiven.data

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class LocalDateConverter {

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? =
        date?.toString() // yyyy-MM-dd

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? {
        if (value.isNullOrBlank()) return null

        return try {
            // New format: yyyy-MM-dd
            LocalDate.parse(value)
        } catch (e: Exception) {
            try {
                // Old format: millis
                val millis = value.toLong()
                Instant.ofEpochMilli(millis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
            } catch (e2: Exception) {
                null
            }
        }
    }
}
