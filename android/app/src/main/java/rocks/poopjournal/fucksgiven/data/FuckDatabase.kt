package rocks.poopjournal.fucksgiven.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FuckData::class], version = 1, exportSchema = false)
abstract class FuckDatabase : RoomDatabase() {
    abstract fun fuckDao(): FuckDao
}
