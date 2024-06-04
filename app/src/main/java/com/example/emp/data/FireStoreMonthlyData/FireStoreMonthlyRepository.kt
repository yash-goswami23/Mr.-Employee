package com.example.emp.data.FireStoreMonthlyData

import com.example.emp.Domain.DataClasses.monthlyData
import com.example.emp.data.Resource

interface FireStoreMonthlyRepository {
    suspend fun setMonthlyData(docId: String, data: monthlyData, MonthDoc:String): Resource<String?>
    suspend fun fetchMonthlyData(docId:String,MonthDoc: String): Resource<monthlyData?>
}