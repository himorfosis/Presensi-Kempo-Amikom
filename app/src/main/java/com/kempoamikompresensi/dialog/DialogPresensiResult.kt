package com.kempoamikompresensi.dialog

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.kempoamikompresensi.R
import com.kempoamikompresensi.home.HomepageActivity
import com.kempoamikompresensi.presensi.ScanPresensi
import kotlinx.android.synthetic.main.dialog_scan_result.*
import org.jetbrains.anko.support.v4.intentFor

class DialogPresensiResult(val name: String, val statusPresensi: Boolean) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_scan_result, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        checkUserPresensi()

    }

    private fun initUI() {

        close_img.setOnClickListener {
            startActivity(Intent(context, HomepageActivity::class.java))
        }
        action_btn.setOnClickListener {
            startActivity(Intent(context, ScanPresensi::class.java))
        }
    }

    private fun checkUserPresensi() {
        if (statusPresensi == true) {
            action_btn.text = "Oke, berhasil"
            status_tv.text = "Berhasil"
            desctiption_tv.text = "$name berhasil melakukan presensi"
        } else {
            status_tv.setTextColor(ContextCompat.getColor(context!!, R.color.red))
            action_btn.text = "Oke"
            status_tv.text = "Yah Gagal"
            desctiption_tv.text = "Presensi gagal, coba periksa id kenshi"
        }

        Handler().postDelayed(object : Thread() {
            override fun run() {
                dismiss()
                startActivity(intentFor<HomepageActivity>())
            }
        }, 2000)

    }

}