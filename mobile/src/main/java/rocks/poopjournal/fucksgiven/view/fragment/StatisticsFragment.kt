package rocks.poopjournal.fucksgiven.view.fragment

import android.graphics.Color
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.fragment_statiscs.*
import rocks.poopjournal.fucksgiven.R


class StatisticsFragment : BaseFragment() {

    override fun provideLayout(): Int {
        return R.layout.fragment_statiscs
    }

    override fun init() {
        val data = BarData(getXAxisValues(), getDataSet())
        chart.data = data
        chart.setDescription(null)
        chart.invalidate()
    }


    private fun getDataSet(): ArrayList<BarDataSet> {
        var dataSets: ArrayList<BarDataSet>? = null

        val valueSet1 = ArrayList<BarEntry>()
        val v1e1 = BarEntry(1f, 0) // Jan
        valueSet1.add(v1e1)
        val v1e2 = BarEntry(4f, 1) // Feb
        valueSet1.add(v1e2)
        val v1e3 = BarEntry(6f, 2) // Mar
        valueSet1.add(v1e3)
        val v1e4 = BarEntry(3f, 3) // Apr
        valueSet1.add(v1e4)
        val v1e5 = BarEntry(9f, 4) // May
        valueSet1.add(v1e5)
        val v1e6 = BarEntry(1f, 5) // Jun
        valueSet1.add(v1e6)
        val v1e7 = BarEntry(4f, 6) // Jun
        valueSet1.add(v1e7)

        val valueSet2 = ArrayList<BarEntry>()
        val v2e1 = BarEntry(5f, 0) // Jan
        valueSet2.add(v2e1)
        val v2e2 = BarEntry(9f, 1) // Feb
        valueSet2.add(v2e2)
        val v2e3 = BarEntry(1f, 2) // Mar
        valueSet2.add(v2e3)
        val v2e4 = BarEntry(6f, 3) // Apr
        valueSet2.add(v2e4)
        val v2e5 = BarEntry(2f, 4) // May
        valueSet2.add(v2e5)
        val v2e6 = BarEntry(7f, 5) // Jun
        valueSet2.add(v2e6)
        val v2e7 = BarEntry(5f, 6) // Jun
        valueSet2.add(v2e7)
        val barDataSet2 = BarDataSet(valueSet2, "Days")
        barDataSet2.colors = listOf(Color.rgb(24, 55, 66))
        barDataSet2.setValueFormatter { value ->
            val formatted = value.toInt()
            "$formatted Fucks"
        }
        dataSets = ArrayList()
        dataSets.add(barDataSet2)
        return dataSets
    }

    private fun getXAxisValues(): ArrayList<String> {
        val xAxis = ArrayList<String>()
        xAxis.add("SUN")
        xAxis.add("MON")
        xAxis.add("TUE")
        xAxis.add("WED")
        xAxis.add("THU")
        xAxis.add("FRI")
        xAxis.add("SAT")
        return xAxis
    }
}