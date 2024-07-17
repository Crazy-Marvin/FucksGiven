package rocks.poopjournal.fucksgivenwatch.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import java.util.Calendar
import javax.inject.Inject

class FuckRepository @Inject constructor(
    private val fuckDao: FuckDao
) {
    fun getAllFucks() : Flow<List<FuckData>> = fuckDao.getAllData().flowOn(Dispatchers.IO).conflate()
    fun getFuck(id: Int) : Flow<FuckData> = fuckDao.getData(id).flowOn(Dispatchers.IO).conflate()
    suspend fun insertFuck(fuckData: FuckData) = fuckDao.insert(fuckData)

    fun getWeeklyData(): LiveData<List<FuckData>> {
        val calendar = Calendar.getInstance().apply {
            firstDayOfWeek = Calendar.MONDAY
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startOfWeek = calendar.timeInMillis
        calendar.add(Calendar.DAY_OF_WEEK, 7)
        val endOfWeek = calendar.timeInMillis

        return fuckDao.getDataBetweenDates(startOfWeek, endOfWeek)
    }

    fun getMonthlyData(): LiveData<List<FuckData>> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startOfMonth = calendar.timeInMillis
        calendar.add(Calendar.MONTH, 1)
        calendar.add(Calendar.DATE, -1)
        val endOfMonth = calendar.timeInMillis

        return fuckDao.getDataBetweenDates(startOfMonth, endOfMonth)
    }

    fun getYearlyData(): LiveData<List<FuckData>> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_YEAR, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startOfYear = calendar.timeInMillis
        calendar.add(Calendar.YEAR, 1)
        calendar.add(Calendar.DATE, -1)
        val endOfYear = calendar.timeInMillis
        return fuckDao.getDataBetweenDates(startOfYear, endOfYear)
    }

}