package com.worldimage.belldemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.worldimage.belldemo.adapter.CarListAdapter
import com.worldimage.belldemo.databinding.ActivityMainBinding
import com.worldimage.belldemo.model.CarListData
import com.worldimage.belldemo.viewmodel.CarListUIState
import com.worldimage.belldemo.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private var carList = listOf<CarListData>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var carListAdapter: CarListAdapter

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupViews()
        lifecycleScope.launch {
            viewModel.fetchCarList()
        }
        loadData()
        setRecyclerView()
    }


    private fun loadData() {
        viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                when (it) {
                    is CarListUIState.Empty -> {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.no_data_available),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    is CarListUIState.Loading -> {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.loading),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is CarListUIState.Loaded -> {
                        carList = it.data
                        carListAdapter.addData(carList)
                    }
                    is CarListUIState.Error -> {
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }.launchIn(lifecycleScope)

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