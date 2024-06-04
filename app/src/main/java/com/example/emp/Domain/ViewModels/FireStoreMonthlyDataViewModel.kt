package com.example.emp.Domain.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emp.Domain.DataClasses.monthlyData
import com.example.emp.data.FireStoreMonthlyData.FirebaseMonthlyRepositoryImpl
import com.example.emp.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FireStoreMonthlyDataViewModel @Inject constructor(
    private val repository: FirebaseMonthlyRepositoryImpl
)  : ViewModel()
{


    private val _data = MutableLiveData<Resource<monthlyData?>>()
    val data: LiveData<Resource<monthlyData?>> = _data

    private val _setDataResult = MutableLiveData<Resource<String?>>()
    val setDataResult: LiveData<Resource<String?>> = _setDataResult

    fun fetchMonthlyData(docId: String,MonthlyDataID:String) {
        viewModelScope.launch {
            _data.value = Resource.Loading
            _data.value = repository.fetchMonthlyData(docId,MonthlyDataID)
//            _data.value = Resource.Loading
        }
    }

    fun setMonthlyData(docId: String, data: monthlyData, monthlyDataID: String) {
        viewModelScope.launch {
            _setDataResult.value = Resource.Loading
            _setDataResult.value = repository.setMonthlyData(docId,data,monthlyDataID)
//            _setDataResult.value = Resource.Loading
        }
    }

}