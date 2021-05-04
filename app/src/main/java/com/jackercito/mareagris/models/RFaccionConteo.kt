package com.jackercito.mareagris.models

import androidx.room.ColumnInfo
import androidx.room.Embedded

class RFaccionConteo {
    @Embedded
    var faccion: Faccion? = null
    @ColumnInfo(name = "total")
    var total: Int? = null
    @ColumnInfo(name = "totalEmpresa")
    var totalEmpresa: Int? = null
    @ColumnInfo(name = "pintadas")
    var pintadas: Int? = null
    @ColumnInfo(name = "pintadasTotal")
    var pintadasTotal: Int? = null
}