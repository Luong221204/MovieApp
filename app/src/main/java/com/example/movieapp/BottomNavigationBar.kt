package com.example.movieapp

import android.widget.Toast
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(){
    val bottomMenuItemsList= prepareBottomMenu()
    val contextForToast= LocalContext.current.applicationContext
    var selectedItem by remember{
        mutableStateOf("Home")
    }
    BottomAppBar(
        cutoutShape = CircleShape,
        contentColor = colorResource(id = R.color.white),
        backgroundColor = colorResource(id = R.color.black3),
        elevation = 3.dp
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
                    Toast.makeText(contextForToast,bottomMenuItem.label,Toast.LENGTH_SHORT).show()
                },
                icon={
                    Icon(
                        painter = bottomMenuItem.icon,
                        contentDescription = bottomMenuItem.label,
                        modifier = Modifier
                            .size(MovieAppTheme.dimensionValue.bottomMenuIconSize)
                    )
                },
                label= {
                    Text(
                        text = bottomMenuItem.label,
                        modifier = Modifier.padding(top = 14.dp)
                    )
                },
                alwaysShowLabel = true,
                enabled = true
            )
        }
    }
}
data class BottomMenuItem(
    val label:String,
    val icon:Painter
)



@Composable
fun prepareBottomMenu():List<BottomMenuItem>{
    return listOf(
        BottomMenuItem(
            label = "Home",
            icon = painterResource(id = R.drawable.btn_1)
        ),
        BottomMenuItem(
            label = "Profile",
            icon = painterResource(id = R.drawable.btn_2)
        ),
        BottomMenuItem(
            label = "Support",
            icon = painterResource(id = R.drawable.btn_3)
        ), BottomMenuItem(
            label = "Settings",
            icon = painterResource(id = R.drawable.btn_4)
        )

    )
}