package rocks.poopjournal.fucksgiven.view.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.view.View
import com.example.kotlindemo.ErrorDialog
import kotlinx.android.synthetic.main.activity_add_fucks.*
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.view.listener.ItemClickListener
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class AddFucksActivity : BaseActivity(), View.OnClickListener {

    lateinit var currentCalendar : Calendar
    override fun provideLayout(): Int {
        return R.layout.activity_add_fucks
    }

    override fun init() {
        currentCalendar = Calendar.getInstance()
        edSetTime.setOnClickListener(this)
        edSetDate.setOnClickListener(this)
    }
    override fun onClick(view: View) {
        when(view.id){
            R.id.edSetDate -> ShowDatePickerDialog()
            R.id.edSetTime -> ShowTimePickerDialog()
        }
    }

    // method to show date picker
    private fun ShowDatePickerDialog(){
        val cal = Calendar.getInstance(TimeZone.getDefault())
        val datePickerDialog = DatePickerDialog(this, datePickerDialogListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.setCancelable(false)
        datePickerDialog.show()
    }

    // method to show time picker
    private fun ShowTimePickerDialog() {
        val cal = Calendar.getInstance(TimeZone.getDefault())
        val timePickerDialog = TimePickerDialog(this, onTimeSetListener, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), false)
        timePickerDialog.setCancelable(false)
        timePickerDialog.show()
    }

    // datepicker listener
    private var datePickerDialogListener = DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
        val simpledateformat: SimpleDateFormat
        val selectedCalendar = Calendar.getInstance()
        selectedCalendar.set(year,monthOfYear,dayOfMonth)

        // check whether selected date is current or not. If selected date is current date then show Today insread of day.
        if (currentCalendar.get(Calendar.YEAR) == year && currentCalendar.get(Calendar.MONTH) == monthOfYear && currentCalendar.get(Calendar.DAY_OF_MONTH) == dayOfMonth) {
            simpledateformat = SimpleDateFormat(getString(R.string.date_month_format))

            val todayString = "Today, " + simpledateformat.format(selectedCalendar.time)
            edSetDate.setText(todayString)

        // If selected date is future date show error dialog
        } else if (currentCalendar.get(Calendar.YEAR) < year || currentCalendar.get(Calendar.MONTH) < monthOfYear || currentCalendar.get(Calendar.DAY_OF_MONTH) < dayOfMonth) {

            ErrorDialog.showErroDialog(this,getString(R.string.error_dialog_title),getString(R.string.error_dialog_desc),getString(R.string.tv_error_dialog_action), object : ItemClickListener<View> {
                override fun onItemClicked(model: View) {
                    ShowDatePickerDialog()
                }
            })
        // if selected past date
        } else {
            simpledateformat = SimpleDateFormat(getString(R.string.day_date_month_format))
            edSetDate.setText(simpledateformat.format(selectedCalendar.time))
        }
    }
    private val onTimeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
        val simpledateformat = SimpleDateFormat(getString(R.string.hour_min_format))
        edSetTime.setText(simpledateformat.format(Time(hour, minute, 0)))
    }
}
