package rocks.poopjournal.fucksgiven.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FuckData::class], version = 2, exportSchema = true)
@TypeConverters(LocalDateConverter::class)
abstract class FuckDatabase : RoomDatabase() {
    abstract fun fuckDao(): FuckDao
}
