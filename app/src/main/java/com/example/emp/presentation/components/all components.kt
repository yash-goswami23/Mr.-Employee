package com.example.emp.presentation.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emp.Domain.DataClasses.DailyData
import com.example.emp.R
import java.time.LocalDate

//card
@Composable
fun MyMainCard(painter: Painter,titleText:String,onCreateFun: @Composable () -> Unit) {

    BaseCard (true){
        Column(modifier = Modifier.padding(bottom = 10.dp)) {
            Spacer(modifier = Modifier.height(15.dp))
            Image(painter = painter, contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(text = titleText,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Light,
                fontSize = 50.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .align(Alignment.CenterHorizontally),
                color = colorResource(id = R.color.TopBarColor) )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .padding(horizontal = if (titleText.length < 7) 140.dp else 110.dp), color = colorResource(
                id = R.color.TopBarColor))
            Spacer(modifier = Modifier.height(15.dp))
            onCreateFun.invoke()
        }
    }
}



//Button
@Composable
fun Btn(btnTitle: String,onClick: ()->Unit) {
    ElevatedCard(modifier = Modifier
        .clickable {
            onClick.invoke()
        }
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 95.dp, vertical = 4.dp)
        , colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.TopBarColor)
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 20.dp
        )
    ) {
        Text(text = btnTitle, fontFamily = FontFamily.Default, fontSize = 20.sp, fontWeight = FontWeight.Light, textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.CenterHorizontally), color = Color.White)
    }
}


//outlineTextFieldPass
@Composable
fun myOutlineTextFiledPass(img:Painter,labelText:String): String {
    var state by remember {
        mutableStateOf("")
    }
    val passwordVisible = remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        value = state,
        onValueChange = {
            state = it
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = colorResource(id = R.color.BackgroundColor),
            focusedTextColor = colorResource(id = R.color.TopBarColor),
            unfocusedBorderColor = colorResource(id = R.color.TopBarColor),
            focusedBorderColor = colorResource(id = R.color.TopBarColor),
            focusedLabelColor = colorResource(id = R.color.BackgroundColor),
            unfocusedLabelColor = colorResource(id = R.color.BackgroundColor),
            focusedLeadingIconColor = colorResource(id = R.color.TopBarColor),
            unfocusedLeadingIconColor = colorResource(id = R.color.TopBarColor)
        ),
        leadingIcon = {
            Image(painter = img, contentDescription = "")
        },
            trailingIcon = {
                val iconImage = if (passwordVisible.value){
                    ImageVector.vectorResource(R.drawable.visibility)
                }else{
                    ImageVector.vectorResource(R.drawable.visibilityoff)
                }
                val description = if(passwordVisible.value){
                    "Hide password"
                }else{
                    "Show password"
                }
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(imageVector = iconImage, contentDescription = description,
                        tint = colorResource(id = R.color.TopBarColor))
                }
        },visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        label = {
            Text(text = labelText)
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 30.dp, vertical = 4.dp),
        singleLine = true,
        shape = RoundedCornerShape(15.dp)
    )
    return state
}


// base card
@Composable
fun BaseCard(isMain:Boolean, color: Color = Color.White, shape:Shape = RoundedCornerShape(25.dp), modifier: Modifier = Modifier, viewItem: @Composable ()->Unit) {
    ElevatedCard(
        modifier = modifier
            .wrapContentHeight()
            .wrapContentHeight()
            .padding(
                horizontal = if (isMain) {
                    30.dp
                } else 0.dp
            )
            .shadow(
                15.dp,
                shape = RoundedCornerShape(15.dp),
                clip = false,
                ambientColor = Color.Black,
                spotColor = Color.Black
            )
        , colors = CardDefaults.elevatedCardColors(
            containerColor = color
        ),
        shape = shape,
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 15.dp
        )
    ) {
        viewItem.invoke()
    }
}


//outlineTextField
@Composable
fun myOutlineTextFiled(img:Painter,labelText:String,keyboardOptions: KeyboardOptions=KeyboardOptions(keyboardType = KeyboardType.Text)): String {
    var state by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        value = state,
        onValueChange = {
            state = it
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = colorResource(id = R.color.BackgroundColor),
            focusedTextColor = colorResource(id = R.color.TopBarColor),
            unfocusedBorderColor = colorResource(id = R.color.TopBarColor),
            focusedBorderColor = colorResource(id = R.color.TopBarColor),
            focusedLabelColor = colorResource(id = R.color.BackgroundColor),
            unfocusedLabelColor = colorResource(id = R.color.BackgroundColor),
            focusedLeadingIconColor = colorResource(id = R.color.TopBarColor),
            unfocusedLeadingIconColor = colorResource(id = R.color.TopBarColor)
        ),
        leadingIcon = {
            Image(painter = img, contentDescription = "",Modifier.size(25.dp))
        },
        label = {
            Text(text = labelText)
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 30.dp, vertical = 1.dp),
        singleLine = true,
        shape = RoundedCornerShape(15.dp),
        keyboardOptions = keyboardOptions
    )
    return state
}




