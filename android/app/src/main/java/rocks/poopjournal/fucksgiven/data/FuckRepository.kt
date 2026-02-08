package rocks.poopjournal.fucksgiven.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDate
import javax.inject.Inject

class FuckRepository @Inject constructor(
    private val fuckDao: FuckDao
) {
    fun getAllFucks(): Flow<List<FuckData>> = fuckDao.getAllData().flowOn(Dispatchers.IO)
    fun getFuck(id: Int): Flow<FuckData> = fuckDao.getData(id).flowOn(Dispatchers.IO).conflate()
    suspend fun insertFuck(fuckData: FuckData) = fuckDao.insert(fuckData)
    suspend fun updateFuck(fuckData: FuckData) = fuckDao.update(fuckData)
    suspend fun deleteFuck(fuckData: FuckData) = fuckDao.delete(fuckData)

    fun getWeeklyData(): LiveData<List<FuckData>> {
        val today = LocalDate.now()
        val startOfWeek = today.with(java.time.DayOfWeek.MONDAY)
        val endOfWeek = startOfWeek.plusDays(6)
        return fuckDao.getDataBetweenDates(startOfWeek, endOfWeek).map { it ?: emptyList() }
    }

    fun getMonthlyData(): LiveData<List<FuckData>> {
        val today = LocalDate.now()
        val startOfMonth = today.withDayOfMonth(1)
        val endOfMonth = today.withDayOfMonth(today.lengthOfMonth())
        return fuckDao.getDataBetweenDates(startOfMonth, endOfMonth)
    }

    fun getYearlyData(): LiveData<List<FuckData>> {
        val today = LocalDate.now()
        val startOfYear = today.withDayOfYear(1)
        val endOfYear = today.withDayOfYear(today.lengthOfYear())
        return fuckDao.getDataBetweenDates(startOfYear, endOfYear)
    }


}