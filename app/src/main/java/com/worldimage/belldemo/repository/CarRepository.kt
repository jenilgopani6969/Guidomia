package com.worldimage.belldemo.repository

import com.worldimage.belldemo.api.CarApi
import com.worldimage.belldemo.db.CarDao
import com.worldimage.belldemo.db.CarListData
import com.worldimage.belldemo.model.CarResponse
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Module
@DisableInstallInCheck
class CarRepository @Inject constructor(
    private val carDao: CarDao,
    private val carApi: CarApi
) {

    private fun carFromResponse(
        response: CarResponse,
    ) = CarListData(
        consList = response.consList,
        customerPrice = response.customerPrice,
        make = response.make,
        marketPrice = response.marketPrice,
        model = response.model,
        prosList = response.prosList,
        rating = response.rating
    )


    suspend fun getVehicleList(forceRefresh: Boolean? = false): List<CarListData>? {
        if (forceRefresh == true) {
            withContext(Dispatchers.IO) {
                delay(2000L)
                val cars = carApi.getCars()
                val carsList = arrayListOf<CarListData>()
                carDao.deleteAllCars()
                cars?.forEach { carResponse ->
                    carsList.add(carFromResponse(carResponse))
                }
                carDao.insertCars(carsList)
            }
        }
        return  carDao.getAllCars()
    }

}