//mini card
@Composable
fun MiniCard(firstIcon: Painter, textView: String,AuthChagne : () -> Unit ){
    val context = LocalContext.current
    BaseCard(true) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(5.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically) {
                    BaseCard(false,modifier = Modifier.padding(5.dp)
                    ){
                        Image(  modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 5.dp).clickable {
                                Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show()
                            }
                            .size(55.dp),
                            painter = firstIcon, contentDescription = null,
                            alignment = Alignment.Center,)
                    }
                BaseCard(false, modifier = Modifier.clickable {
                    AuthChagne.invoke()
                }) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 5.dp, vertical = 15.dp)
                            .align(Alignment.CenterVertically)
                            .padding(5.dp),
                        text = textView,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        fontFamily = FontFamily.Default,
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.TopBarColor))
                }
            }
            Text(text = "Policy",
                fontSize = 22.sp,
                fontWeight = FontWeight.Thin,
                fontFamily = FontFamily.Default,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.TopBarColor),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(5.dp))
        }
    }
}




@Composable
fun TextWithDivider(text1:String,text2:String) {
    Text(text = text1,
        textAlign = TextAlign.Center,
        fontSize = 18.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        color = colorResource(id = R.color.TopBarColor))
    Text(text = text2,
        textAlign = TextAlign.Center,
        fontSize = 18.sp,
        fontFamily = FontFamily.Default,
        color = colorResource(id = R.color.TopBarColor))
    Divider(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp),
        color = colorResource(id = R.color.BackgroundColor),
        thickness = 2.dp)
}





