package com.example.emp.presentation.screens.AuthScreens.SignUpScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.emp.R
import com.example.emp.data.Resource
import com.example.emp.presentation.components.*
import com.example.emp.Domain.ViewModels.AuthViewModel

@Composable
fun SignupScreen(viewModel: AuthViewModel, navController: NavHostController) {

    var fullName by remember{ mutableStateOf("") }
    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    val context = LocalContext.current

    val signupFlow = viewModel.signupFlow.collectAsState()

    Scaffold(topBar = { MyTopAppBar("App Name") },
        containerColor = colorResource(id = R.color.BackgroundColor)
    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(color = colorResource(id = R.color.BackgroundColor)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround) {
            Spacer(modifier = Modifier.height(0.dp))
            MyMainCard(painterResource(R.drawable.circleicon),"Create Account"){
               fullName =  myOutlineTextFiled(img = painterResource(id = R.drawable.usericon),labelText = "Enter Full Name")
                email = myOutlineTextFiled(img = painterResource(id = R.drawable.usericon),labelText = "Enter Your Email",
                    KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                password = myOutlineTextFiledPass(img = painterResource(id = R.drawable.passicon),labelText = "Create Password")
                Btn(btnTitle = "Sign-Up") {
//                    navController.navigate("afterSignUp")
                    if(fullName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty())
                    viewModel.signup(fullName,email,password)
                    else
                        Toast.makeText(context, "Please Fill All Fields", Toast.LENGTH_SHORT).show()
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
            MiniCard(
                firstIcon = painterResource(id = R.drawable.gmailicon),
                textView = " LOGIN "){
                navController.navigate("login")
            }
            signupFlow.value.let {
                when(it){
                    is Resource.Failure -> {
                        Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
                    }
                    Resource.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                    is Resource.Success -> {
                        LaunchedEffect(Unit) {
                            navController.navigate("afterSignUp"){
                                popUpTo("afterSignUp"){
                                    inclusive = true
                                }
                            }
                        }
                    }
                    else -> Unit
                }
            }
        }

    }

}