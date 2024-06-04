package com.example.emp.data.FireStoreMonthlyData

import com.example.emp.Domain.DataClasses.monthlyData
import com.example.emp.data.Resource
import com.example.emp.data.utils.await
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseMonthlyRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): FireStoreMonthlyRepository {
    override suspend fun setMonthlyData(
        docId: String,
        data: monthlyData,
        MonthDoc: String
    ): Resource<String?> {
        return try {
            firestore.collection("Users").document(docId).collection("MonthlyData").document(MonthDoc).set(data).await()
            Resource.Success("Data Saved")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun fetchMonthlyData(
        docId: String,
        MonthDoc: String
    ): Resource<monthlyData?> {
        return try {
            val snapshot = firestore.collection("Users").document(docId).collection("MonthlyData").document(MonthDoc).get().await()
            val data = snapshot.toObject(monthlyData::class.java)
            Resource.Success(data)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

}