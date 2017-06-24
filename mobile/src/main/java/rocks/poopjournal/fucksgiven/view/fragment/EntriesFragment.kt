package rocks.poopjournal.fucksgiven.view.fragment

import android.support.annotation.Keep
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.fragmet_entries.*
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.model.Entry
import rocks.poopjournal.fucksgiven.model.MonthChangedEvent
import rocks.poopjournal.fucksgiven.view.adapter.EntriesAdapter
import timber.log.Timber


class EntriesFragment : BaseFragment() {

    private lateinit var adapter: EntriesAdapter

    override fun provideLayout(): Int {
        return R.layout.fragmet_entries
    }

    override fun init() {
        val entries = listOf<Entry>()
        adapter = EntriesAdapter(context, entries)
        adapter.itemClickListener = { position, _ -> adapter.showOptions(position) }
        rvEntries.adapter = adapter
    }

    override fun dispose() {
        adapter.itemClickListener = null
        super.dispose()
    }

    @Keep
    @Subscribe
    public fun onMonthChanged(event: MonthChangedEvent) {
        Timber.d(event.toString())
    }
}
