package com.worldimage.belldemo.domain.use_case

import com.worldimage.belldemo.comman.Resource
import com.worldimage.belldemo.data.remote.dto.toCar
import com.worldimage.belldemo.domain.model.Car
import com.worldimage.belldemo.domain.repository.CarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetCarUseCase @Inject constructor(
    private val repository: CarRepository
) {
    operator fun invoke(): Flow<Resource<List<Car>>> = flow {
        try {
            emit(Resource.Loading<List<Car>>())
            repository.refreshVehicleList()
            val cars = repository.getCars().map { it.toCar() }
           emit(Resource.Success<List<Car>>(cars))
        } catch(e: IOException) {
            emit(Resource.Error<List<Car>>("Couldn't reach server. Check your internet connection."))
        }
    }
}