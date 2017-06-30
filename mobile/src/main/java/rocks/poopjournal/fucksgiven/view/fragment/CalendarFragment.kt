package rocks.poopjournal.fucksgiven.view.fragment

import com.github.gfranks.collapsible.calendar.CollapsibleCalendarView
import com.github.gfranks.collapsible.calendar.model.CollapsibleCalendarEvent
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.joda.time.LocalDate
import rocks.poopjournal.fucksgiven.R


class CalendarFragment : BaseFragment(), CollapsibleCalendarView.Listener<CollapsibleCalendarEvent> {


    override fun provideLayout(): Int {
        return R.layout.fragment_calendar
    }

    override fun init() {

        collapsibleCalendar.setListener(this)

    }

    override fun onDateSelected(date: LocalDate?, events: MutableList<CollapsibleCalendarEvent>?) {

    }

    override fun onMonthChanged(date: LocalDate?) {

    }

    override fun onHeaderClick() {

    }
}
