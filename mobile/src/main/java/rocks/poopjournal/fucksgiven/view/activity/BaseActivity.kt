package rocks.poopjournal.fucksgiven.view.activity

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
import rocks.poopjournal.fucksgiven.helper.BusProvider
import rocks.poopjournal.fucksgiven.util.NetworkReceiver


/**
 * base class of all activities
 */

abstract class BaseActivity : AppCompatActivity() {

    private val networkReceiver = NetworkReceiver
    protected val bus = BusProvider.instance.bus
    protected val mainThreadHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(provideLayout())
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
        bus.unregister(this)
        unregisterReceiver(networkReceiver)
        super.onStop()
    }

    @CallSuper
    override fun onDestroy() {
        dispose()
        super.onDestroy()
    }


    /**
     * to hide keyboard programmatically
     */
    public fun hideKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromInputMethod(currentFocus?.windowToken, 0)
    }

    /**
     * to show a message in snake bar
     */
    public fun showMessage(error: String) {
        Snackbar.make(
                findViewById(android.R.id.content),
                error,
                Snackbar.LENGTH_SHORT
        ).show()
    }

    /**
     * to add fragment within activity container
     */
    public fun addFragment(@IdRes container: Int, fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(container, fragment, fragment.javaClass.simpleName)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    /**
     * to replace a fragment within activity container
     */
    public fun replaceFragment(@IdRes container: Int, fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(container, fragment, fragment.javaClass.simpleName)
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
