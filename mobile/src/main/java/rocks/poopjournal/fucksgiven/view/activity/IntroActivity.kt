package rocks.poopjournal.fucksgiven.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.github.paolorotolo.appintro.AppIntro2
import com.github.paolorotolo.appintro.AppIntroFragment
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.util.Constants
import rocks.poopjournal.fucksgiven.util.PreferenceHelper
import rocks.poopjournal.fucksgiven.util.PreferenceHelper.set

class IntroActivity : AppIntro2() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFlowAnimation()
        // Just set a title, description, background and image. AppIntro will do the rest.
        addSlide(AppIntroFragment.newInstance(
                getString(R.string.keep_a_diary),
                getString(R.string.keep_a_diary_desc),
                R.drawable.ic_intro,
                ContextCompat.getColor(this, R.color.md_blue_500))
        )
        addSlide(AppIntroFragment.newInstance(
                getString(R.string.find_patterns),
                getString(R.string.find_patterns_desc),
                R.drawable.ic_intro,
                ContextCompat.getColor(this, R.color.md_amber_500))
        )
        addSlide(AppIntroFragment.newInstance(
                getString(R.string.build_new_habits),
                getString(R.string.build_new_habits_desc),
                R.drawable.ic_intro,
                ContextCompat.getColor(this, R.color.md_blue_grey_500))
        )
        showSkipButton(false)
    }


    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        val prefs = PreferenceHelper.defaultPrefs(this)
        prefs[Constants.Preferences.WAS_APP_OPENED] = true
        navigateToHome()
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        this.finish()
        startActivity(intent)
    }

}
