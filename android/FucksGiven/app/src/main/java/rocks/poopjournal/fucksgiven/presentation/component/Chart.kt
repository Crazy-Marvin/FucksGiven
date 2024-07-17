package rocks.poopjournal.fucksgiven.presentation.component

import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.layoutDirection
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.presentation.ui.theme.FuckGreen
import java.util.Locale

@Composable
fun LineChartComposable(
    lineDataPoints: List<LineDataPoint>,
    xCount : Int

) {
    val context1 = LocalContext.current
    val entries = lineDataPoints.mapIndexed { index, dataPoint ->
        Entry(index.toFloat(), dataPoint.yValue.toFloat())
    }

    val tf = ResourcesCompat.getFont(context1, R.font.opensans_regular)
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        factory = { context ->
            LineChart(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(Color.White.toArgb())
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                axisRight.isEnabled = true
                axisLeft.isEnabled = false
                axisRight.textColor = Color.Black.toArgb()
                xAxis.textColor = Color.Black.toArgb()
                legend.isEnabled = false

                // Customizing the Line Chart
                description.isEnabled = false
                setDrawGridBackground(false)

                // Data Setup

                val dataSet = LineDataSet(entries, "Label").apply {
                    color = FuckGreen.toArgb()
                    valueTextColor = Color.Black.toArgb()
                    lineWidth = 2f
                    setDrawCircles(true)
                    setCircleColor(FuckGreen.toArgb())
                    setDrawValues(false)
                    setPinchZoom(false)
                }

                val lineData = LineData(dataSet)
                this.data = lineData

                // XAxis configuration
                xAxis.valueFormatter = IndexAxisValueFormatter(lineDataPoints.map { it.xValue })
                xAxis.granularity = 1f
                xAxis.labelCount = xCount
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.isGranularityEnabled = true
                xAxis.setDrawGridLines(true)
                xAxis.setAvoidFirstLastClipping(true)
                xAxis.setTextSize(14f)
                xAxis.typeface = tf


                axisRight.valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return value.toInt().toString()
                    }
                }
                // YAxis configuration
                axisRight.granularity = 1f
                axisRight.setDrawGridLines(true)
             // For 0, 5, 10
                axisRight.setLabelCount(3,true)
                axisRight.axisMinimum = 0f
//                axisRight.axisMaximum = 10f
                axisRight.isGranularityEnabled = true
                notifyDataSetChanged()
                invalidate() // Refresh the chart
            }
        },
        update = { chart ->

            val dataSet = LineDataSet(entries, "Label").apply {
                color = FuckGreen.toArgb()
                valueTextColor = Color.Black.toArgb()
                lineWidth = 2f
                setDrawCircles(true)
                setCircleColor(FuckGreen.toArgb())
                setDrawValues(false)
            }

            chart.data = LineData(dataSet)

            // XAxis configuration
            chart.xAxis.valueFormatter = IndexAxisValueFormatter(lineDataPoints.map { it.xValue })
            chart.xAxis.granularity = 1f
            chart.xAxis.labelCount = xCount
            chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            chart.xAxis.isGranularityEnabled = true
            chart.xAxis.setDrawGridLines(true)
            chart.xAxis.setAvoidFirstLastClipping(true)
            chart.xAxis.setTextSize(14f)
            chart.xAxis.typeface = tf

            // YAxis configuration
            chart.axisRight.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return value.toInt().toString()
                }
            }
            chart.axisRight.granularity = 1f
            chart.axisRight.setDrawGridLines(true)
            chart.axisRight.setLabelCount(3,true)
            chart.axisRight.axisMinimum = 0f
//            chart.axisRight.axisMaximum = 10f
            chart.axisRight.isGranularityEnabled = true
            chart.notifyDataSetChanged()

            chart.invalidate() // Refresh the chart
        }
    )
}


data class LineDataPoint(val xValue: String, val yValue: Int)


