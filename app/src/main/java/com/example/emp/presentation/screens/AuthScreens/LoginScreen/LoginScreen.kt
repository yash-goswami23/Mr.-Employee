package com.example.emp.presentation.screens.AuthScreens.LoginScreen


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.emp.R
import com.example.emp.data.Resource
//import com.example.emp.data.Resource
import com.example.emp.presentation.components.Btn
import com.example.emp.presentation.components.MiniCard
import com.example.emp.presentation.components.MyMainCard
import com.example.emp.presentation.components.myOutlineTextFiled
import com.example.emp.presentation.components.myOutlineTextFiledPass
import com.example.emp.presentation.components.MyTopAppBar
import com.example.emp.Domain.ViewModels.AuthViewModel


@Composable
fun LoginScreen(viewModel: AuthViewModel, navController: NavHostController) {
    val context = LocalContext.current
    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }

    val loginFlow = viewModel.loginFlow.collectAsState()

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
            MyMainCard(painterResource(R.drawable.circleicon),"Login"){
                email = myOutlineTextFiled(img = painterResource(id = R.drawable.usericon),labelText = "Enter Your Email")
                password = myOutlineTextFiledPass(img = painterResource(id = R.drawable.passicon),labelText = "Enter Your Password")
                Text(text = "Forget Password",
                    fontFamily = FontFamily.Default,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.End), color = colorResource(id = R.color.TopBarColor))
                Btn(btnTitle = "Login") {
                    if(email.isNotEmpty() && password.isNotEmpty())
                    viewModel.login(email, password)
                    else
                        Toast.makeText(context, "Please Fill All Fields", Toast.LENGTH_SHORT).show()
                    /*navController.navigate("main")
                    Toast.makeText(context, "ClickLogin", Toast.LENGTH_SHORT).show()*/
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
            MiniCard(firstIcon = painterResource(id =R.drawable.gmailicon), textView = "SIGUN-UP"){
                navController.navigate("signup")
                Toast.makeText(context, "ClickSignUp", Toast.LENGTH_SHORT).show()
            }
            loginFlow.value.let {
                when(it){
                    is Resource.Failure -> {
                        Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
                    }
                    Resource.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                    is Resource.Success -> {
                        LaunchedEffect(Unit) {
                            navController.navigate("main"){
                                popUpTo("main"){
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
