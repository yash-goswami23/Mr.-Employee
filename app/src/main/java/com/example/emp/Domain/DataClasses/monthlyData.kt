package com.example.emp.Domain.DataClasses

data class monthlyData(
    val totalPresent:Int = 0,//totalDays - totalAbsent
    val totalAbsent:Int= 0,//totalDays - totalPresent
    val totalHolyDays:Int= 0,//totalDays - totalPresent - totalAbsent
    val totalDays:Int= 0,//totalPresent + totalAbsent + totalHolyDays
    val totalOverTimeHours: Int= 0,//
    val totalOverTimeSalary:Int= 0,//monthlySalary / month = dailySalary / jobTimeHour = HourSalary * overTimeHour = ots
    val totalSalary:Int = 0,//totalPresent * dailySalary
    val totalIncome:Int= 0
)