package com.worldimage.belldemo.model

data class CarListData(
    val consList: List<String>,
    val customerPrice: Int,
    val make: String,
    val marketPrice: Int,
    val model: String,
    val prosList: List<Any>,
    val rating: Int
)