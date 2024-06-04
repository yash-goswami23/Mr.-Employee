package com.example.emp.data.utils

import android.util.Log
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Task<T>.await() :T{
    return suspendCancellableCoroutine {cout ->
        addOnCompleteListener {
            if(it.exception != null){
                cout.resumeWithException(it.exception!!)
            }else{
                cout.resume(it.result,null)
            }
        }
    }
}
