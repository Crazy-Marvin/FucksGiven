package rocks.poopjournal.fucksgiven.view.fragment

import com.github.gfranks.collapsible.calendar.CollapsibleCalendarView
import com.github.gfranks.collapsible.calendar.model.CollapsibleCalendarEvent
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.joda.time.LocalDate
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.model.MonthChangedEvent


class CalendarFragment : BaseFragment(), CollapsibleCalendarView.Listener<CollapsibleCalendarEvent> {

    override fun provideLayout(): Int {
        return R.layout.fragment_calendar
    }

    override fun init() {
        collapsibleCalendar.setListener(this)
        collapsibleCalendar.minDate = LocalDate.now().minusYears(10)
        collapsibleCalendar.maxDate = LocalDate.now().plusYears(10)
    }

    override fun onDateSelected(date: LocalDate?, events: MutableList<CollapsibleCalendarEvent>?) {

    }

    override fun onMonthChanged(date: LocalDate?) {

    }

    override fun onHeaderClick() {

    }


    @Subscribe
    fun onMonthChanged(event: MonthChangedEvent) {
        if (event.prev) {
            collapsibleCalendar.prev()
        } else {
            collapsibleCalendar.next()
        }
    }
}
