package com.worldimage.belldemo

import android.content.ClipData
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.worldimage.belldemo.model.CarListData
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jsonFileString = getData(this)
        Log.i("data", jsonFileString!!)
        val gson = Gson()
        val listCar = object : TypeToken<List<CarListData>>() {}.type
        val carList: ArrayList<CarListData> = gson.fromJson(jsonFileString, listCar)
        Log.i("data", carList[0].make)


    }

    private fun getData(context: Context): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open("jsonfile.json").bufferedReader().use { it.readText() }
        } catch (ioException : IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}