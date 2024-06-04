package com.example.emp.Domain.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emp.Domain.DataClasses.EmpDetail
import com.example.emp.data.FirebaseFireStore.FireStoreEmpRepositoryImpl
import com.example.emp.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class fireStoreViewModel @Inject constructor(
    private val firestoreRepository: FireStoreEmpRepositoryImpl
) : ViewModel() {

    private val _data = MutableLiveData<Resource<EmpDetail?>>()
    val data: LiveData<Resource<EmpDetail?>> = _data

    private val _setDataResult = MutableLiveData<Resource<Void?>>()
    val setDataResult: LiveData<Resource<Void?>> = _setDataResult

    fun fetchEmpData(docId: String) {
        viewModelScope.launch {
            _data.value = Resource.Loading
            _data.value = firestoreRepository.fetchEmpData(docId)
//            _data.value = Resource.Loading
        }
    }

    fun setEmpData(docId: String,data: EmpDetail) {
        viewModelScope.launch {
            _setDataResult.value = Resource.Loading
            _setDataResult.value = firestoreRepository.setEmpData(docId,data)
//            _setDataResult.value = Resource.Loading
        }
    }
}