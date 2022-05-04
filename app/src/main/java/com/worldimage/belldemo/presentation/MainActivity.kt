package com.worldimage.belldemo.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.worldimage.belldemo.R
import com.worldimage.belldemo.databinding.ActivityMainBinding
import com.worldimage.belldemo.domain.model.Car
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private var carList = listOf<Car>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var carListAdapter: CarListAdapter

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupViews()

        val state = viewModel.state.value

        //show progress dialog
        if(state.isLoading) {
            Toast.makeText(
                applicationContext,R.string.loading,
                Toast.LENGTH_SHORT
            ).show()
        }

        if(state.error.isNotBlank()) {
            Toast.makeText(
                applicationContext,state.error,
                Toast.LENGTH_SHORT
            ).show()
        }
        setRecyclerView()
        carList = state.cars
        carListAdapter.addData(carList)

    }

    private fun setupViews() {
        binding.editMake.setOnQueryTextListener(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRecyclerView() {
        carListAdapter = CarListAdapter()
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