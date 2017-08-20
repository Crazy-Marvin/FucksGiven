package rocks.poopjournal.fucksgiven.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.fragment_intro_one.*
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.util.Constants
import rocks.poopjournal.fucksgiven.view.activity.IntroActivity

/**
 * Created on 23-Jul-17.
 */

class IntroFragment : BaseFragment() {

    companion object {
        fun newInstance(index: Int): Fragment {
            val bundle = Bundle()
            bundle.putInt(Constants.BundleExtras.INDEX, index)
            val fragment = IntroFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun provideLayout(): Int {
        val index = arguments.getInt(Constants.BundleExtras.INDEX)
        when (index) {
            1 -> return R.layout.fragment_intro_one
            2 -> return R.layout.fragment_intro_two
            else -> return R.layout.fragment_intro_three
        }
    }

    override fun init() {
        viewClickable.setOnClickListener { (activity as IntroActivity).nextScreen() }
    }
}

