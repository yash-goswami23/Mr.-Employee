package com.example.emp.data

sealed class Resource<out R>{
    object Loading: Resource<Nothing>()
    data class Success<out T>(val data: T): Resource<T>()
    data class Failure(val exception: Exception): Resource<Nothing>()
}