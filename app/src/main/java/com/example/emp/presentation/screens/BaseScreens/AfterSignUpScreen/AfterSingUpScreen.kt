package com.example.emp.presentation.screens.BaseScreens.AfterSignUpScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.emp.*
import com.example.emp.Domain.DataClasses.EmpDetail
import com.example.emp.presentation.components.*
import com.example.emp.presentation.components.MyTopAppBar
import com.example.emp.Domain.ViewModels.AuthViewModel
import com.example.emp.Domain.ViewModels.fireStoreViewModel
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation")
@Composable
fun AfterSignUpScreen(fireStoreViewModel: fireStoreViewModel, authViewModel: AuthViewModel, navController: NavHostController) {

    val setDataResult by fireStoreViewModel.setDataResult.observeAsState()
    var mail by remember{ mutableStateOf("") }
    var name by remember{ mutableStateOf("") }
    var compName by remember{ mutableStateOf("") }
    var jobTime by remember{ mutableStateOf("") }
    var salary by remember{ mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    Scaffold(topBar = { MyTopAppBar("App Name") },
        containerColor = colorResource(id = R.color.BackgroundColor)
    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(color = colorResource(id = R.color.BackgroundColor)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            BaseCard(isMain = true){
                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(text = "Enter Detail",fontSize = 55.sp,
                            fontWeight = FontWeight.Light,
                            color = colorResource(
                                id = R.color.TopBarColor),
                            textAlign = TextAlign.Center)
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 3.dp, start = 70.dp, end = 70.dp),
                            thickness = 3.dp,
                            color = colorResource(id = R.color.TopBarColor),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        val clicked = remember { mutableStateOf(true) }
                        DayNightCard(dayIcon = if (!clicked.value) painterResource(id = R.drawable.day) else painterResource(id = R.drawable.fillday),
                            nightIcon = if (clicked.value) painterResource(id = R.drawable.night) else painterResource(id = R.drawable.fillnight) ){
                            clicked.value = !clicked.value
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        compName =  myOutlineTextFiled(img = painterResource(id = R.drawable.compicon),
                            labelText = "Company Name")
                    Spacer(modifier = Modifier.height(8.dp))
                        OutlinedCard(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp, vertical = 1.dp),
                            colors = CardDefaults.outlinedCardColors(
                                containerColor = Color.White,
                                contentColor = Color.Blue
                            ), border = BorderStroke(width = 1.dp, color = colorResource(id = R.color.BackgroundColor))
                            ){
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp), horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically) {
                                Icon(painter = painterResource(id = R.drawable.time), contentDescription = null, modifier = Modifier
                                    .size(25.dp), tint = colorResource(id = R.color.BackgroundColor))
                                Text(text = "Select Job Time Hour",textAlign = TextAlign.Center,
                                    color = colorResource(id = R.color.BackgroundColor),
                                    modifier = Modifier.padding(start = 10.dp))
                               jobTime =  MyDropDownMenu(9,9,modifier = Modifier
                                   .padding(horizontal = 10.dp, vertical = 10.dp)
                                   .fillMaxWidth()
                                   .padding(start = 10.dp, end = 15.dp)
                                   .shadow(1.dp)).toString()
                            }
                        }
                       /* jobTIme = myOutlineTextFiled(img = painterResource(id = R.drawable.time),
                            labelText = "Job Time")*/
                        salary = myOutlineTextFiled(img = painterResource(id = R.drawable.wallet),
                            labelText = "Monthly Salary", KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Btn(btnTitle = "Submit") {
                            mail = authViewModel.currentUser?.email.toString()
                            name = authViewModel.currentUser?.displayName.toString()

                         val empDetail = EmpDetail(name,mail,salary,jobTime,compName)
                            if(salary.isNotEmpty() && jobTime != "0") {
                                scope.launch {
                                    fireStoreViewModel.setEmpData(mail, empDetail)
                                }
                                if(setDataResult != null){
                                    Toast.makeText(context, "Try Again", Toast.LENGTH_SHORT).show()
                                }else{
                                    navController.navigate("main")
                                    Toast.makeText(
                                        context,
                                        "Data added successfully! ",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }else{
                                Toast.makeText(context, "Please fill Salary or Working Hour", Toast.LENGTH_SHORT).show()
                            }
//                        navController.navigate(HomeScreen(navController = navController))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

