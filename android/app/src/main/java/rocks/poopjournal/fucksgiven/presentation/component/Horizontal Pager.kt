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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.presentation.ui.utils.ThemeSetting
import rocks.poopjournal.fucksgiven.presentation.viewmodel.StatsViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerView(
    pagerState: PagerState,
    viewModel: StatsViewModel,
    themeSetting: ThemeSetting

    ) {
    val weeklyData by viewModel.weeklyData.observeAsState(emptyList())
    val monthlyData by viewModel.monthlyData.observeAsState(emptyList())
    val yearlyData by viewModel.yearlyData.observeAsState(emptyList())


    //weekly data processing
    val weeklyXValues = listOf(
        stringResource(id = R.string.m),
        stringResource(id = R.string.t),
        stringResource(id = R.string.w),
        stringResource(id = R.string.th),
        stringResource(id = R.string.f),
        stringResource(id = R.string.s),
        stringResource(id = R.string.su)
    )
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
        stringResource(id = R.string.one),
        stringResource(id = R.string.two),
        stringResource(id = R.string.three),
        stringResource(id = R.string.four),
        stringResource(id = R.string.five),
        stringResource(id = R.string.six),
        stringResource(id = R.string.seven),
        stringResource(id = R.string.eight),
        stringResource(id = R.string.nine),
        stringResource(id = R.string.ten),
        stringResource(id = R.string.eleven),
        stringResource(id = R.string.twelve),
        stringResource(id = R.string.thirteen),
        stringResource(id = R.string.fourteen),
        stringResource(id = R.string.fifteen),
        stringResource(id = R.string.sixteen),
        stringResource(id = R.string.seventeen),
        stringResource(id = R.string.eighteen),
        stringResource(id = R.string.nineteen),
        stringResource(id = R.string.twenty),
        stringResource(id = R.string.twentyone),
        stringResource(id = R.string.twentytwo),
        stringResource(id = R.string.twentythree),
        stringResource(id = R.string.twentyfour),
        stringResource(id = R.string.twentyfive),
        stringResource(id = R.string.twentysix),
        stringResource(id = R.string.twentyseven),
        stringResource(id = R.string.twentyeight),
        stringResource(id = R.string.twentynine),
        stringResource(id = R.string.thirty),
        stringResource(id = R.string.thirtyone)
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
    val monthlyAverage: Double =
        if (monthlyLineDataPoints.isNotEmpty()) totalMonthly.toDouble() / monthlyLineDataPoints.size else 0.0

    val currentMonth =
        Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
    Log.d("MonthlyData", "Monthly data: $monthlyLineDataPoints , $monthlyAverage")

    //yearly data processing
    val yearlyXValues = listOf(
        stringResource(id = R.string.jan),
        stringResource(id = R.string.Feb),
        stringResource(id = R.string.Mar),
        stringResource(id = R.string.April),
        stringResource(id = R.string.May),
        stringResource(id = R.string.june),
        stringResource(id = R.string.july),
        stringResource(id = R.string.aug),
        stringResource(id = R.string.sep),
        stringResource(id = R.string.oct),
        stringResource(id = R.string.nov),
        stringResource(id = R.string.dec)
    )
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
    val yearlyAverage: Double =
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
                            text = stringResource(id = R.string.average_fucks),
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
                        xCount = weeklyLineDataPoints.size,
                        themeSetting = themeSetting
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
                            text = stringResource(id = R.string.average_fucks),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Text(
                        text = "$currentMonth 1-31",
                        modifier = Modifier.padding(end = 3.dp, bottom = 5.dp, start = 10.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    LineChartComposable(lineDataPoints = monthlyLineDataPoints, xCount = 6, themeSetting = themeSetting)
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
                            text = when {
                                yearlyAverage == 0.0 -> "0"
                                yearlyAverage < 1.0 -> "<1"
                                else -> yearlyAverage.toString()
                            },
                            modifier = Modifier.padding(end = 3.dp),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = stringResource(id = R.string.average_fucks),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.january_december),
                        modifier = Modifier.padding(end = 3.dp, bottom = 5.dp, start = 10.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    LineChartComposable(
                        lineDataPoints = yearlyLineDataPoints,
                        xCount = yearlyLineDataPoints.size,
                        themeSetting = themeSetting
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


