package com.worldimage.belldemo.presentation

import com.worldimage.belldemo.domain.model.Car

data class CarListState(
    val isLoading: Boolean = false,
    val cars: List<Car> = emptyList(),
    val error: String = ""
)