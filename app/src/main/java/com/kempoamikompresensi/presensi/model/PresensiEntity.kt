package com.kempoamikompresensi.presensi.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class PresensiEntity (
    var id: String? ="",
    var id_kenshi: String? ="",
    var nik: String? ="",
    var name: String? ="",
    var date: Long? = 0,
    var datetime: Long? = 0
)