package com.worldimage.belldemo.model


data class CarResponse(
    val consList: List<String>,
    val customerPrice: Int,
    val make: String,
    val marketPrice: Int,
    val model: String,
    val prosList: List<String>,
    val rating: Int
)