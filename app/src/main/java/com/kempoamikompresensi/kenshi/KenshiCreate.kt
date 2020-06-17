package com.kempoamikompresensi.kenshi

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kempoamikompresensi.R
import com.kempoamikompresensi.dialog.DialogLoading
import com.kempoamikompresensi.home.HomepageActivity
import com.kempoamikompresensi.kenshi.model.KenshiEntity
import com.kempoamikompresensi.util.date.DateSet
import kotlinx.android.synthetic.main.activity_kenshi_create.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast
import java.text.DateFormatSymbols
import java.util.*

class KenshiCreate : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kenshi_create)

        setToolbar()
        initUI()
        database = FirebaseDatabase.getInstance().getReference("kenshi")

    }

    private fun initUI() {

        setBornDate(DateSet.getDateToday())

        create_kenshi_btn.onClick {
            isLoading()
            submitDataKenshi()
        }

        male_rb.onClick {
            onSelectGender(MALE)
        }

        female_rb.onClick {
            onSelectGender(FEMALE)
        }

    }

    private fun submitDataKenshi() {

        val name = name_ed.text.toString()
        val address = address_ed.text.toString()
        val phone = phone_ed.text.toString()
        val nik = nik_ed.text.toString()
        val born = "$YEAR_SELECTED-$MONTH_SELECTED-$DATE_SELECTED"
        val idKenshi = database.push().key.toString()

        isLog("-------------------------------")
        isLog("idKenshi : $idKenshi")
        isLog("nama : $name")
        isLog("address : $address")
        isLog("phone : $phone")
        isLog("nik : $nik")
        isLog("born : $born")
        isLog("SELECTED_GENDER : $SELECTED_GENDER")
        isLog("timestamp : ${DateSet.getTimestampNow()}")
        isLog("descript timestamp : ${DateSet.descripTimestamp(
            DateSet.getTimestampNow())}")

        if (name.isNotEmpty() && address.isNotEmpty() && phone.isNotEmpty()
            && SELECTED_GENDER.isNotEmpty() && born.isNotEmpty() && nik.isNotEmpty()
        ) {

            loadingDialog.dismiss()

            val kenshiData = KenshiEntity(idKenshi, nik, name, phone, address, SELECTED_GENDER, born,  DateSet.getTimestampNow(), DateSet.getTimestampNow())
            database.child(idKenshi)
                .setValue(kenshiData)
                .addOnCompleteListener {
                    toast("Successfull Save Data")
            }

            startActivity(intentFor<HomepageActivity>(
                "from" to HomepageActivity.KENSHI)
            )

        } else {
            loadingDialog.dismiss()
            toast(getString(R.string.please_complete_data))
        }

    }

    private fun onSelectGender(gender: String?) {
        if (gender!!.isNotEmpty()) {
            SELECTED_GENDER = gender
            if (gender == MALE) {
                male_rb.isChecked = true
                female_rb.isChecked = false
            } else {
                male_rb.isChecked = false
                female_rb.isChecked = true
            }
        }
    }

    private fun setBornDate(born: String) {
        DATE_SELECTED = DateSet.selectedDate(born)
        MONTH_SELECTED = DateSet.selectedMonth(born)
        YEAR_SELECTED = DateSet.selectedYear(born)

        addYearToSpinner()
        addMonthToSpinner()
        month_spinner.setSelection(MONTH_SELECTED.toInt() - 1)
        date_spinner.setSelection(DATE_SELECTED.toInt() - 1)
    }

    private fun addDateToSpinner(month: Int, year: Int) {
        val position = date_spinner.selectedItemPosition
        val arrayList: ArrayList<String> = ArrayList()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        val maxDate = calendar.getActualMaximum(Calendar.DATE)
        for (i in 0 until maxDate) {
            arrayList.add(i.plus(1).toString())
        }
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        date_spinner.adapter = arrayAdapter
        date_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                val data = parent!!.getItemAtPosition(position).toString()
                DATE_SELECTED = if (data.toInt() < 10) "0$data" else data
                isLog("selected date : $DATE_SELECTED")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        if (position > arrayList.size - 1) {
            date_spinner.setSelection(arrayList.size - 1)
        } else {
            if (position >= 1) {
                date_spinner.setSelection(position)
                DATE_SELECTED = if (position < 10) "0$position" else position.toString()
            } else {
                date_spinner.setSelection(DATE_SELECTED.toInt() - 1)
            }
            isLog("addDateToSpinner selected : $DATE_SELECTED")
        }
    }

    private fun addMonthToSpinner() {

        val month = DateFormatSymbols.getInstance().months
        val arrayMonth: ArrayList<String> = ArrayList()
        for (i in month.indices) {
            arrayMonth.add(month[i])
        }
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayMonth)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        month_spinner.adapter = arrayAdapter
        month_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val monthSelected = position + 1
                MONTH_SELECTED =
                    if (monthSelected < 10) "0$monthSelected" else monthSelected.toString()
                CAL_MONTH_POS = position
                addDateToSpinner(position, CAL_YEAR_POS)
                isLog("addMonthToSpinner selected $MONTH_SELECTED")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //ga usah ada aksi
            }
        }

    }

    private fun addYearToSpinner() {

        val listYear: MutableList<String> = ArrayList()
        val startYear = 1940
        val totalYear = 110
        for (position in 0 until totalYear) {
            val year = startYear + position
            listYear.add(year.toString())
        }

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listYear)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        year_spinner.adapter = arrayAdapter
        year_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val data = parent!!.getItemAtPosition(position).toString()
                YEAR_SELECTED = data
                CAL_YEAR_POS = position
                addDateToSpinner(CAL_MONTH_POS, position)
                isLog("year selected : $YEAR_SELECTED")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //ga usah ada aksi
            }
        }

        for (item in 0 until listYear.size) {
            if (YEAR_SELECTED.toInt() == listYear[item].toInt()) {
                val yearAdapter = arrayAdapter.getPosition(listYear[item])
                year_spinner.setSelection(yearAdapter)
                break
            }
        }

    }


    private fun setToolbar() {

        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.toolbar)

        titlebar_tv.text = "Daftar Kenshi"
        backbar_btn.onClick {
            startActivity(intentFor<HomepageActivity>())
        }

    }

    private fun isLoading() {
        loadingDialog = DialogLoading(this)
        loadingDialog.setCancelable(false)
        loadingDialog.show()
    }

    private fun isLog(msg: String) {
        Log.e("KenshiCreate", msg)
    }

    companion object {

        val calendar: Calendar = Calendar.getInstance()
        lateinit var database: DatabaseReference
        lateinit var loadingDialog: DialogLoading

        // gender
        var SELECTED_GENDER = ""
        var MALE = "M"
        var FEMALE = "F"

        // date born selected
        var DATE_SELECTED = "-"
        var MONTH_SELECTED = "-"
        var YEAR_SELECTED = "-"
        var CAL_YEAR_POS = 0
        var CAL_MONTH_POS = 0

    }


}
