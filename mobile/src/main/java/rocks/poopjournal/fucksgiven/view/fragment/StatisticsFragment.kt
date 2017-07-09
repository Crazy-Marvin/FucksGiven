package rocks.poopjournal.fucksgiven.view.fragment

import android.content.Intent
import android.view.View
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.view.activity.AddFucksActivity


class StatisticsFragment : BaseFragment(), View.OnClickListener {
    override fun onClick(p0: View?) {
        startActivity(Intent(activity, AddFucksActivity::class.java))
    }

    override fun provideLayout(): Int {
        return R.layout.fragment_statiscs
    }

    override fun init() {
            fabAddAction.setOnClickListener(this)
    }
}