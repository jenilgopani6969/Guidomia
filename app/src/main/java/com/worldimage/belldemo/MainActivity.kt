package com.worldimage.belldemo

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.worldimage.belldemo.adapter.CarListAdapter
import com.worldimage.belldemo.databinding.ActivityMainBinding
import com.worldimage.belldemo.model.CarListData
import com.worldimage.belldemo.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private var carList = listOf<CarListData>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var carListAdapter: CarListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupViews()
        initViewModel()
        setRecyclerView()
    }

    @SuppressLint
    private fun initViewModel() {
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getVehicleList().observe(this, Observer<List<CarListData>> {
            if (it != null){
                carList = it
            }
        })
        carList = viewModel.makeApiCall()
    }

    private fun setupViews() {
        binding.editMake.setOnQueryTextListener(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRecyclerView() {
        carListAdapter = CarListAdapter()
        carListAdapter.addData(carList)
        binding.recyclerView.adapter = carListAdapter
        carListAdapter.notifyDataSetChanged()
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