package rocks.poopjournal.fucksgiven.view.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import rocks.poopjournal.fucksgiven.helper.BusProvider

/**
 * base class of all fragments
 */

abstract class BaseFragment : Fragment() {

    protected val bus = BusProvider.instance.bus
    protected val mainThreadHandler = Handler(Looper.getMainLooper())

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(provideLayout(), container, false)
        return view
    }

    @CallSuper
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onStart() {
        super.onStart()
        bus.register(this)
    }

    override fun onStop() {
        bus.unregister(this)
        super.onStop()
    }


    @CallSuper
    override fun onDestroyView() {
        dispose()
        super.onDestroyView()
    }

    @LayoutRes
    protected abstract fun provideLayout(): Int

    protected abstract fun init()


    protected open fun dispose() {
        mainThreadHandler.removeCallbacksAndMessages(null)
    }

}
