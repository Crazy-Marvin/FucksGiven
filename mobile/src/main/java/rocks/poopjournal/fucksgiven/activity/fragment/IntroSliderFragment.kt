package rocks.poopjournal.fucksgiven.activity.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class IntroSliderFragment : Fragment() {

    private var layoutResId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null && arguments.containsKey(ARG_LAYOUTID))
            layoutResId = arguments.getInt(ARG_LAYOUTID)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(layoutResId, container, false)
    }

    companion object {
        private val ARG_LAYOUTID = "layoutResId"

        fun newInstance(layoutResId: Int): IntroSliderFragment {
            val fragment = IntroSliderFragment()
            val args = Bundle()
            args.putInt(ARG_LAYOUTID, layoutResId)
            fragment.arguments = args
            return fragment
        }
    }
}
