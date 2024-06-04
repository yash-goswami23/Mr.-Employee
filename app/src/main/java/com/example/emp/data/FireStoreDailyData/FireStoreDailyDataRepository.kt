package com.example.emp.data.FireStoreDailyData

import com.example.emp.Domain.DataClasses.DailyData
import com.example.emp.data.Resource

interface FireStoreDailyDataRepository {
    suspend fun setDailyData(docId: String, data: DailyData,DateDoc:String): Resource<Void?>
    suspend fun fetchDailyData(docId:String): Resource<List<DailyData>>

}