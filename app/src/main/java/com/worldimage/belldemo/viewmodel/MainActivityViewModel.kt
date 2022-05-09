package com.worldimage.belldemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.worldimage.belldemo.useCases.CarListUIState
import com.worldimage.belldemo.useCases.CarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getCarUseCase: CarUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CarListUIState>(CarListUIState.Loading)
    val uiState: StateFlow<CarListUIState> = _uiState

    init {
        getCars()
    }

    private fun getCars() {
        getCarUseCase.invoke().onEach { state ->
            when (state) {
                is CarListUIState.Success -> {
                    _uiState.value = CarListUIState.Success(state.data)
                }
                is CarListUIState.Error -> {
                    _uiState.value = CarListUIState.Error("something went wrong")
                }
                is CarListUIState.Loading -> {
                    _uiState.value = CarListUIState.Loading
                }
            }
        }.launchIn(viewModelScope)
    }

}

