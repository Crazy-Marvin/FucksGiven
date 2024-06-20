package rocks.poopjournal.fucksgiven.presentation.component

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import rocks.poopjournal.fucksgiven.presentation.viewmodel.StatsViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerView(
    pagerState: PagerState,
    viewModel: StatsViewModel,

    ) {
    val weeklyData by viewModel.weeklyData.observeAsState(emptyList())
    val monthlyData by viewModel.monthlyData.observeAsState(emptyList())
    val yearlyData by viewModel.yearlyData.observeAsState(emptyList())


    //weekly data processing
    val weeklyXValues = listOf("M", "T", "W", "TH", "F", "S", "SU")
    // Create a list of LineDataPoint objects
    val weeklyLineDataPoints = weeklyXValues.map { day ->
        val dayOfWeek = getDayOfWeek(day)
        val count = weeklyData.count { data ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = data.date
            calendar.get(Calendar.DAY_OF_WEEK) == dayOfWeek
        }
        LineDataPoint(day, count)
    }

    val total = weeklyLineDataPoints.sumOf { it.yValue }
    val weeklyAverage: Double = if (weeklyLineDataPoints.isNotEmpty()) {
        total.toDouble() / weeklyLineDataPoints.size
    } else {
        0.0
    }


    val calendar1 = Calendar.getInstance()
    calendar1.firstDayOfWeek = Calendar.MONDAY
    while (calendar1.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
        calendar1.add(Calendar.DATE, -1)
    }
    val weekStart = calendar1.time
    calendar1.add(Calendar.DATE, 6)
    val weekEnd = calendar1.time
    val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
    val weekStartStr = dateFormat.format(weekStart)
    val weekEndStr = dateFormat.format(weekEnd)
    val currentWeekRange = "$weekStartStr - $weekEndStr"

    //monthly data processing
    val monthlyXValues = listOf(
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "11",
        "12",
        "13",
        "14",
        "15",
        "16",
        "17",
        "18",
        "19",
        "20",
        "21",
        "22",
        "23",
        "24",
        "25",
        "26",
        "27",
        "28",
        "29",
        "30",
        "31"
    )
    val monthlyLineDataPoints = monthlyXValues.map { day ->
        val dayOfMonth = day.toInt()
        val count = monthlyData.count { data ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = data.date
            calendar.get(Calendar.DAY_OF_MONTH) == dayOfMonth
        }
        LineDataPoint(day, count)
    }
    val totalMonthly = monthlyLineDataPoints.sumOf { it.yValue }
    val monthlyAverage : Double = if (monthlyLineDataPoints.isNotEmpty()) totalMonthly.toDouble() / monthlyLineDataPoints.size else 0.0

    val currentMonth =
        Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
    Log.d("MonthlyData", "Monthly data: $monthlyLineDataPoints , $monthlyAverage")

    //yearly data processing
    val yearlyXValues = listOf("J", "F", "M", "A", "M", "J", "J", "A", "S", "O", "N", "D")
    val yearlyLineDataPoints = yearlyXValues.mapIndexed { index, month ->
        val monthOfYear = index + 1
        val count = yearlyData.count { data ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = data.date
            calendar.get(Calendar.MONTH) == monthOfYear - 1 // Calendar.MONTH is zero-based
        }
        LineDataPoint(month, count)
    }
    val totalYearly = yearlyLineDataPoints.sumOf { it.yValue }
    val yearlyAverage : Double =
        if (yearlyLineDataPoints.isNotEmpty()) totalYearly.toDouble() / yearlyLineDataPoints.size else 0.0


    HorizontalPager(
        state = pagerState,
        userScrollEnabled = false,
        modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ) { page ->
        when (page) {
            0 -> {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, bottom = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = when {
                                weeklyAverage == 0.0 -> "0"
                                weeklyAverage < 1.0 -> "<1"
                                else -> weeklyAverage.toString()
                            },
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 3.dp),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "fucks given on average",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Text(
                        text = "Week $currentWeekRange",
                        modifier = Modifier.padding(end = 3.dp, bottom = 5.dp, start = 10.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.W400
                    )

                    LineChartComposable(
                        lineDataPoints = weeklyLineDataPoints,
                        xCount = weeklyLineDataPoints.size
                    )
                }
            }

            1 -> {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, bottom = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = when {
                                monthlyAverage == 0.0 -> "0"
                                monthlyAverage < 1.0 -> "<1"
                                else -> monthlyAverage.toString()
                            },
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 3.dp),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "fucks given on average",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Text(
                        text = "$currentMonth 1-31",
                        modifier = Modifier.padding(end = 3.dp, bottom = 5.dp, start = 10.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    LineChartComposable(lineDataPoints = monthlyLineDataPoints, xCount = 6)
                }
            }

            2 -> {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, bottom = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text =when {
                                yearlyAverage == 0.0 -> "0"
                                yearlyAverage < 1.0 -> "<1"
                                else -> yearlyAverage.toString()
                            },
                            modifier = Modifier.padding(end = 3.dp),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "fucks given on average",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Text(
                        text = "January-December",
                        modifier = Modifier.padding(end = 3.dp, bottom = 5.dp, start = 10.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    LineChartComposable(
                        lineDataPoints = yearlyLineDataPoints,
                        xCount = yearlyLineDataPoints.size
                    )
                }
            }
        }
    }
}

fun getDayOfWeek(day: String): Int {
    return when (day) {
        "M" -> Calendar.MONDAY
        "T" -> Calendar.TUESDAY
        "W" -> Calendar.WEDNESDAY
        "TH" -> Calendar.THURSDAY
        "F" -> Calendar.FRIDAY
        "S" -> Calendar.SATURDAY
        "SU" -> Calendar.SUNDAY
        else -> throw IllegalArgumentException("Invalid day of the week")
    }
}


