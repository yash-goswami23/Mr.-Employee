package com.example.emp.di

import com.example.emp.data.FireStoreDailyData.FireStoreDailyDataRepository
import com.example.emp.data.FireStoreMonthlyData.FireStoreMonthlyRepository
import com.example.emp.data.FirebaseAuth.AuthRepository
import com.example.emp.data.FirebaseFireStore.FireStoreEmpRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun providerFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun providerAuthRepository(impl: AuthRepository): AuthRepository = impl

    @Provides
    fun providerFireStoreInstance(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun providerFireStoreEmpRepository(impl: FireStoreEmpRepository):FireStoreEmpRepository = impl

    @Provides
    fun providerFireStoreDailyDataRepository(impl: FireStoreDailyDataRepository): FireStoreDailyDataRepository = impl

    @Provides
    fun providerFireStoreMonthlyDataRepository(impl:FireStoreMonthlyRepository):FireStoreMonthlyRepository = impl

}
