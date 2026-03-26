package rocks.poopjournal.fucksgiven

import android.system.Os.close
import androidx.room.testing.MigrationTestHelper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rocks.poopjournal.fucksgiven.data.FuckDatabase
import rocks.poopjournal.fucksgiven.data.MIGRATION_1_2
import java.time.LocalDate
import java.time.ZoneOffset

@RunWith(AndroidJUnit4::class)
class MigrationTest {

    private val TEST_DB = "migration-test"

    @get:Rule
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        FuckDatabase::class.java
    )

    // ─── Test 1: old entry with millis date ───────────────────────────────────
    @Test
    fun migrate_oldEntry_millisDate() {
        // Create v1 database and insert a row with a millis timestamp
        val millis = LocalDate.of(2024, 1, 15)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()

        helper.createDatabase(TEST_DB, 1).apply {
            execSQL(
                "INSERT INTO fucksTable (description, date) VALUES (?, ?)",
                arrayOf("old entry with millis", millis.toString())
            )
            close()
        }

        // Run migration
        val db = helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2)

        // Verify the migrated row
        val cursor = db.query("SELECT description, date FROM fucksTable")
        assertTrue("Should have one row", cursor.moveToFirst())

        val description = cursor.getString(0)
        val date = cursor.getString(1)

        assertEquals("old entry with millis", description)
        assertEquals("2024-01-15", date)  // should be converted to yyyy-MM-dd

        cursor.close()
        db.close()
    }

    // ─── Test 2: entry already in yyyy-MM-dd format ───────────────────────────
    @Test
    fun migrate_entryAlreadyInNewFormat() {
        helper.createDatabase(TEST_DB, 1).apply {
            execSQL(
                "INSERT INTO fucksTable (description, date) VALUES (?, ?)",
                arrayOf("new format entry", "2024-06-20")
            )
            close()
        }

        val db = helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2)

        val cursor = db.query("SELECT description, date FROM fucksTable")
        assertTrue(cursor.moveToFirst())
        assertEquals("2024-06-20", cursor.getString(1))

        cursor.close()
        db.close()
    }

    // ─── Test 3: multiple entries mixed ───────────────────────────────────────
    @Test
    fun migrate_multipleEntries_mixed() {
        val millis1 = LocalDate.of(2023, 12, 1)
            .atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
        val millis2 = LocalDate.of(2024, 3, 7)
            .atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()

        helper.createDatabase(TEST_DB, 1).apply {
            execSQL("INSERT INTO fucksTable (description, date) VALUES (?, ?)",
                arrayOf("entry one", millis1.toString()))
            execSQL("INSERT INTO fucksTable (description, date) VALUES (?, ?)",
                arrayOf("entry two", millis2.toString()))
            execSQL("INSERT INTO fucksTable (description, date) VALUES (?, ?)",
                arrayOf("entry three", "2024-06-20"))
            close()
        }

        val db = helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2)

        val cursor = db.query("SELECT description, date FROM fucksTable ORDER BY id")
        assertEquals(3, cursor.count)

        cursor.moveToFirst(); assertEquals("2023-12-01", cursor.getString(1))
        cursor.moveToNext(); assertEquals("2024-03-07", cursor.getString(1))
        cursor.moveToNext(); assertEquals("2024-06-20", cursor.getString(1))

        cursor.close()
        db.close()
    }

    // ─── Test 4: empty database ───────────────────────────────────────────────
    @Test
    fun migrate_emptyDatabase() {
        helper.createDatabase(TEST_DB, 1).close()

        val db = helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2)
        val cursor = db.query("SELECT * FROM fucksTable")
        assertEquals(0, cursor.count)

        cursor.close()
        db.close()
    }
}