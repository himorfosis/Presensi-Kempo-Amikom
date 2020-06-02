package com.kempoamikompresensi.kenshi.model

import com.google.firebase.database.IgnoreExtraProperties

class KenshiModel (
    var id: String,
    var nik: String,
    var name: String,
    var phone: String,
    var address : String,
    var gender: String,
    var born: String
)