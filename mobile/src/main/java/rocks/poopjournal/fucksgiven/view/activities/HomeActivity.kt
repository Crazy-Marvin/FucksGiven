package rocks.poopjournal.fucksgiven.view.activities


import kotlinx.android.synthetic.main.activity_home.*
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.view.adapters.CommonPagerAdapter
import rocks.poopjournal.fucksgiven.view.fragment.CalendarFragment
import rocks.poopjournal.fucksgiven.view.fragment.EntriesFragment
import rocks.poopjournal.fucksgiven.view.fragment.StatisticsFragment

class HomeActivity : BaseActivity() {


    override fun provideLayout(): Int {
        return R.layout.activity_home
    }

    override fun init() {
        val fragments = listOf(EntriesFragment(), CalendarFragment(), StatisticsFragment())
        val titles = listOf(getString(R.string.entries), getString(R.string.stats), getString(R.string.calendar))
        val adapter = CommonPagerAdapter(supportFragmentManager, fragments, titles)
        pagerHome.adapter = adapter
        tabHome.setupWithViewPager(pagerHome)
    }


}
