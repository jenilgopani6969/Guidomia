package com.worldimage.belldemo.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.worldimage.belldemo.data.remote.dto.CarDto
@Dao
interface AppDao {

    @Query("SELECT * FROM vehicleList")
    fun getAllRecords(): List<CarDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecords(listings: CarDto)

    @Query("DELETE FROM vehicleList")
    fun deleteAllRecords()

}