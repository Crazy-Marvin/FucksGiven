package rocks.poopjournal.fucksgiven.view.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.support.v7.app.AlertDialog
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlacePicker
import kotlinx.android.synthetic.main.activity_add_fucks.*
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.helper.*
import rocks.poopjournal.fucksgiven.util.Constants
import java.util.*

class AddFucksActivity : PlaceDetectorActivity() {

    private val calendar: Calendar = Calendar.getInstance()
    private val PLACE_PICKER_REQUEST_CODE = 11

    override fun provideLayout(): Int {
        return R.layout.activity_add_fucks
    }

    override fun init() {
        super.init()
        ivFuck.setOnClickListener { finish() }
        edSetDate.setOnClickListener { showDatePickerDialog() }
        edSetTime.setOnClickListener { showTimePickerDialog() }
        tvLocation.setOnClickListener { openPlacePicker() }
    }

    private fun openPlacePicker() {
        val builder = PlacePicker.IntentBuilder()
        startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST_CODE)
    }

    // method to show date picker
    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth -> onDateSet(year, monthOfYear, dayOfMonth) },
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.show(true)
    }

    // method to show time picker
    private fun showTimePickerDialog() {
        val timePickerDialog = TimePickerDialog(
                this,
                { _, hourOfDay, minute -> onTimeSet(hourOfDay, minute) },
                calendar[Calendar.HOUR],
                calendar[Calendar.MINUTE],
                false
        )
        timePickerDialog.show(true)
    }

    private fun onDateSet(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val selectedCalendar = Calendar.getInstance()
        selectedCalendar.setDate(year, monthOfYear, dayOfMonth)
        validateDate(selectedCalendar, { date ->
            calendar.setDate(year, monthOfYear, dayOfMonth)
            edSetDate.setText(date)
        })
    }

    private fun onTimeSet(hourOfDay: Int, minute: Int) {
        calendar.setTime(hourOfDay, minute, 0)
        val formattedTime = calendar.formatDateTime(Constants.DateFormats.HOUR_MIN_FORMAT)
        edSetTime.setText(formattedTime)
    }

    private fun validateDate(selectedCalendar: Calendar, callback: (String) -> Unit) {
        //check whether selected date is current or not. If selected date is current date then show Today instead of day.
        when {
            selectedCalendar.isTodayDate() -> {
                var todayDate = selectedCalendar.formatDateTime(Constants.DateFormats.DATE_MONTH_FORMAT)
                todayDate = getString(R.string.today_date, todayDate)
                callback.invoke(todayDate)
            }
        //if selected date is future date show error dialog
            selectedCalendar.isFutureDate() -> {
                AlertDialog.Builder(this)
                        .showError(getString(R.string.error_dialog_title), getString(R.string.error_dialog_desc), getString(R.string.tv_error_dialog_action), {
                            showDatePickerDialog()
                        })
            }
        //if selected past date
            else -> {
                val pastDate = selectedCalendar.formatDateTime(Constants.DateFormats.DAY_DATE_MONTH_FORMAT)
                callback.invoke(pastDate)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == PLACE_PICKER_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlacePicker.getPlace(this, data)
                onPlaceUpdated(place)
            }
        }
    }

    override fun onPlaceUpdated(place: Place) {
        tvLocation.text = place.name
    }
}
