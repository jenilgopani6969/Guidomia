package com.worldimage.belldemo.api

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.worldimage.belldemo.model.CarResponse
import java.io.IOException
import javax.inject.Inject


class CarApi @Inject constructor(
    private val context : Context
){

    /* reading cars content from json */

    fun getCars(): ArrayList<CarResponse>? {
        val jsonFileString: String
        try {
            jsonFileString = context.assets.open("jsonfile.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            println("json exception $ioException")
            return null
        }
        val listCar = object : TypeToken<List<CarResponse>>() {}.type

        return Gson().fromJson(jsonFileString, listCar)
    }

    /*usually we will be dealing with real apis to we need to write single line code only , for example
    *
    *
    *
    GET("api/cars")
    fun getCars() : Response<List<CarResponse>?
    *
    * */

}