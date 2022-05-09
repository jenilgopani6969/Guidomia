package com.worldimage.belldemo.useCases

import com.worldimage.belldemo.db.CarListData
import com.worldimage.belldemo.repository.CarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CarUseCase @Inject constructor(
    private val carRepository: CarRepository
) {
    operator fun invoke(): Flow<CarListUIState> = flow {
        val cars = carRepository.getVehicleList()
        cars?.let { emit(CarListUIState.Success(it)) }

    }

}

sealed class CarListUIState {
    object Loading : CarListUIState()
    class Success(val data: List<CarListData>?) : CarListUIState()
    class Error(val message: String) : CarListUIState()
}