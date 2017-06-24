package rocks.poopjournal.fucksgiven.view.fragment

import kotlinx.android.synthetic.main.fragmet_entries.*
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.model.Entry
import rocks.poopjournal.fucksgiven.view.adapter.EntriesAdapter


class EntriesFragment : BaseFragment() {


    private var adapter: EntriesAdapter? = null

    override fun provideLayout(): Int {
        return R.layout.fragmet_entries
    }

    override fun init() {
        val entries = listOf<Entry>()
        adapter = EntriesAdapter(context, entries)
        adapter!!.itemClickListener = { position, _ -> adapter!!.showOptions(position) }
        rvEntries.adapter = adapter
    }

    override fun dispose() {
        adapter?.itemClickListener = null
        super.dispose()
    }

}
