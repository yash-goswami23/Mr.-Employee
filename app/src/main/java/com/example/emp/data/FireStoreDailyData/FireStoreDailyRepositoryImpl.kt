package com.example.emp.data.FireStoreDailyData

import com.example.emp.Domain.DataClasses.DailyData
import com.example.emp.data.Resource
import com.example.emp.data.utils.await
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FireStoreDailyRepositoryImpl  @Inject constructor(
    private val firestore: FirebaseFirestore
) : FireStoreDailyDataRepository {

    override suspend fun setDailyData(
        docId: String,
        data: DailyData,
        DateDoc: String
    ): Resource<Void?> {
        return try {
            firestore.collection("Users").document(docId)
                .collection("dailyData").document(DateDoc).set(data).await()
            Resource.Success(null)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun fetchDailyData(docId: String):  Resource<List<DailyData>> {
        /*
          .collection("users").document(docId).collection("dailyData").orderBy("date", com.google.firebase.firestore.Query.Direction.DESCENDING)
        .limit(7)
         */

        return try {
            val snapshot = firestore.collection("Users")
                .document(docId)
                .collection("dailyData")
                .orderBy("date", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .limit(7)
                .get()
                .await()
            if(snapshot.isEmpty){
                Resource.Failure(Exception("No data"))
            }else {
//                val data = snapshot.toObjects(DailyData::class.java)
//                Resource.Success(data)
                val data = snapshot.documents.map { document ->
                    document.toObject(DailyData::class.java) ?: DailyData()
                }
                Resource.Success(data)
            }

//            val data = snapshot.documents[0].toObject(DailyData::class.java)
//            val data = snapshot.toObject(DailyData::class.java)

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}