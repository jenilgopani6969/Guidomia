package com.worldimage.belldemo.data.remote.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.worldimage.belldemo.data.remote.dto.CarDto
import com.worldimage.belldemo.data.db.AppDao
import com.worldimage.belldemo.domain.repository.CarRepository
import java.io.IOException
import javax.inject.Inject

class CarRepositoryImpl @Inject constructor(
    private val appDao: AppDao, private val context: Context
) : CarRepository {

    private var carList = ArrayList<CarDto>()

    override suspend fun getCars(): List<CarDto> {
        return appDao.getAllRecords()
    }

    private fun insertVehicleList(listings: CarDto) {
        appDao.insertRecords(listings)
    }

    override suspend fun refreshVehicleList(): ArrayList<CarDto> {
        val jsonFileString = getData(context)
        val gson = Gson()
        val listCar = object : TypeToken<List<CarDto>>() {}.type
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