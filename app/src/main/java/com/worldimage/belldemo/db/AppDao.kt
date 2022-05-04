package com.worldimage.belldemo.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.worldimage.belldemo.model.CarListData
import dagger.Provides
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Query("SELECT * FROM vehicleList")
    fun getAllRecords(): Flow<List<CarListData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecords(listings: CarListData)

    @Query("DELETE FROM vehicleList")
    fun deleteAllRecords()

}