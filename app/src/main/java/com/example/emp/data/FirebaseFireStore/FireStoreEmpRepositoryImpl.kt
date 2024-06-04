package com.example.emp.data.FirebaseFireStore

import com.example.emp.Domain.DataClasses.EmpDetail
import com.example.emp.data.Resource
import com.example.emp.data.utils.await
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FireStoreEmpRepositoryImpl  @Inject constructor(
    private val firestore: FirebaseFirestore
) : FireStoreEmpRepository{

    override suspend fun setEmpData(docId: String,data: EmpDetail): Resource<Void?> {
        return try {
            firestore.collection("Users").document(docId).set(data).await()
            Resource.Success(null)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
    override suspend fun fetchEmpData(docId: String): Resource<EmpDetail?> {
        return try {
            val snapshot = firestore.collection("Users").document(docId).get().await()
            val data = snapshot.toObject(EmpDetail::class.java)
            Resource.Success(data)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }



}