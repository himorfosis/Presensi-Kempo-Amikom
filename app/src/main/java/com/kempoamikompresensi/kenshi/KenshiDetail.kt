package com.kempoamikompresensi.kenshi

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
import kotlinx.android.synthetic.main.activity_kenshi_detail.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class KenshiDetail : AppCompatActivity() {

    lateinit var database: DatabaseReference
    var getId: String = "-"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kenshi_detail)

        database = FirebaseDatabase.getInstance().getReference("kenshi")
        setToolbar()
        initUI()
        fetchDataParse()

    }

    private fun fetchDataParse() {

        getId = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val address = intent.getStringExtra("address")
        val phone = intent.getStringExtra("phone")
        val nik = intent.getStringExtra("nik")
        val born = intent.getStringExtra("born")
        val gender = intent.getStringExtra("gender")

        name_tv.text = name
        nik_tv.text = nik
        phone_tv.text = phone
        born_tv.text = born
        address_tv.text = address

        if (gender == "M") {
            gender_tv.text = "Laki-Laki"
        } else {
            gender_tv.text = "Perempuan"
        }

        edit_btn.onClick {
            startActivity(
                intentFor<KenshiCreate>(
                    "id" to getId
                )
            )
        }

    }

    private fun initUI() {

        edit_btn.onClick {
            toast("Perbaru Data Kenshi")
        }
    }

    private fun handleBack() {
        startActivity(
            intentFor<HomepageActivity>(
                "from" to HomepageActivity.KENSHI
            )
        )
    }

    private fun deleteDataKenshi() {

        database.orderByChild("id").equalTo(getId)
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                toast("Failed delete data")
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (item in p0.children) {
                    var data = item.getValue(KenshiEntity::class.java)
                    if (data!!.id == getId) {
                        item.ref.removeValue()
                        toast("Successful delete data")
                        break
                    }

                }
                handleBack()
            }
        })
    }

    private fun setToolbar() {

        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.toolbar)

        action_btn.visibility = View.VISIBLE
        titlebar_tv.text = "Kenshi Detail"
        backbar_btn.onClick {
            handleBack()
        }

        action_btn.onClick {
            deleteDataKenshi()

        }

    }

    override fun onBackPressed() {
        handleBack()
    }

}
