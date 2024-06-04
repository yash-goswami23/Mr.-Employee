package com.example.emp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.rememberNavController
import com.example.emp.navigation.AppNavHost
import com.example.emp.Domain.ViewModels.AuthViewModel
import com.example.emp.Domain.ViewModels.fireStoreViewModel
import com.example.emp.Domain.ViewModels.FireStoreDailyDataViewModel
import com.example.emp.Domain.ViewModels.FireStoreMonthlyDataViewModel
import com.example.emp.presentation.ui.theme.EmpTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel by viewModels<AuthViewModel>()
    private val firebaseFireStoreViewModel by viewModels<fireStoreViewModel>()
    private val firebaseMonthlyViewModel by viewModels<FireStoreMonthlyDataViewModel>()
    private val fireStoreDailyDataViewModel by viewModels<FireStoreDailyDataViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmpTheme {
                 val navController = rememberNavController()
                 val view = LocalView.current
                 if (!view.isInEditMode) {
                     view.setBackgroundColor(colorResource(id = R.color.white).toArgb())
                 }
                enableEdgeToEdge()
                AppNavHost(
                    fireStoreDailyDataViewModel = fireStoreDailyDataViewModel,
                    fireStoreMonthlyMonthlyViewModel = firebaseMonthlyViewModel,
                    fireStoreViewModel = firebaseFireStoreViewModel,
                    authViewModel = authViewModel
                )

            }
        }

    }
}


/*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel by viewModels<AuthViewModel>()
    private val firebaseFireStoreViewModel by viewModels<fireStoreViewModel>()
    private val firebaseMonthlyViewModel by viewModels<FireStoreMonthlyDataViewModel>()
    private val fireStoreDailyDataViewModel by viewModels<FireStoreDailyData
 */