package com.worldimage.belldemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.worldimage.belldemo.model.CarListData
import com.worldimage.belldemo.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val appRepository: AppRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow<CarListUIState>(CarListUIState.Empty)
    val uiState: StateFlow<CarListUIState> = _uiState

    private fun refreshVehicleList() = viewModelScope.launch {
        appRepository.refreshVehicleList()
    }

    suspend fun fetchCarList() {
        _uiState.value = CarListUIState.Loading
        try {
            val response = appRepository.getVehicleList().also {
                refreshVehicleList()
            }
            response.collect { carList ->
                _uiState.value = CarListUIState.Loaded(carList)
            }

        } catch (exception: Exception) {
            _uiState.value = CarListUIState.Error("Something went wrong.....")
        }
    }
}

sealed class CarListUIState {
    object Empty : CarListUIState()
    object Loading : CarListUIState()
    class Loaded(val data: List<CarListData>) : CarListUIState()
    class Error(val message: String) : CarListUIState()
}