package rocks.poopjournal.fucksgiven.view.activity


import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.model.MonthChangedEvent
import rocks.poopjournal.fucksgiven.view.adapter.CommonPagerAdapter
import rocks.poopjournal.fucksgiven.view.fragment.CalendarFragment
import rocks.poopjournal.fucksgiven.view.fragment.EntriesFragment
import rocks.poopjournal.fucksgiven.view.fragment.StatisticsFragment
import java.util.*

class HomeActivity : BaseActivity() {

    val monthCalendar: Calendar = Calendar.getInstance()

    override fun provideLayout(): Int {
        return R.layout.activity_home
    }

    override fun init() {
        setupViewPager()
        ivLeft.setOnClickListener { onPreviousMonthClick() }
        ivRight.setOnClickListener { onNextMonthClick() }
    }

    private fun setupViewPager() {
        val fragments = listOf(EntriesFragment(), StatisticsFragment(), CalendarFragment())
        val titles = listOf(getString(R.string.entries), getString(R.string.stats), getString(R.string.calendar))
        val adapter = CommonPagerAdapter(supportFragmentManager, fragments, titles)
        pagerHome.adapter = adapter
        tabHome.setupWithViewPager(pagerHome)
    }

    private fun onNextMonthClick() {
        monthCalendar.add(Calendar.MONTH, 1)
        bus.post(MonthChangedEvent(monthCalendar))
    }

    private fun onPreviousMonthClick() {
        monthCalendar.add(Calendar.MONTH, -1)
        bus.post(MonthChangedEvent(monthCalendar))
    }


}
