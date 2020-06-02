package com.kempoamikompresensi.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import com.kempoamikompresensi.R
import kotlinx.android.synthetic.main.dialog_message.*

class DialogInfo(context: Context?, title: String = "", message: String = "") : Dialog(context!!)  {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_message)
        action_btn.setOnClickListener { dismiss() }

        if (title.isNotEmpty()) {
            status_tv.text = title
        } else {
            status_tv.visibility = View.GONE
        }

        if (message.isNotEmpty()) {
            desctiption_tv.text = message
        } else {
            desctiption_tv.visibility = View.GONE
        }

    }
}