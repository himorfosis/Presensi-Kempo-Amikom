package com.kempoamikompresensi.home.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.kempoamikompresensi.R
import com.kempoamikompresensi.home.adapter.PresensiAdapter
import com.kempoamikompresensi.presensi.ScanPresensi
import com.kempoamikompresensi.presensi.model.PresensiEntity
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.layout_status_failure.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.intentFor

class HomeFragmnet : Fragment() {

    lateinit var adapterPresensi: PresensiAdapter
    lateinit var database: DatabaseReference
    var listData: MutableList<PresensiEntity> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FirebaseDatabase.getInstance().getReference("presensi")
        setAdapter()
        initUI()
        fetchPresensi()
    }

    private fun initUI() {

//        adapterPresensi.add(DataSample.fetchDataPrensensi())
        scan_presensi_ll.onClick {
            startActivity(intentFor<ScanPresensi>())
        }
    }

    private fun setAdapter() {
        adapterPresensi = PresensiAdapter()
        presensi_recyler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterPresensi
        }
    }

    private fun fetchPresensi() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                isFailure()
            }

            override fun onDataChange(p0: DataSnapshot) {

                if (p0!!.exists()) {

                    p0.children.forEach {
                        var item = it.getValue(PresensiEntity::class.java)
                        listData.add(item!!)
                    }

                    if (listData.isNotEmpty()) {
                        isProgressGone()
                        adapterPresensi.add(listData)
                    } else {
                        onDataEmpty()
                    }

                } else {
                    isFailure()
                }

            }
        })
    }

    private fun isProgressGone() {
//        progress_presensi.visibility = View.INVISIBLE
    }

    private fun onDataEmpty() {

        isProgressGone()
        title_status_tv.visibility = View.VISIBLE
        description_status_tv.visibility = View.VISIBLE

        title_status_tv.text = "Yah, Belum ada presensi"
        description_status_tv.text =
            "Ketuk icon berwarna merah di bawah untuk melakukan scan presensi hari ini"
    }

    private fun isFailure() {
        isProgressGone()
        title_status_tv.visibility = View.VISIBLE
        description_status_tv.visibility = View.VISIBLE

        title_status_tv.text = "Yah, koneksi gagal..."
        description_status_tv.text = "Cek koneksi Wi-Fi atau kuota internetmu dan coba lagi ya."
    }

    private fun isLog(message: String) {
        Log.e("home frag", message)
    }

}