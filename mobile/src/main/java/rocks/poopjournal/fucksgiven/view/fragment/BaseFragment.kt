package rocks.poopjournal.fucksgiven.view.fragment

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import butterknife.ButterKnife

/**
 * Created by Experiments on 19-Feb-17.
 */

abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(provideLayout(), container, false)
        ButterKnife.bind(this, view)
        return view
    }

    @CallSuper
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    @CallSuper
    override fun onDestroyView() {
        dispose()
        super.onDestroyView()
    }

    protected abstract fun init()

    @LayoutRes
    protected abstract fun provideLayout(): Int

    protected abstract fun dispose()

}
