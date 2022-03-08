package com.worldimage.belldemo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doOnTextChanged
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.worldimage.belldemo.adapter.CarListAdapter
import com.worldimage.belldemo.databinding.ActivityMainBinding
import com.worldimage.belldemo.model.CarListData
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private var carList = ArrayList<CarListData>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        initData()
        setRecyclerView()




    }

    private fun setRecyclerView() {
        Log.i("data", carList.toString())
        val adapter = CarListAdapter(carList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun initData() {

        val jsonFileString = getData(this)
        val gson = Gson()
        val listCar = object : TypeToken<List<CarListData>>() {}.type
        carList = gson.fromJson(jsonFileString, listCar)
        //add data to Room
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