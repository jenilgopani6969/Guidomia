package com.worldimage.belldemo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter


@Entity(tableName = "vehicleList")
data class CarListData(
    @ColumnInfo(name = "consList")val consList: List<String>,
    @ColumnInfo(name = "customerPrice")val customerPrice: Int,
    @ColumnInfo(name = "make")val make: String,
    @ColumnInfo(name = "marketPrice")val marketPrice: Int,
    @ColumnInfo(name = "model")val model: String,
    @ColumnInfo(name = "prosList")val prosList: List<String>,
    @ColumnInfo(name = "rating")val rating: Int
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("table_id") var table_id: Int = 0
}

class StringListConverter {
    @TypeConverter
    fun fromString(stringListString: String): List<String> {
        return stringListString.split(",").map { it }
    }

    @TypeConverter
    fun toString(stringList: List<String>): String {
        return stringList.joinToString(separator = ",")
    }
}