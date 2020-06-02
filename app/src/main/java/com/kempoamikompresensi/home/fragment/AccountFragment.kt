package com.kempoamikompresensi.home.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kempoamikompresensi.R
import kotlinx.android.synthetic.main.layout_status_failure.*

class AccountFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.account_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        isLog("")
        isFailure()
    }

    private fun initUI() {
    }

    private fun isLog(message: String) {
        Log.e("profile frag", message)
    }

    private fun isFailure() {
        title_status_tv.visibility = View.VISIBLE
        title_status_tv.text = "Cooming Soon"
    }


}