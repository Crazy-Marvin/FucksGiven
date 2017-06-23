package rocks.poopjournal.fucksgiven.view.activities

import android.content.Intent
import android.widget.TextView

import butterknife.BindView
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.util.Constants
import rocks.poopjournal.fucksgiven.util.PreferenceHelper
import rocks.poopjournal.fucksgiven.util.PreferenceHelper.get
import rocks.poopjournal.fucksgiven.util.Utils


class SplashActivity : BaseActivity() {

    @BindView(R.id.tvVersion)
    @JvmField var appVersion: TextView? = null


    override fun provideLayout(): Int {
        return R.layout.activity_splash
    }

    override fun init() {
        appVersion?.text = getString(R.string.splash_app_version, Utils.appVersionName)
        mainThreadHandler.postDelayed({ navigateToNextScreen() }, Constants.Delays.SPLASH_DELAY)
    }

    private fun navigateToNextScreen() {
        val prefs = PreferenceHelper.defaultPrefs(this)
        val wasAppOpened: Boolean? = prefs[Constants.Preferences.WAS_APP_OPENED]

        val intent: Intent
        if (wasAppOpened!!) {
            intent = Intent(this, HomeActivity::class.java)
        } else {
            intent = Intent(this, IntroActivity::class.java)
        }
        this.finish()
        startActivity(intent)
    }

}
