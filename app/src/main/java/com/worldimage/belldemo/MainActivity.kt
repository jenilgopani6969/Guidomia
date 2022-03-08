package com.worldimage.belldemo

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.worldimage.belldemo.adapter.CarListAdapter
import com.worldimage.belldemo.databinding.ActivityMainBinding
import com.worldimage.belldemo.model.CarListData
import java.io.IOException

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private var carList = ArrayList<CarListData>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var carListAdapter: CarListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initData()
        setupViews()
        setRecyclerView()




    }

    private fun setupViews() {
        binding.editMake.setOnQueryTextListener(this)


    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRecyclerView() {
        Log.i("data", carList.toString())
        binding.recyclerView.adapter = carListAdapter
        carListAdapter.notifyDataSetChanged()
    }

    private fun initData() {

        val jsonFileString = getData(this)
        val gson = Gson()
        val listCar = object : TypeToken<List<CarListData>>() {}.type
        carList = gson.fromJson(jsonFileString, listCar)
        carListAdapter = CarListAdapter()
        carListAdapter.addData(carList)
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

    @SuppressLint("NotifyDataSetChanged")
    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.i("data", "$query 1")
        carListAdapter.filter.filter(query)
        carListAdapter.notifyDataSetChanged()
        return false
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onQueryTextChange(newText: String?): Boolean {
        Log.i("data", "$newText 2")
        carListAdapter.filter.filter(newText)
        carListAdapter.notifyDataSetChanged()
        return false
    }


}