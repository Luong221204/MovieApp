package com.example.movieapp

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(link: Int?=null){
    val contextForToast= LocalContext.current.applicationContext
    var selectedItem by remember{
        mutableStateOf("Home")
    }
    val bottomMenuItemsList= prepareBottomMenu(link,selectedItem)

    BottomAppBar(
        cutoutShape = CircleShape,
        contentColor = colorResource(id = R.color.white),
        backgroundColor = colorResource(id = R.color.red),
        elevation = 3.dp,
        modifier = Modifier
            .navigationBarsPadding()
            .clip(shape = RoundedCornerShape(MovieAppTheme.dimensionValue.roundCornerForTextField))
            .height(70.dp)

    ){
        bottomMenuItemsList.forEachIndexed {
            index,bottomMenuItem ->
            if(index == 2){
                BottomNavigationItem(
                    selected = false,
                    onClick = {},
                    icon = {},
                    enabled = false
                )
            }
            BottomNavigationItem(
                selected = (selectedItem==bottomMenuItem.label),
                onClick = {
                    selectedItem=bottomMenuItem.label
                },
                icon={
                    if(bottomMenuItem.link != null){
                        Image(
                            painter = painterResource(bottomMenuItem.link),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(30.dp)
                                .width(30.dp)
                                .clip(CircleShape)
                                .customBottomItem(bottomMenuItem.checked,)
                        )
                    }else{
                        Icon(
                            painter = bottomMenuItem.icon,
                            contentDescription = bottomMenuItem.label,
                            modifier = Modifier
                                .height(20.dp)
                                .width(20.dp)
                        )
                    }

                },
                alwaysShowLabel = true,
                enabled = true,
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.White
            )
        }
    }
}
data class BottomMenuItem(
    val label:String,
    val icon:Painter,
    val link:Int?=null,
    var selectedItem:String
){
    var checked=selectedItem==label
}



@Composable
fun prepareBottomMenu( link:Int? = null,selectedItem:String):List<BottomMenuItem>{
    return listOf(
        BottomMenuItem(
            label = "Home",
            icon = painterResource(id = R.drawable.btn_1),
            selectedItem = selectedItem
        ),
        BottomMenuItem(
            label = "Profile",
            icon = painterResource(id = R.drawable.btn_2),
            selectedItem = selectedItem

        ),
        BottomMenuItem(
            label = "Support",
            icon = painterResource(id = R.drawable.btn_3),
            selectedItem = selectedItem

        ), BottomMenuItem(
            label = "Settings",
            icon = painterResource(id = R.drawable.btn_4),
            link,
            selectedItem = selectedItem

        )

    )
}


@SuppressLint("ModifierFactoryUnreferencedReceiver")
@Composable
fun Modifier.customBottomItem(checked:Boolean):Modifier{
    return this.then(
        Modifier.border(
            BorderStroke(
                width = 2.dp,
                color =  when(checked){
                    true->Color.Blue
                    false->Color.White
                }
            ),
            shape = CircleShape
        )
            .padding(2.dp)
    )
}