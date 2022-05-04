package com.worldimage.belldemo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.worldimage.belldemo.comman.Resource
import com.worldimage.belldemo.domain.use_case.GetCarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getCarUseCase: GetCarUseCase
) :
    ViewModel() {

    private val _state = MutableStateFlow(CarListState())
    val state: StateFlow<CarListState> = _state

    init {
        getCars()
    }

    private fun getCars() {
        getCarUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CarListState(cars = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = CarListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = CarListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}

