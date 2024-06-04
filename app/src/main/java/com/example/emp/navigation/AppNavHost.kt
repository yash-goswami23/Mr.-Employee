package com.example.emp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.emp.Domain.ViewModels.AuthViewModel
import com.example.emp.presentation.screens.AuthScreens.LoginScreen.LoginScreen
import com.example.emp.presentation.screens.AuthScreens.SignUpScreen.SignupScreen
import com.example.emp.presentation.screens.BaseScreens.AfterSignUpScreen.AfterSignUpScreen
import com.example.emp.presentation.screens.BaseScreens.WelcomeScreen.WelcomeScreen
import com.example.emp.Domain.ViewModels.fireStoreViewModel
import com.example.emp.Domain.ViewModels.FireStoreDailyDataViewModel
import com.example.emp.Domain.ViewModels.FireStoreMonthlyDataViewModel
import com.example.emp.presentation.screens.MainScreens.HomeScreen.HomeScreen
import com.example.emp.presentation.screens.MainScreens.ProfileScreen.ProfileScreen
import com.example.emp.presentation.screens.MainScreens.TaskScreen.TaskScreen

@Composable
fun AppNavHost(fireStoreDailyDataViewModel: FireStoreDailyDataViewModel, fireStoreMonthlyMonthlyViewModel: FireStoreMonthlyDataViewModel, fireStoreViewModel: fireStoreViewModel, authViewModel: AuthViewModel, navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "auth") {
        navigation(startDestination = "welcome", route = "auth") {
            composable("welcome") { WelcomeScreen(authViewModel,navController) }
            composable("login") { LoginScreen(authViewModel,navController) }
            composable("signup") { SignupScreen(authViewModel,navController) }
            composable("afterSignUp") { AfterSignUpScreen(fireStoreViewModel,authViewModel,navController) }
        }
        navigation(startDestination = "home", route = "main") {
            composable("home") { HomeScreen(authViewModel,fireStoreDailyDataViewModel,navController) }
            composable("task") { TaskScreen(fireStoreViewModel,authViewModel,fireStoreDailyDataViewModel,fireStoreMonthlyMonthlyViewModel,navController) }
            composable("profile") { ProfileScreen(fireStoreViewModel,authViewModel,navController) }
        }
    }
}