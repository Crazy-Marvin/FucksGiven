package rocks.poopjournal.fucksgiven.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {

        // 1️⃣ Rename old table
        database.execSQL("""
            ALTER TABLE fucksTable RENAME TO fucksTable_old
        """)

        // 2️⃣ Create new table with TEXT date
        database.execSQL("""
            CREATE TABLE fucksTable (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                description TEXT NOT NULL,
                date TEXT NOT NULL
            )
        """)

        // 3️⃣ Convert millis → LocalDate (UTC safe)
        val cursor = database.query("SELECT id, description, date FROM fucksTable_old")

        val zone = ZoneId.systemDefault()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val description = cursor.getString(1)
            val dateValue = cursor.getString(2)

            val localDate = try {
                // If it's already yyyy-MM-dd
                LocalDate.parse(dateValue)
            } catch (e: Exception) {
                // Otherwise assume millis
                Instant.ofEpochMilli(dateValue.toLong())
                    .atZone(zone)
                    .toLocalDate()
            }

            database.execSQL(
                "INSERT INTO fucksTable (id, description, date) VALUES (?, ?, ?)",
                arrayOf(id, description, localDate)
            )
        }
        cursor.close()

        // 4️⃣ Drop old table
        database.execSQL("DROP TABLE fucksTable_old")
    }
}
