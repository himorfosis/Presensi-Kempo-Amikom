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
import com.kempoamikompresensi.data.DataSample
import com.kempoamikompresensi.home.adapter.KenshiAdapter
import com.kempoamikompresensi.kenshi.KenshiCreate
import com.kempoamikompresensi.kenshi.model.KenshiEntity
import kotlinx.android.synthetic.main.activity_kenshi_create.*
import kotlinx.android.synthetic.main.kenshi_fragment.*
import kotlinx.android.synthetic.main.layout_status_failure.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.intentFor
import java.util.*
import kotlin.collections.ArrayList

class KenshiFragment : Fragment() {

    lateinit var adapterKenshi: KenshiAdapter
    lateinit var database: DatabaseReference
    lateinit var listData: MutableList<KenshiEntity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.kenshi_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        initUI()
        listData = mutableListOf()
        fetchKenshi()

    }

    private fun initUI() {

//        adapterKenshi.add(DataSample.fetchDataKenshi())
        add_kenshi_ll.onClick {
            startActivity(intentFor<KenshiCreate>())
        }

    }

    private fun fetchKenshi() {

        database = FirebaseDatabase.getInstance().getReference("kenshi")
        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                isFailure()
            }

            override fun onDataChange(p0: DataSnapshot) {

                if (p0!!.exists()) {

                    p0.children.forEach {
                        var item = it.getValue(KenshiEntity::class.java)
                        listData.add(item!!)
                        isLog("name : ${item.name}")
                    }

                    if (listData.isEmpty()) {
                        onDataEmpty()
                    } else {
                        isProgressGone()
                        adapterKenshi.add(listData)
                    }
                } else {
                    isFailure()
                }

            }
        })

    }

    private fun setAdapter() {
        adapterKenshi = KenshiAdapter()
        recycler_kenshi.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterKenshi
        }
    }

    private fun onDataEmpty() {
        isProgressGone()
        title_status_tv.visibility = View.VISIBLE
        description_status_tv.visibility = View.VISIBLE

        title_status_tv.text = "Yah, data belum tersedia"
        description_status_tv.text = "Ketuk + untuk menambahkan data kenshi"
    }

    private fun isProgressGone() {
//        progress_kenshi.visibility = View.INVISIBLE
    }

    private fun isFailure() {
        isProgressGone()
        title_status_tv.visibility = View.VISIBLE
        description_status_tv.visibility = View.VISIBLE

        title_status_tv.text = "Yah, koneksi gagal..."
        description_status_tv.text = "Cek koneksi Wi-Fi atau kuota internetmu dan coba lagi ya."
    }

    private fun isLog(msg: String) {
        Log.e("KenshiFrag", msg)
    }

}