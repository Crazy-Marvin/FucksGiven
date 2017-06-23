package rocks.poopjournal.fucksgiven.view.activities

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.annotation.CallSuper
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import butterknife.ButterKnife
import rocks.poopjournal.fucksgiven.helper.BusProvider
import rocks.poopjournal.fucksgiven.util.NetworkReceiver


/**
 * Created by Fenil on 21-Nov-16.
 *
 * base class of all activities
 */

abstract class BaseActivity : AppCompatActivity() {

    private val networkReceiver = NetworkReceiver
    protected val bus = BusProvider.instance.bus
    protected val mainThreadHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(provideLayout())
        ButterKnife.bind(this)
        init()
    }


    @CallSuper
    override fun onStart() {
        super.onStart()
        bus.register(this)
        val connectivityFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, connectivityFilter)
    }

    @CallSuper
    override fun onStop() {
        super.onStop()
        bus.unregister(this)
        unregisterReceiver(networkReceiver)
    }

    @CallSuper
    override fun onDestroy() {
        dispose()
        super.onDestroy()
    }


    /**
     * to hide keyboard programmatically
     */
    protected fun hideKeyBoard() {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromInputMethod(currentFocus!!.windowToken, 0)
        }
    }

    /**
     * to show a message in snake bar
     */
    protected fun showMessage(error: String) {
        Snackbar.make(
                findViewById(android.R.id.content),
                error,
                Snackbar.LENGTH_SHORT
        ).show()
    }

    /**
     * to add or replace a fragment within activity container
     */
    protected fun addReplaceFragment(@IdRes container: Int, fragment: Fragment, addFragment: Boolean, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        if (addFragment) {
            transaction.add(container, fragment, fragment.javaClass.simpleName)
        } else {
            transaction.replace(container, fragment, fragment.javaClass.simpleName)
        }
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }


    /**
     * provided layout will be set in [setContentView]
     */
    @LayoutRes
    protected abstract fun provideLayout(): Int

    /**
     * will be called in [onCreate]. do initialization stuff.
     */
    protected abstract fun init()

    /**
     * will be called in [onDestroy]. release resources, callbacks and heavy objects
     */
    @CallSuper
    protected open fun dispose() {
        mainThreadHandler.removeCallbacksAndMessages(null)
    }
}
