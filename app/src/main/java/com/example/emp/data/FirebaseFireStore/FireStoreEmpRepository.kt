package com.example.emp.data.FirebaseFireStore

import com.example.emp.Domain.DataClasses.EmpDetail
import com.example.emp.data.Resource

interface FireStoreEmpRepository {
    suspend fun setEmpData(docId: String,data: EmpDetail,): Resource<Void?>
    suspend fun fetchEmpData(docId:String): Resource<EmpDetail?>
}