package rocks.poopjournal.fucksgiven.view.activity

import android.content.Intent
import kotlinx.android.synthetic.main.activity_intro.*
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.util.ViewPageTransformer
import rocks.poopjournal.fucksgiven.view.adapter.CommonPagerAdapter
import rocks.poopjournal.fucksgiven.view.fragment.IntroFragment

class IntroActivity : BaseActivity() {
    lateinit var adapter: CommonPagerAdapter
    override fun provideLayout(): Int {
        return R.layout.activity_intro
    }

    override fun init() {
        val fragments = listOf(IntroFragment.newInstance(1), IntroFragment.newInstance(2), IntroFragment.newInstance(3))
        adapter = CommonPagerAdapter(supportFragmentManager, fragments)
        pagerIntro.adapter = adapter
        pagerIntro.setPageTransformer(true, ViewPageTransformer(ViewPageTransformer.TransformType.FLOW))
    }

    fun nextScreen() {
        if (pagerIntro.currentItem == adapter.count - 1) {
            navigateToHome()
        } else {
            pagerIntro.currentItem = pagerIntro.currentItem + 1
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        this.finish()
        startActivity(intent)
    }
}
