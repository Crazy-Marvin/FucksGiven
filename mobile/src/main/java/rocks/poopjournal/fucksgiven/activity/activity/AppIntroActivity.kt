package rocks.poopjournal.fucksgiven.activity.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment

import com.github.paolorotolo.appintro.AppIntro2

import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.activity.fragment.IntroSliderFragment

class AppIntroActivity : AppIntro2() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addSlide(IntroSliderFragment.newInstance(R.layout.fragment_intro_slider_1))
        addSlide(IntroSliderFragment.newInstance(R.layout.fragment_intro_slider_2))
        addSlide(IntroSliderFragment.newInstance(R.layout.fragment_intro_slider_3))

        showSkipButton(false)

    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)

        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) {
        super.onSlideChanged(oldFragment, newFragment)

    }
}
