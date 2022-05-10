package com.worldimage.belldemo.useCases

import com.worldimage.belldemo.db.CarListData
import com.worldimage.belldemo.repository.CarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class CarUseCase @Inject constructor(
    private val carRepository: CarRepository
) {
    operator fun invoke(): Flow<CarListUIState> = flow {
        emit(CarListUIState.Loading)
        try {
            val cars = carRepository.getVehicleList(true)
            cars?.let { emit(CarListUIState.Success(it)) }
        } catch (e: Exception) {
            emit(CarListUIState.Error("something went wrong, please try again....."))
        }

    }

}

sealed class CarListUIState {
    object Loading : CarListUIState()
    class Success(val data: List<CarListData>?) : CarListUIState()
    class Error(val message: String) : CarListUIState()
}