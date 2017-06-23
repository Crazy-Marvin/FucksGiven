package rocks.poopjournal.fucksgiven.view.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Author : Experiments
 * Date : 09-Apr-16
 *
 *
 * common class for all view pager adapters
 * there are two overloaded constructors. one is without titles and other is with titles<br></br>
 * if titles are given; it will call [FragmentPagerAdapter.getPageTitle] to set title
 */
class CommonPagerAdapter : FragmentPagerAdapter {

    private val items: List<Fragment>
    private var titles: List<String> = ArrayList()


    constructor(fm: FragmentManager, fragments: List<Fragment>) : super(fm) {
        this.items = fragments
    }

    constructor(fm: FragmentManager, fragments: List<Fragment>, titles: List<String>) : super(fm) {
        this.items = fragments
        this.titles = titles
        if (fragments.size != titles.size) {
            throw IllegalArgumentException("no. of titles and no. of fragments must be same in size")
        }
    }

    override fun getItem(position: Int): Fragment {
        return items[position]
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}
