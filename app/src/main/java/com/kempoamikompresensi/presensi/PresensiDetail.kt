package com.kempoamikompresensi.presensi

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import com.google.firebase.database.*
import com.kempoamikompresensi.R
import com.kempoamikompresensi.home.HomepageActivity
import com.kempoamikompresensi.kenshi.model.KenshiEntity
import com.kempoamikompresensi.presensi.model.PresensiEntity
import com.kempoamikompresensi.util.date.DateSet
import kotlinx.android.synthetic.main.activity_presensi_detail.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class PresensiDetail : AppCompatActivity() {

    lateinit var database: DatabaseReference
    var getId: String = "-"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presensi_detail)

        database = FirebaseDatabase.getInstance().getReference("presensi")
        setToolbar()
        initUI()

    }

    private fun initUI() {

        getId = intent.getStringExtra("id")
        val id_kenshi = intent.getStringExtra("id_kenshi")
        val nik = intent.getStringExtra("nik")
        val name = intent.getStringExtra("name")
        val time = intent.getStringExtra("time")

        name_tv.text = name
        nik_tv.text = nik
        datetime_tv.text = DateSet.descripTimestamp(time.toLong())

    }

    private fun deleteDataPresensi() {
        database.orderByChild("id").equalTo(getId)
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                toast("Failed delete data")
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (item in p0.children) {
                    var data = item.getValue(PresensiEntity::class.java)
                    if (data!!.id == getId) {
                        item.ref.removeValue()
                        toast("Successful delete data")
                        break
                    }
                }

                startActivity(intentFor<HomepageActivity>())
            }
        })
    }

    private fun setToolbar() {

        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.toolbar)

        action_btn.visibility = View.VISIBLE
        titlebar_tv.text = "Presensi Detail"
        backbar_btn.onClick {
            finish()
        }

        action_btn.onClick {
            deleteDataPresensi()
        }

    }

}
