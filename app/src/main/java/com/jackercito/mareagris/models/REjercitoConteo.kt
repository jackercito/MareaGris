package com.jackercito.mareagris.models

import androidx.room.ColumnInfo
import androidx.room.Embedded

class REjercitoConteo {
    @Embedded
    var ejercito: Ejercito? = null
    @ColumnInfo(name = "total")
    var total: Int? = null
    @ColumnInfo(name = "totalEmpresa")
    var totalEmpresa: Int? = null
    @ColumnInfo(name = "pintadas")
    var pintadas: Int? = null
    @ColumnInfo(name = "pintadasTotal")
    var pintadasTotal: Int? = null
}