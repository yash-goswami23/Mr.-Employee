package com.example.emp.presentation.screens.MainScreens.HomeScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.emp.Domain.DataClasses.DailyData
import com.example.emp.R
import com.example.emp.data.Resource
import com.example.emp.presentation.components.*
import com.example.emp.Domain.ViewModels.AuthViewModel
import com.example.emp.Domain.ViewModels.FireStoreDailyDataViewModel
import java.time.LocalDate
@Composable
fun HomeScreen(authViewModel: AuthViewModel, fireStoreDaillyDataViewModel: FireStoreDailyDataViewModel, navController: NavHostController) {

    val setDataResult by fireStoreDaillyDataViewModel.setDataResult.observeAsState()
    val context = LocalContext.current
    val fetchDataResult by fireStoreDaillyDataViewModel.data.observeAsState()
    var dailyDataList by remember { mutableStateOf<List<DailyData>>(emptyList()) }
//    var fetchDataResult by remember { mutableStateOf<Resource<List<DailyData>>?>(null) }
    val docID = authViewModel.currentUser?.email.toString()
    val currentDate = LocalDate.now().toString()
//    val currentDate = "2024-06-04"
    var change by remember { mutableStateOf(true) }
    val callScope = rememberCoroutineScope()
    val currentDay = LocalDate.now().dayOfWeek.toString()

    LaunchedEffect(Unit) {
            fireStoreDaillyDataViewModel.fetchDailyData(docID)
    }
    when(fetchDataResult){
        is Resource.Loading -> {

        }
        is Resource.Success -> {
            LaunchedEffect(Unit) {
                Toast.makeText(context, "Data Fetched", Toast.LENGTH_SHORT).show()
                if (dailyDataList.isEmpty()){
                    dailyDataList = (fetchDataResult as Resource.Success<List<DailyData>>).data
                }else{
                    Toast.makeText(context, "Data List Not Empty" , Toast.LENGTH_SHORT).show()
                }
                val isExist = dailyDataList.any{it.date == currentDate}
                if(isExist){
                    Log.d("isExists","Date is Exists $dailyDataList")
                }else {
                    fireStoreDaillyDataViewModel.setDailyData(docID, DailyData(date = currentDate, selectedOtp = "", day = currentDay, overTime = 9), currentDate)
                    Log.d("isExists","Date Not Exists $dailyDataList")
                }
            }
        }
        is Resource.Failure -> {
            val exception = (fetchDataResult as Resource.Failure).exception
            LaunchedEffect(Unit) {
                fireStoreDaillyDataViewModel.setDailyData(docID, DailyData(date = currentDate, selectedOtp = "", day = currentDay, overTime = 9), currentDate)
            }
            Toast.makeText(context, "Data Not Fetched ${exception.message}", Toast.LENGTH_SHORT).show()
        }
        else -> {
            Toast.makeText(context, "Data Are Null123", Toast.LENGTH_SHORT).show()
        }
    }
    when(setDataResult){
        is Resource.Loading -> {

        }
        is Resource.Success -> {
            LaunchedEffect(Unit) {
                fireStoreDaillyDataViewModel.fetchDailyData(docID)
            }
        }
        is Resource.Failure -> {
            val exception = (fetchDataResult as Resource.Failure).exception
            Toast.makeText(context, "Data Not Fetched $exception", Toast.LENGTH_SHORT).show()
        }
        else -> {
            LaunchedEffect(Unit) {
                fireStoreDaillyDataViewModel.fetchDailyData(docID)
                Toast.makeText(context, "Data Are Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }



    Scaffold(
        topBar = {
            MyTopAppBar(title = "Home")
        },
        bottomBar = {
            MyBottomBar(navController = navController)
        },
        containerColor = colorResource(id = R.color.BackgroundColor)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            this.item {
                when(fetchDataResult){
                    is Resource.Loading -> {
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
                        dailyDataList = (fetchDataResult as Resource.Success<List<DailyData>>).data
                        val size = dailyDataList.size
                        if(size > 1){
                            dailyDataList.forEach {dailyData ->
                                Spacer(modifier = Modifier.height(20.dp))
                                if(change){
                                    MainHomeCard(
                                        dailyData,
                                        shape1 = RoundedCornerShape(
                                            topEnd = 0.dp,
                                            bottomEnd = 0.dp,
                                            topStart = 15.dp,
                                            bottomStart = 15.dp
                                        ),
                                        shape2 = RoundedCornerShape(
                                            bottomEnd = 0.dp,
                                            topEnd = 0.dp,
                                            topStart = 0.dp,
                                            bottomStart = 15.dp
                                        ),
                                        shape3 = RoundedCornerShape(
                                            topEnd = 0.dp,
                                            bottomEnd = 0.dp,
                                            topStart = 15.dp,
                                            bottomStart = 0.dp
                                        ),
                                        modifier = Modifier.padding(
                                            end = 0.dp,
                                            top = 0.dp,
                                            bottom = 0.dp,
                                            start = 80.dp
                                        ),
                                        modifier2 = Modifier.padding(
                                            end = 0.dp,
                                            top = 0.dp,
                                            bottom = 0.dp,
                                            start = 40.dp
                                        )
                                    ) {
                                        if(it.selectedOtp != ""){
                                            fireStoreDaillyDataViewModel.setDailyData(docID,it,currentDate)
                                            when(setDataResult){
                                                is Resource.Success -> {
                                                    fireStoreDaillyDataViewModel.fetchDailyData(docID)
                                                    Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show()
                                                }
                                                is Resource.Failure -> {
                                                    val exception = (setDataResult as Resource.Failure).exception
                                                    Toast.makeText(context, "Data Not Saved $exception", Toast.LENGTH_SHORT).show()
                                                }
                                                else -> {
                                                    fireStoreDaillyDataViewModel.fetchDailyData(docID)
                                                    Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        }else{
                                            Toast.makeText(context, "Please Select Options", Toast.LENGTH_SHORT).show()
                                        }
                                    }//left
                                    Spacer(modifier = Modifier.height(20.dp))
                                    change = !change
                                }
                                else{
                                    MainHomeCard(
                                        dailyData,
                                        shape1 = RoundedCornerShape(
                                            topEnd = 15.dp,
                                            bottomEnd = 15.dp,
                                            topStart = 0.dp,
                                            bottomStart = 0.dp
                                        ),
                                        shape2 = RoundedCornerShape(
                                            bottomEnd = 15.dp,
                                            topEnd = 0.dp,
                                            topStart = 0.dp,
                                            bottomStart = 0.dp
                                        ),
                                        shape3 = RoundedCornerShape(
                                            topEnd = 15.dp,
                                            bottomEnd = 0.dp,
                                            topStart = 0.dp,
                                            bottomStart = 0.dp
                                        ),
                                        modifier = Modifier.padding(
                                            end = 80.dp,
                                            top = 0.dp,
                                            bottom = 0.dp,
                                            start = 0.dp
                                        ),
                                        modifier2 = Modifier.padding(
                                            end = 40.dp,
                                            top = 0.dp,
                                            bottom = 0.dp,
                                            start = 0.dp
                                        )
                                    ) {
                                        if(it.selectedOtp != ""){
                                            fireStoreDaillyDataViewModel.setDailyData(docID,it,currentDate)
                                            when(setDataResult){
                                                is Resource.Loading -> {}
                                                is Resource.Success -> {
                                                    fireStoreDaillyDataViewModel.fetchDailyData(docID)
                                                    Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show()
                                                }
                                                is Resource.Failure -> {
                                                    val exception = (setDataResult as Resource.Failure).exception
                                                    Toast.makeText(context, "Data Not Saved $exception", Toast.LENGTH_SHORT).show()
                                                }
                                                else -> {
                                                    fireStoreDaillyDataViewModel.fetchDailyData(docID)
                                                    Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        }else{
                                            Toast.makeText(context, "Please Select Options", Toast.LENGTH_SHORT).show()
                                        }
                                    }//left
                                    change = !change
                                    Spacer(modifier = Modifier.height(20.dp))
                                }
                            }
                        }else{
                            val dailyData = dailyDataList.get(0)
                            MainHomeCard(
                                dailyData,
                                shape1 = RoundedCornerShape(
                                    topEnd = 0.dp,
                                    bottomEnd = 0.dp,
                                    topStart = 15.dp,
                                    bottomStart = 15.dp
                                ),
                                shape2 = RoundedCornerShape(
                                    bottomEnd = 0.dp,
                                    topEnd = 0.dp,
                                    topStart = 0.dp,
                                    bottomStart = 15.dp
                                ),
                                shape3 = RoundedCornerShape(
                                    topEnd = 0.dp,
                                    bottomEnd = 0.dp,
                                    topStart = 15.dp,
                                    bottomStart = 0.dp
                                ),
                                modifier = Modifier.padding(
                                    end = 0.dp,
                                    top = 0.dp,
                                    bottom = 0.dp,
                                    start = 80.dp
                                ),
                                modifier2 = Modifier.padding(
                                    end = 0.dp,
                                    top = 0.dp,
                                    bottom = 0.dp,
                                    start = 40.dp
                                )
                            ) {
                                if(it.selectedOtp != ""){
                                    fireStoreDaillyDataViewModel.setDailyData(docID,it,currentDate)
                                    when(setDataResult){
                                        is Resource.Success -> {
                                            fireStoreDaillyDataViewModel.fetchDailyData(docID)
                                            Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show()
                                        }
                                        is Resource.Failure -> {
                                            val exception = (setDataResult as Resource.Failure).exception
                                            Toast.makeText(context, "Data Not Saved $exception", Toast.LENGTH_SHORT).show()
                                        }
                                        else -> {
                                            fireStoreDaillyDataViewModel.fetchDailyData(docID)
                                            Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }else{
                                    Toast.makeText(context, "Please Select Options", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                    is Resource.Failure -> {
                        val exception = (fetchDataResult as Resource.Failure).exception
                        Toast.makeText(context, "Data Not Fetched $exception", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(context, "Data Are Null44", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

