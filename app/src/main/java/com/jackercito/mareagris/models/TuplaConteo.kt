package com.jackercito.mareagris.models

import androidx.room.ColumnInfo

class TuplaConteo {
    @ColumnInfo(name = "uid")
    var uid: Long? = null

    @ColumnInfo(name = "estado")
    var estado: String? = null
}