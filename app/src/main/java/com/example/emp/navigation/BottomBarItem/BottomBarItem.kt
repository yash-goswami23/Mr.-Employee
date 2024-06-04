package com.example.emp.navigation.BottomBarItem

import com.example.emp.R


sealed class BottomBarItem (
    var route:String,var icon:Int, var title:String
){
    object Home : BottomBarItem("home", R.drawable.home,"Home")
    object totalPro : BottomBarItem("task", R.drawable.totalicon,"Total Progress")
    object Profile : BottomBarItem("profile", R.drawable.profile,"Profile")
}
