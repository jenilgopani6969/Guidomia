package com.worldimage.belldemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.worldimage.belldemo.model.CarListData
import com.worldimage.belldemo.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {
    fun getVehicleList(): LiveData<List<CarListData>> {
        return appRepository.getVehicleList()
    }

    fun makeApiCall() : ArrayList<CarListData>{
        return appRepository.makeApiCall()
    }
}