package rocks.poopjournal.fucksgiven.view.activity


import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.helper.formatDateTime
import rocks.poopjournal.fucksgiven.model.MonthChangedEvent
import rocks.poopjournal.fucksgiven.util.Constants
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
        refreshMonthText()
        ivLeft.setOnClickListener { onPreviousMonthClick() }
        ivRight.setOnClickListener { onNextMonthClick() }
    }

    private fun setupViewPager() {
        val fragments = listOf(EntriesFragment(), StatisticsFragment(), CalendarFragment())
        val titles = listOf(getString(R.string.entries), getString(R.string.stats), getString(R.string.calendar))
        val adapter = CommonPagerAdapter(supportFragmentManager, fragments, titles)
        pagerHome.offscreenPageLimit = 1
        pagerHome.adapter = adapter
        tabHome.setupWithViewPager(pagerHome)
    }

    private fun onNextMonthClick() {
        monthCalendar.add(Calendar.MONTH, 1)
        bus.post(MonthChangedEvent(monthCalendar, false))
        refreshMonthText()
    }

    private fun onPreviousMonthClick() {
        monthCalendar.add(Calendar.MONTH, -1)
        bus.post(MonthChangedEvent(monthCalendar, true))
        refreshMonthText()
    }

    private fun refreshMonthText() {
        tvToolbarTitle.text = monthCalendar.formatDateTime(Constants.DateFormats.MONTH_YEAR_FORMAT)
    }


}
