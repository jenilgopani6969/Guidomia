package com.worldimage.belldemo.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.worldimage.belldemo.db.AppDao
import com.worldimage.belldemo.model.CarListData
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Module
@DisableInstallInCheck
class AppRepository @Inject constructor(private val appDao: AppDao, private val context: Context) {

    private var carList = ArrayList<CarListData>()

    fun getVehicleList(): Flow<List<CarListData>> = appDao.getAllRecords()

    private fun insertVehicleList(listings: CarListData) {
        appDao.insertRecords(listings)
    }

    fun refreshVehicleList(): ArrayList<CarListData> {
        val jsonFileString = getData(context)
        val gson = Gson()
        val listCar = object : TypeToken<List<CarListData>>() {}.type
        carList = gson.fromJson(jsonFileString, listCar)

        appDao.deleteAllRecords()
        carList.forEach {
            insertVehicleList(it)
        }

        return carList
    }

    private fun getData(context: Context): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open("jsonfile.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}