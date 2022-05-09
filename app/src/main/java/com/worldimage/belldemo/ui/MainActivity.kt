package com.worldimage.belldemo.ui

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.worldimage.belldemo.R
import com.worldimage.belldemo.adapter.CarListAdapter
import com.worldimage.belldemo.databinding.ActivityMainBinding
import com.worldimage.belldemo.db.CarListData
import com.worldimage.belldemo.useCases.CarListUIState
import com.worldimage.belldemo.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), SearchView.OnQueryTextListener {

    private var carList = listOf<CarListData>()
    private lateinit var binding: ActivityMainBinding
    private var carListAdapter: CarListAdapter = CarListAdapter()

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.root
        setupViews()
        setRecyclerView()
        loadData()
    }


    private fun loadData() {
        viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).onEach {
                when (it) {
                    is CarListUIState.Loading -> {
                        Toast.makeText(applicationContext, getString(R.string.loading), Toast.LENGTH_SHORT).show()
                    }
                    is CarListUIState.Success -> {
                        it.data?.let{ cars ->
                           updateUI(cars)
                        }

                    }
                    is CarListUIState.Error -> {
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }.launchIn(lifecycleScope)
    }

    private fun updateUI(cars: List<CarListData>) {
        setRecyclerView()
        carList =  cars
        carListAdapter.addData(carList)
    }


    private fun setupViews() {
        binding.editMake.setOnQueryTextListener(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRecyclerView() {
        carListAdapter = CarListAdapter()
        with(binding.recyclerView){
            adapter = carListAdapter
            carListAdapter.notifyDataSetChanged()
        }

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