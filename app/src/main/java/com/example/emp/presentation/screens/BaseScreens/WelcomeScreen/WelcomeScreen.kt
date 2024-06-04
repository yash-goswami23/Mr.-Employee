package com.example.emp.presentation.screens.BaseScreens.WelcomeScreen


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.emp.R
import com.example.emp.data.Resource
import com.example.emp.presentation.components.*
import com.example.emp.Domain.ViewModels.AuthViewModel


@Composable
fun WelcomeScreen(viewModel: AuthViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val loginFlow = viewModel.loginFlow.collectAsState()
    loginFlow.value.let {
        when(it){
            is Resource.Success -> {
                    navController.navigate("main"){
                        popUpTo("main"){
                            inclusive = true
                    }
                }
            }
            else -> Unit
        }
    }

    Scaffold(topBar = { MyTopAppBar("App Name") },
        containerColor = colorResource(id = R.color.BackgroundColor)) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(color = colorResource(id = R.color.BackgroundColor)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            MyMainCard(painterResource(R.drawable.circleicon),"Welcome"){
                Btn(btnTitle = "Login"){
                    navController.navigate("login")
                }
                Btn(btnTitle = "Create Account"){
                    navController.navigate("signup")
                }
                Btn(btnTitle = "How To Use"){
                    Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show()
                }
                Text(text = "Policy", fontFamily = FontFamily.Default, fontSize = 20.sp, fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .padding(25.dp)
                        .align(Alignment.CenterHorizontally), color = colorResource(id = R.color.TopBarColor))

        }
    }
}
}

