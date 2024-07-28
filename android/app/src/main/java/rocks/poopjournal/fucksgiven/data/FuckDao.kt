package rocks.poopjournal.fucksgiven.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import rocks.poopjournal.fucksgiven.presentation.ui.utils.THETABLE_TABLENAME

@Dao
interface FuckDao {
    @Query("SELECT * from $THETABLE_TABLENAME")
    fun getAllData(): Flow<List<FuckData>>

    @Query("SELECT * from $THETABLE_TABLENAME WHERE id = :id")
    fun getData(id: Int): Flow<FuckData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: FuckData)

    @Query("SELECT * FROM $THETABLE_TABLENAME WHERE date BETWEEN :startDate AND :endDate")
    fun getDataBetweenDates(startDate: Long, endDate: Long): LiveData<List<FuckData>>

}