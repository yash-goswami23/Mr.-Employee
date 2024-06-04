package com.example.emp.Domain.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emp.Domain.DataClasses.DailyData
import com.example.emp.data.FireStoreDailyData.FireStoreDailyRepositoryImpl
import com.example.emp.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FireStoreDailyDataViewModel @Inject constructor(
    private val repository: FireStoreDailyRepositoryImpl
)  : ViewModel()
{

    private val _data = MutableLiveData<Resource<List<DailyData>>>()
    val data: LiveData<Resource<List<DailyData>>> = _data

    private val _setDataResult = MutableLiveData<Resource<Void?>>()
    val setDataResult: LiveData<Resource<Void?>> = _setDataResult

    fun fetchDailyData(docId: String) {
        viewModelScope.launch {
            _data.value = Resource.Loading
//            _data.value = repository.fetchDailyData(docId,DailyDataID)
            _data.value = repository.fetchDailyData(docId)
//            _data.value = Resource.Loading
        }
    }

    fun setDailyData(docId: String,data: DailyData,DailyDataID: String) {
        viewModelScope.launch {
            _setDataResult.value = Resource.Loading
            _setDataResult.value = repository.setDailyData(docId,data,DailyDataID)
//            _setDataResult.value = Resource.Loading
        }
    }

}