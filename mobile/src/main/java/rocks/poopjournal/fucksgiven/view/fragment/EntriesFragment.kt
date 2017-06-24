package rocks.poopjournal.fucksgiven.view.fragment

import kotlinx.android.synthetic.main.fragmet_entries.*
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.model.Entry
import rocks.poopjournal.fucksgiven.view.adapter.EntriesAdapter


class EntriesFragment : BaseFragment() {

    private val entries = listOf<Entry>()

    override fun provideLayout(): Int {
        return R.layout.fragmet_entries
    }

    override fun init() {
        rvEntries.adapter = EntriesAdapter(context, entries)
    }

}
