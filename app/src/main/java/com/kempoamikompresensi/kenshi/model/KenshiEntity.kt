package com.kempoamikompresensi.kenshi.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class KenshiEntity (
    var id: String? = "",
    var nik: String? = "",
    var name: String? = "",
    var phone: String? = "",
    var address : String? = "",
    var gender: String? = "",
    var born: String? = "",
    var created_at: Long? = 0,
    var update_at: Long? = 0
)