@Composable
fun MainHomeCard(
    dailyData: DailyData ,
    shape1: RoundedCornerShape,
    shape2: RoundedCornerShape,
    shape3: RoundedCornerShape,
    modifier: Modifier,
    modifier2: Modifier,
    onSaveClick: (DailyData) -> Unit
) {
    var overTimeHour by remember {
        mutableStateOf(0)
    }
    var seletedOptions by remember {
        mutableStateOf("")
    }
    var data: List<MutableState<out Any>>
    MiniElevationCard(modifier = modifier
        .fillMaxWidth(), shape = shape1) {
        Column {
            MiniElevationCard(
                modifier = modifier2
                    .fillMaxWidth(),
                shape = shape2,
                color = colorResource(id = R.color.BackgroundColor)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
                    Text(
//                        text = "Date And Day",
                        text = "${dailyData.date} , ${dailyData.day}",
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Light,
                        color = Color.White,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            MiniElevationCard(
                modifier = modifier2
                    .fillMaxWidth(),
                shape = shape3,
                color = colorResource(id = R.color.BackgroundColor)
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
                    Spacer(modifier = Modifier.height(40.dp))
                    Row(horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        var isShow by remember {
                            mutableStateOf(false)
                        }
                        if(isShow){
                            Text(
                                text = "Overtime Hour ",
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Light,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.CenterVertically))
                            Spacer(modifier = Modifier.width(2.dp))
                            overTimeHour = MyDropDownMenu(8,dailyData.overTime)
//                            overTimeHour = MyDropDownMenu()
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        val context = LocalContext.current
                        data = radioCardButton(dailyData.selectedOtp)
                        seletedOptions = data.get(0).value.toString()
                        isShow = data.get(1).value as Boolean
                        Spacer(modifier = Modifier.width(5.dp))
                        val currentDate = LocalDate.now().toString()
                        if(dailyData.selectedOtp == "" && dailyData.date ==  currentDate){
                            CardWithIcon(painter = Icons.Default.Check, modifier = Modifier.clickable {
                                if(seletedOptions != "P"){
                                    overTimeHour = 0
                                }
                                onSaveClick.invoke(DailyData(dailyData.date,dailyData.day,overTimeHour,seletedOptions))
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardWithText(txt: String,modifier: Modifier = Modifier) {
    BaseCard(isMain = false, shape = CircleShape,modifier = Modifier
        .padding(0.dp)
        .clip(shape = CircleShape)) {
        Box(contentAlignment = Alignment.Center, modifier = modifier.padding(0.dp)) {
            Text(
                text = txt,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Light,
                fontSize = 22.sp,
                color = colorResource(id = R.color.BackgroundColor),
                modifier = Modifier.padding(vertical = 1.dp, horizontal = 7.dp)
            )
        }
    }
}


@Composable
fun CardWithIcon(painter: ImageVector, modifier: Modifier = Modifier) {
    BaseCard(isMain = false, shape = CircleShape,modifier = Modifier
        .padding(0.dp)
        .clip(shape = CircleShape)) {
        Box(contentAlignment = Alignment.Center, modifier = modifier.padding(0.dp)) {
            Icon(imageVector = painter, contentDescription = null, tint = colorResource(id = R.color.BackgroundColor),
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 5.dp)
                    .size(18.dp))
        }
    }
}



@Composable
fun TaskCard(rValue:Float,fValue:Float,itemName:String,ItemCountity:String) {
    Row(modifier = Modifier
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        MiniElevationCard(modifier = Modifier
            .weight(rValue)
            .height(80.dp),shape = RoundedCornerShape(bottomStart = 0.dp, topStart = 0.dp, bottomEnd = 15.dp, topEnd = 15.dp)
        ){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(text =  itemName,
                    fontFamily = FontFamily.Default,
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.BackgroundColor),
                    textAlign = TextAlign.Center)
            }
        }
        MiniElevationCard(modifier = Modifier
            .weight(fValue)
            .height(80.dp),
            shape = RoundedCornerShape(bottomStart = 15.dp, topStart = 15.dp, bottomEnd = 0.dp, topEnd = 0.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(text =   ItemCountity,
                    fontFamily = FontFamily.Default,
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.BackgroundColor),
                    textAlign = TextAlign.Center)
            }
        }
    }
}


@Composable
fun MiniElevationCard(modifier: Modifier = Modifier, shape: RoundedCornerShape ,color: Color = Color.White, onItemShow: @Composable ()->Unit) {
    ElevatedCard(
        modifier = modifier
            .shadow(
                30.dp,
                clip = false,
                ambientColor = Color.Black,
                spotColor = Color.Black
            )
        , colors = CardDefaults.elevatedCardColors(
            containerColor = color
        ),
        shape = shape,
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 15.dp
        )
    ) {
        onItemShow.invoke()
    }
}




//dayNight
@Composable
fun DayNightCard(dayIcon: Painter, nightIcon:Painter,clicked : ()->Unit ){
    BaseCard(true, color = colorResource(id = R.color.BackgroundColor), modifier = Modifier.padding(horizontal = 70.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically) {
                Column (horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center){
                    BaseCard(false
                    , modifier = Modifier.clickable {
                        clicked.invoke()
                        }){
                        Image(painter = dayIcon, contentDescription = null,
                            Modifier
                                .size(40.dp)
                                .padding(6.dp))
                    }
                    Text(text = "Day Sift",
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Light,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    BaseCard(false, modifier = Modifier.clickable {
                        clicked.invoke()
                    }){
                        Image(painter = nightIcon, contentDescription = null,
                            Modifier
                                .size(40.dp)
                                .padding(8.dp))
                    }
                    Text(text = "Night Sift",
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Light,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun radioCardButton(selectedOpt:String): List<MutableState<out Any>> {
    val options = listOf("P", "A", "H")
    val selectedOption = remember {
        mutableStateOf(selectedOpt)
    }
    val isShow = remember {
        mutableStateOf(true)
    }
    Row{
        val context = LocalContext.current
        options.forEach{option ->
            val MyModifier = Modifier
                .background(
                    color =
                    if (option == selectedOption.value) {
                        when (option) {
                            "P" -> {
                                isShow.value = true
                                Color.Green
                            }

                            "A" -> {
                                isShow.value = false
                                Color.Red
                            }

                            else -> {
                                isShow.value = false
                                Color.DarkGray
                            }
                        }
                    } else {
                        Color.White
                    }
                )
            Spacer(modifier = Modifier.width(10.dp))
            Box (contentAlignment = Alignment.Center){
                CardWithText(txt = option,modifier = MyModifier)
                var isEnale by remember {
                    mutableStateOf(false)
                }
                LaunchedEffect(1) {
                    isEnale = if(selectedOpt == ""){true}else{false}
                }
                RadioButton(selected = selectedOption.value == option, onClick = {
                    selectedOption.value = option
                },colors = RadioButtonDefaults.colors(
                    unselectedColor = Color.Transparent,
                    disabledSelectedColor = Color.Transparent,
                    disabledUnselectedColor = Color.Transparent,
                    selectedColor = Color.Transparent
                ), modifier = Modifier.size(20.dp),
                    enabled = isEnale)

            }
        }
    }

    val data = listOf(selectedOption,isShow)
    return data
}

@Composable
fun MyDropDownMenu(optValue:Int,value:Int,modifier: Modifier = Modifier): Int {
    val options = (0..optValue).toList()
    val newValue = remember { mutableStateOf(0) }
    if(value == 9){
        newValue.value = 0
    }else{
        newValue.value = value
    }
    val selectedOption = remember { mutableStateOf(options[newValue.value])}

    val expanded = remember { mutableStateOf(false) }
    Column {

        CardWithText(txt = selectedOption.value.toString(), modifier = Modifier
                . clickable{
                    if(value == 9){
                        expanded.value = !expanded.value
                    }else{
                        expanded.value = false
                    }
               } )
        DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
            options.forEach{
                DropdownMenuItem(text = { Text(text = it.toString()) }, onClick = {
                    selectedOption.value = it
                    expanded.value = false
                })
            }
        }
    }
    return selectedOption.value
}