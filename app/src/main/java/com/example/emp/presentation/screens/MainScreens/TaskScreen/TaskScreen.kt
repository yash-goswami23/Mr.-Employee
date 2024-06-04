package com.example.emp.presentation.screens.MainScreens.TaskScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.emp.Domain.DataClasses.EmpDetail
import com.example.emp.Domain.DataClasses.monthlyData
import com.example.emp.R
import com.example.emp.data.Resource
import com.example.emp.presentation.components.*
import com.example.emp.Domain.ViewModels.AuthViewModel
import com.example.emp.Domain.ViewModels.fireStoreViewModel
import com.example.emp.Domain.ViewModels.FireStoreDailyDataViewModel
import com.example.emp.Domain.ViewModels.FireStoreMonthlyDataViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.util.Calendar
import javax.annotation.meta.When

val TAG = "TotalScreenLogs"

@Composable
fun TaskScreen(fireStoreViewModel: fireStoreViewModel, authViewModel: AuthViewModel, fireStoreDailyDataViewModel: FireStoreDailyDataViewModel, fireStoreMonthlyDataViewModel: FireStoreMonthlyDataViewModel, navController: NavHostController) {
    val Month = LocalDate.now().monthValue.toString()
    val year = LocalDate.now().year.toString()
    val MonthAndYear = "$Month-$year"
    val fetchMonthlyDataResult by fireStoreMonthlyDataViewModel.data.observeAsState()
    val setMonthlyDataResult by fireStoreMonthlyDataViewModel.setDataResult.observeAsState()
    val docID = authViewModel.currentUser?.email.toString()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val JobTime = remember {
        mutableStateOf(0)
    }
    val MonthlySalary = remember {
        mutableStateOf(0)
    }
    val fetchDailyDataResult by fireStoreDailyDataViewModel.data.observeAsState()
    var TP by remember {
        mutableStateOf(0)
    }
    var TA by remember {
        mutableStateOf(0)
    }
    var TH by remember {
        mutableStateOf(0)
    }
    var TD by remember {
        mutableStateOf(0)
    }
    var overTime by remember {
        mutableStateOf(0)
    }
    val fetchEmpDataResult by fireStoreViewModel.data.observeAsState()//monthlySalary,jobTime
    var TS by remember {
        mutableStateOf(0)
    }
    var TI by remember {
        mutableStateOf(0)
    }
    var TOTS by remember {
        mutableStateOf(0)
    }
    var HS by remember {
        mutableStateOf(0)
    }
    var DS by remember {
        mutableStateOf(0)
    }
    var MonthlyData by remember {
        mutableStateOf(monthlyData(0, 0, 0, 0, 0, 0, 0, 0))
    }
    val DayInMonth = getDaysInMonth()
    LaunchedEffect(Unit) {
        fireStoreViewModel.fetchEmpData(docID)
        fireStoreDailyDataViewModel.fetchDailyData(docID)
    }
    when(fetchEmpDataResult){
        is Resource.Success -> {
            val data = (fetchEmpDataResult as Resource.Success<EmpDetail>).data
            LaunchedEffect(Unit){
                scope.launch {
                    Log.d(TAG,"fetchEmpData Success Data : $data")
                    JobTime.value = data.jobTime.toInt()
                    MonthlySalary.value = data.monthlySalary.toInt()
                }
            }
        }
        is Resource.Failure -> {
            val exception = (fetchEmpDataResult as Resource.Failure).exception
            Log.d(TAG,"fetchEmpData  Failure Data : ${exception.message}")
            MonthlyData = monthlyData(0, 0, 0, 0, 0, 0, 0, 0)
        }
        else -> {
            Log.d(TAG,"fetchEmpData else")
            MonthlyData = monthlyData(0, 0, 0, 0, 0, 0, 0, 0)
        }
    }
    var count by remember {
        mutableStateOf(1)
    }
    when(fetchDailyDataResult){
        is Resource.Success -> {

            val data = (fetchDailyDataResult as Resource.Success<List<DailyData>>).data
            Log.d(TAG,"fetchDailyData Success Data : $data")
            LaunchedEffect(Unit) {
                scope.launch {
                    if(count == 1){
                        data.forEach {
                            when (it.selectedOtp) {
                                "P" -> {
                                    TP = TP + 1
                                    overTime = overTime + it.overTime
                                }

                                "A" -> {
                                    TA = TA + 1
                                }

                                "H" -> {
                                    TH = TH + 1
                                }
                            }
                        }
                        TD = TP + TA + TH
                        count = 5
                    }
                    //TS,TI,TOTS
                    TI = TS + TOTS
                    DS = (MonthlySalary.value / DayInMonth) // Daily Salary , Job Time Hour (JTH)
                    if(DS != 0){
                        HS = (DS / JobTime.value)//HS Hour Salary
                        TOTS = HS * overTime // OTHS Overtime Hour Salary
                        TS = TP * DS
                    }
                    Log.d(
                        TAG,
                        "TD : $TD, TP : $TP, TA : $TA, TH : $TH, overTime : $overTime, TS = $TS, TI = $TI, TOTS = $TOTS"
                    )
                    if (TD != 0) {
                        fireStoreMonthlyDataViewModel.setMonthlyData(
                            docID,
                            monthlyData(TP, TA, TH, TD, overTime, TOTS,TS ,TI),
                            MonthAndYear
                        )
                    }
                }
            }
        }
        is Resource.Failure -> {
            val exception = (fetchDailyDataResult as Resource.Failure).exception
            Log.d(TAG,"fetchDailyData  Failure Data : ${exception.message}")
        }
        else -> {
            Log.d(TAG,"fetchDailyData else")
        }
    }
    when (setMonthlyDataResult) {
        is Resource.Success -> {
            val data = (setMonthlyDataResult as Resource.Success<String>).data
            if (data == "Data Saved") {
                Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "setMonthlyData Success Data : $data")
                LaunchedEffect(Unit) {
                    scope.launch {
                        fireStoreMonthlyDataViewModel.fetchMonthlyData(docID, MonthAndYear)
                    }
                }
            }
        }

        is Resource.Failure -> {
            val exception = (setMonthlyDataResult as Resource.Failure).exception
            Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
        }
        else -> {}
    }

    when(fetchMonthlyDataResult){
        is Resource.Success -> {
            val data = (fetchMonthlyDataResult as Resource.Success<monthlyData>).data
            LaunchedEffect(Unit){
            if(data.totalDays != 0){
                Log.d(TAG, "fetchMonthlyData Success Data : $data")
                MonthlyData = data
            }
            }
        }
        is Resource.Failure -> {
            val exception = (fetchMonthlyDataResult as Resource.Failure).exception
            Log.d(TAG, "fetchMonthlyData  Failure Data : ${exception.message}")
        }
        else -> {
            Log.d(TAG, "fetchMonthlyData else")
        }
    }

    Scaffold(
        topBar = {
            MyTopAppBar(title = "Task")
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
            item {
                Log.d(TAG,"Monthly Data : $MonthlyData")
                val properties: List<Pair<String, Int>> = listOf(
                    "Total Present" to MonthlyData.totalPresent,
                    "Total Absent" to MonthlyData.totalAbsent,
                    "Total Holy Days" to MonthlyData.totalHolyDays,
                    "Total Days" to MonthlyData.totalDays,
                    "Total Over Time Hours" to MonthlyData.totalOverTimeHours,
                    "Total Over Time Salary" to MonthlyData.totalOverTimeSalary,
                    "Total Salary" to MonthlyData.totalSalary,
                    "Total Income" to MonthlyData.totalIncome
                )
                var change by remember { mutableStateOf(false) }
                properties.forEach { (label, value) ->
                    if(change){
                        Spacer(modifier = Modifier.height(20.dp))
                        TaskCard(3f, 1f, label, value.toString())
                        change = !change
                    }else{
                        Spacer(modifier = Modifier.height(20.dp))
                        TaskCard(1f, 3f,value.toString(), label)
                        change = !change
                    }
                }
           }
        }
    }
}


fun getDaysInMonth(): Int {
    val year = LocalDate.now().year
    val month = LocalDate.now().monthValue
    val yearMonth = YearMonth.of(year, month)
    return yearMonth.lengthOfMonth()
}