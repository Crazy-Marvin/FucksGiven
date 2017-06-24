package com.example.kotlindemo

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.view.listener.ItemClickListener

object ErrorDialog {

        lateinit var dialog: AlertDialog
        lateinit var builder: AlertDialog.Builder
        lateinit var handleClickListener: ItemClickListener<View>

        fun showErroDialog(context: Context, dialogTitle: String, errorDescription: String, actionName: String, listener: ItemClickListener<View>) {

            handleClickListener = listener

            builder = AlertDialog.Builder(context)

            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.dialog_error, null)
            //  ButterKnife.bind(context,view);

            val tvAction = view.findViewById(R.id.tv_action) as TextView
            val tvErrorTitle = view.findViewById(R.id.tv_error_title) as TextView
            val tvErrorDesc = view.findViewById(R.id.tv_error_desc) as TextView

            tvErrorTitle.text = dialogTitle
            tvErrorDesc.text = errorDescription
            tvAction.text = actionName
            tvAction.setOnClickListener { view->
                dialog.dismiss()
                handleClickListener.onItemClicked(view)
            }
            builder.setCancelable(true)
            builder.setView(view)
            dialog = builder.create()
            dialog.show()

        }
    }

