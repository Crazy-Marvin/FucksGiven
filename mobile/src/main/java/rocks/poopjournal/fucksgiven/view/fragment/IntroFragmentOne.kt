package rocks.poopjournal.fucksgiven.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.fragment_introduction.*
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.util.Constants
import rocks.poopjournal.fucksgiven.view.activity.IntroActivity

/**
 * Created by KS on 09-Jul-17.
 */
class IntroFragment : BaseFragment() {

    companion object {
        fun newInstance(background: Int): Fragment {
            val introFragment = IntroFragment()
            val bundle = Bundle()
            bundle.putInt(Constants.Extras.INTRO_BACKGROUND, background)
            introFragment.arguments = bundle
            return introFragment
        }
    }

    override fun init() {
        llRoot.setBackgroundResource(arguments.getInt(Constants.Extras.INTRO_BACKGROUND))
        viewFake.setOnClickListener { (activity as IntroActivity).changePage() }
    }

    override fun provideLayout(): Int {
        return R.layout.fragment_introduction
    }


}