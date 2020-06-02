package com.kempoamikompresensi.data

import com.kempoamikompresensi.kenshi.model.KenshiModel
import com.kempoamikompresensi.presensi.model.PresensiEntity

object DataSample {

    fun fetchDataKenshi(): List<KenshiModel> {

        return listOf(
            KenshiModel("", "17.01.0001", "Egar Ikhsan", "", "", "", ""),
            KenshiModel("","17.01.0002", "Umar Syaid Himawan","", "", "", ""),
            KenshiModel("","17.01.0003", "Firman Simatupang", "", "", "", ""),
            KenshiModel("","17.01.0004", "Cahya Subiantoro", "", "", "", ""),
            KenshiModel("","17.01.0005", "Banu", "", "", "", ""),
            KenshiModel("","17.01.0006", "Depin Sembiring", "", "", "", ""),
            KenshiModel("","17.01.0007", "Rio", "", "", "", ""),
            KenshiModel("","17.01.0008", "Moza", "", "", "", "")

        )

    }

    fun fetchDataPrensensi(): List<PresensiEntity> {

        return listOf(
            PresensiEntity("","", "17.01.0001",  "Egar Ikhsan",0, 0),
            PresensiEntity("","", "17.01.0002",  "Umar Syaid Himawan",0, 0),
            PresensiEntity("","", "17.01.0003",  "Firman Simatupang",0, 0),
            PresensiEntity("","", "17.01.0004",  "Cahya Subiantoro",0, 0),
            PresensiEntity("","", "17.01.0005", "Banu",0, 0)
        )
    }

}