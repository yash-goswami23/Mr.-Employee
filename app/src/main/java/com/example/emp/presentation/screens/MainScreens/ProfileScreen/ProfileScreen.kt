package com.example.emp.presentation.screens.MainScreens.ProfileScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.emp.Domain.DataClasses.EmpDetail
import com.example.emp.R
import com.example.emp.data.Resource
import com.example.emp.presentation.components.*
import com.example.emp.Domain.ViewModels.AuthViewModel
import com.example.emp.Domain.ViewModels.fireStoreViewModel

@Composable
fun ProfileScreen(fireStoreViewModel: fireStoreViewModel, viewModel: AuthViewModel, navController: NavHostController) {

    val context = LocalContext.current
    val fetchDataResult by fireStoreViewModel.data.observeAsState()
    var empData by remember {
        mutableStateOf(EmpDetail())
    }
    val docID = viewModel.currentUser?.email.toString()

    LaunchedEffect(Unit) {
        fireStoreViewModel.fetchEmpData(docID)
    }



    Scaffold(
        topBar = {
                 MyTopAppBar(title = "Profile")
        },
        bottomBar = {
            MyBottomBar(navController = navController)
        },
        containerColor = colorResource(id = R.color.BackgroundColor)
    ) {paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(), contentAlignment = Alignment.TopCenter){
            when (fetchDataResult) {
                is Resource.Loading ->{
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues).background(colorResource(id = R.color.BackgroundColor))){
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.White
                        )
                    }
                }
                is Resource.Success -> {
                    val data = (fetchDataResult as Resource.Success<EmpDetail>).data
                    if (data != null) {
                        empData = data
                        BaseCard(isMain = true, modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 140.dp)) {
                            Column (horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()){
//                    fireStoreViewModel.fetchData(viewModel.currentUser?.email.toString())
                                Spacer(modifier = Modifier.height(50.dp))
                                TextWithDivider(text1 = "Employee Name :", text2 = empData.name)
                                Spacer(modifier = Modifier.height(10.dp))
                                TextWithDivider(text1 = "Employee Mail :", text2 = empData.email)
                                Spacer(modifier = Modifier.height(10.dp))
                                TextWithDivider(text1 = "Monthly Salary :", text2 =empData.monthlySalary)
                                Spacer(modifier = Modifier.height(10.dp))
                                TextWithDivider(text1 = "Company Name :", text2 =empData.compName)
                                Spacer(modifier = Modifier.height(35.dp))
                                Btn(btnTitle = "Logout") {
                                    viewModel.logout()
                                    navController.navigate("auth") {
                                        popUpTo("auth") {
                                            inclusive = true
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(25.dp))
                            }
                        }
                        BaseCard(isMain = false, modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 100.dp)) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.profile),
                                contentDescription = null,
                                tint = colorResource(id = R.color.TopBarColor),
                                modifier = Modifier
                                    .size(70.dp)
                                    .padding(8.dp)
                            )
                        }
                        Toast.makeText(context, "Data Found $data", Toast.LENGTH_SHORT).show()
                        Log.d("ProfileTag", "ProfileScreen: $data")
                    } else {
                        Toast.makeText(context, "No Data Found $data", Toast.LENGTH_SHORT).show()
                        Log.d("ProfileTag", "ProfileScreen: $data")
                    }
                }
                is Resource.Failure -> {
                    val exception = (fetchDataResult as Resource.Failure).exception
                    Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
                    Log.d("ProfileTag", "ProfileScreen: ${exception.message}")
                }
                else -> {
                    // Handle other cases if necessary
                }
            }
        }
    }
}
