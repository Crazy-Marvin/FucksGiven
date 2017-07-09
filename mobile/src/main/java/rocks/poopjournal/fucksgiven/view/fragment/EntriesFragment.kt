package rocks.poopjournal.fucksgiven.view.fragment

import android.content.Intent
import android.support.annotation.Keep
import android.support.v7.widget.LinearLayoutManager
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.fragmet_entries.*
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.helper.formatDateTime
import rocks.poopjournal.fucksgiven.model.Entry
import rocks.poopjournal.fucksgiven.model.MonthChangedEvent
import rocks.poopjournal.fucksgiven.util.Constants
import rocks.poopjournal.fucksgiven.view.activity.AddFucksActivity
import rocks.poopjournal.fucksgiven.view.adapter.EntriesAdapter
import timber.log.Timber
import java.util.*


class EntriesFragment : BaseFragment() {

    private lateinit var adapter: EntriesAdapter

    override fun provideLayout(): Int {
        return R.layout.fragmet_entries
    }

    override fun init() {
        fabAddFuck.setOnClickListener {
            startActivity(Intent(activity, AddFucksActivity::class.java))
        }

        val entries = mutableListOf<Entry>()

        val random = Random(1)
        val currentCalendar = Calendar.getInstance()
        for (i in 1..10) {
            currentCalendar.add(Calendar.DAY_OF_MONTH, random.nextInt(25))
            currentCalendar.add(Calendar.MONTH, 1)
            entries.add(Entry(i, currentCalendar.timeInMillis, currentCalendar.formatDateTime(Constants.DateFormats.ENTRY_DATE_FORMAT)))
        }

        adapter = EntriesAdapter(context, entries)
        adapter.itemClickListener = { position, _ -> adapter.showOptions(position) }
        rvEntries.layoutManager = LinearLayoutManager(context)
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
