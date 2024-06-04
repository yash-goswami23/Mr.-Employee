package com.example.emp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.emp.R
import com.example.emp.navigation.BottomBarItem.BottomBarItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(title:String) {
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .shadow(
            15.dp,
            clip = true,
            shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)
        ),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.TopBarColor)
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 15.dp
        ),
        shape = RoundedCornerShape(bottomEnd = 25.dp, bottomStart = 25.dp)
    ) {
        TopAppBar(title = { Text(text = title,
            color = Color.White,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Light)
        },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorResource(id = R.color.TopBarColor)

            ))
    }
}



//Bottom Bar
@Composable
fun MyBottomBar(navController: NavController) {
    val items = listOf(
        BottomBarItem.Home,
        BottomBarItem.totalPro,
        BottomBarItem.Profile
    )
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .shadow(
            15.dp,
            clip = true,
            shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)
        ),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.TopBarColor)
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 15.dp
        ),
        shape = RoundedCornerShape(topEnd = 25.dp, topStart = 25.dp)
    ) {
        BottomAppBar (containerColor = colorResource(id = R.color.TopBarColor), tonalElevation = 20.dp,
            contentColor = colorResource(id = R.color.TopBarColor)){
            Row (modifier = Modifier.fillMaxSize(),horizontalArrangement = Arrangement.SpaceAround){

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                NavigationBar(containerColor = colorResource(id = R.color.TopBarColor)){

                    Row (modifier = Modifier.fillMaxSize(),horizontalArrangement = Arrangement.SpaceAround) {
                        items.forEach {
                            NavigationBarItem(
                                selected =currentRoute == it.route,
                                onClick = {
                                    navController.navigate(it.route)
                                {
                                        navController.navigate(it.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                } },
                                icon = {
                                    IconWithText(imageVector = ImageVector.vectorResource(it.icon), title = it.title) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun IconWithText(imageVector: ImageVector, title:String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BaseCard(isMain = false){
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                    tint = colorResource(id = R.color.BackgroundColor),
                    modifier = Modifier
                        .size(50.dp)
                        .padding(9.dp)
                )
        }
        Text(text = title, fontFamily = FontFamily.Default, color = Color.White)
    }
}
