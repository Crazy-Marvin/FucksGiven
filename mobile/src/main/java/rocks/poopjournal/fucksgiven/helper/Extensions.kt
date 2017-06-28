package rocks.poopjournal.fucksgiven.helper

import android.app.Dialog
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.dialog_error.view.*
import rocks.poopjournal.fucksgiven.R
import java.text.SimpleDateFormat
import java.util.*

fun Calendar.setTime(hourOfDay: Int, minute: Int, second: Int) {
    this[Calendar.HOUR_OF_DAY] = hourOfDay
    this[Calendar.MINUTE] = minute
    this[Calendar.SECOND] = second
}

fun Calendar.setDate(year: Int, monthOfYear: Int, dayOfMonth: Int) {
    this[Calendar.YEAR] = year
    this[Calendar.MONTH] = monthOfYear
    this[Calendar.DAY_OF_MONTH] = dayOfMonth
}

fun Calendar.formatDateTime(format: String): String {
    val date = Date(timeInMillis)
    val simpleDateFormat = SimpleDateFormat(format, Locale.ENGLISH)
    return simpleDateFormat.format(date)
}

fun Calendar.isTodayDate(): Boolean {
    val todayCal = Calendar.getInstance()
    return this[Calendar.YEAR] == todayCal[Calendar.YEAR]
            && this[Calendar.MONTH] == todayCal[Calendar.MONTH]
            && this[Calendar.DAY_OF_MONTH] == todayCal[Calendar.DAY_OF_MONTH]
}

fun Calendar.isFutureDate(): Boolean {
    val todayCal = Calendar.getInstance()
    return todayCal[Calendar.YEAR] <= this[Calendar.YEAR]
            && todayCal[Calendar.MONTH] <= this[Calendar.MONTH]
            && todayCal[Calendar.DAY_OF_MONTH] < this[Calendar.DAY_OF_MONTH]
}

fun Dialog.show(cancelable: Boolean) {
    setCancelable(cancelable)
    setCanceledOnTouchOutside(cancelable)
    show()
}


fun AlertDialog.Builder.showError(title: String, description: String, action: String, callback: () -> Unit) {
    val view = LayoutInflater.from(context).inflate(R.layout.dialog_error, null, false)
    view.tvErrorTitle.text = title
    view.tvErrorDesc.text = description
    view.tvAction.text = action
    setView(view)
    val dialog = create()
    dialog.show(true)
    view.tvAction.setOnClickListener {
        dialog.dismiss()
        callback.invoke()
    }
}