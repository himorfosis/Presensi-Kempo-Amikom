package com.kempoamikompresensi.presensi

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import com.kempoamikompresensi.R
import kotlinx.android.synthetic.main.activity_scan.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import com.google.zxing.Result
import com.kempoamikompresensi.dialog.DialogPresensiResult
import com.kempoamikompresensi.home.HomepageActivity
import com.kempoamikompresensi.kenshi.model.KenshiEntity
import com.kempoamikompresensi.presensi.model.PresensiEntity
import com.kempoamikompresensi.util.date.DateSet
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast


class ScanPresensi : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private lateinit var mScannerView: ZXingScannerView
    lateinit var databaseKenshi: DatabaseReference
    lateinit var databasePresensi: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        databasePresensi = FirebaseDatabase.getInstance().getReference("presensi")
        databaseKenshi = FirebaseDatabase.getInstance().getReference("kenshi")
        initUI()
        initScannerView()
    }

    private fun initUI() {
        close_scan_img.setOnClickListener {
            startActivity(intentFor<HomepageActivity>())
        }
    }

    private fun initScannerView() {

        mScannerView = ZXingScannerView(this) // Programmatically initialize the scanner view
        scan_frame_layout.addView(mScannerView) // Set the scanner view as the content view

    }

    override fun onStart() {
        mScannerView.startCamera()
        doRequestPermissionUserCamera()
        super.onStart()
    }


    public override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView.startCamera()          // Start camera on resume
    }

    public override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()           // Stop camera on pause
    }

    private fun doRequestPermissionUserCamera() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            100 -> {
                initScannerView()
            }
            else -> {
                /* nothing to do in here */
            }
        }
    }


    override fun handleResult(rawResult: Result) {
        checkDataScanPresensi(rawResult.text)
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    private fun checkDataScanPresensi(resultScan: String) {

        databaseKenshi.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                isFailure()
            }

            override fun onDataChange(p0: DataSnapshot) {

                if (p0!!.exists()) {

                    var statusPresensi = false

                    for (it in p0.children) {
                        var item = it.getValue(KenshiEntity::class.java)
                        if (resultScan == item!!.nik) {
                            statusPresensi = true
                            onSuccessScan(item)
                            break
                        }
                    }

                    if (statusPresensi == false) {
                        onFailedScan()
                    }

                } else {
                    isFailure()
                }

            }
        })

    }

    private fun isFailure() {
        isLog("Failure")
        toast("Failed Presensi")
        startActivity(intentFor<HomepageActivity>())
    }

    private fun onFailedScan() {
        isLog("Failed")
        val ft = supportFragmentManager.beginTransaction()
        val dialog = DialogPresensiResult("", false)
        dialog.isCancelable
        dialog.show(ft, "dialog")
    }

    private fun onSuccessScan(item: KenshiEntity) {
        isLog("Success")

        val idPresensi = databasePresensi.push().key.toString()
        val kenshiData = PresensiEntity(idPresensi, item.id, item.nik, item.name, DateSet.getDatestampToday(), DateSet.getTimestampNow())
        databasePresensi.child(idPresensi)
            .setValue(kenshiData)
            .addOnCompleteListener {
                val ft = supportFragmentManager.beginTransaction()
                val dialog = DialogPresensiResult(item.name!!, true)
                dialog.isCancelable
                dialog.show(ft, "dialog")
            }

    }

    private fun isLog(msg: String) {
        Log.e("ScanActivity", msg)
    }

}
