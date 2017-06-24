package rocks.poopjournal.fucksgiven.view.activity


import kotlinx.android.synthetic.main.activity_home.*
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.view.adapter.CommonPagerAdapter
import rocks.poopjournal.fucksgiven.view.fragment.CalendarFragment
import rocks.poopjournal.fucksgiven.view.fragment.EntriesFragment
import rocks.poopjournal.fucksgiven.view.fragment.StatisticsFragment
import java.util.*

class HomeActivity : BaseActivity() {

    lateinit var dateChangeListener: (month: Calendar) -> Unit

    override fun provideLayout(): Int {
        return R.layout.activity_home
    }

    override fun init() {
        //setting up viewpager
        val fragments = listOf(EntriesFragment(), CalendarFragment(), StatisticsFragment())
        val titles = listOf(getString(R.string.entries), getString(R.string.stats), getString(R.string.calendar))
        val adapter = CommonPagerAdapter(supportFragmentManager, fragments, titles)
        pagerHome.adapter = adapter
        tabHome.setupWithViewPager(pagerHome)
    }


}
