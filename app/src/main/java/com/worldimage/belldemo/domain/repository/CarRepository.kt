package com.worldimage.belldemo.domain.repository

import android.content.Context
import com.worldimage.belldemo.data.remote.dto.CarDto
import dagger.Provides
import kotlinx.coroutines.flow.Flow


interface CarRepository {

    suspend fun getCars(): List<CarDto>
    suspend fun refreshVehicleList(): ArrayList<CarDto>
}