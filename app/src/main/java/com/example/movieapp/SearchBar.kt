package com.example.movieapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieapp.ui.*
import com.example.movieapp.ui.theme.BlackGray
import com.example.movieapp.ui.theme.unknown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun SearchBar(hint:String = ""){
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(50.dp)
            .background(
                color = BlackGray,shape= RoundedCornerShape(MovieAppTheme.dimensionValue.roundCornerForTextField)
            )
            .border(BorderStroke(
                width = MovieAppTheme.dimensionValue.borderWidthForTextField ,
                brush = Brush.linearGradient(colors =MovieAppTheme.colorScheme.linearGradientColorsForTextField)),
                shape = RoundedCornerShape(MovieAppTheme.dimensionValue.roundCornerForTextField)
            )
            .padding(horizontal = 16.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(id = R.drawable.search),
            contentDescription = null,
            tint = MovieAppTheme.colorScheme.iconTint,
            modifier = Modifier.size(MovieAppTheme.dimensionValue.iconSize2)
        )
        Spacer(modifier = Modifier.width(MovieAppTheme.dimensionValue.spacer1))
        TextField(value = "", onValueChange = {},
            placeholder = {
                Text(text=hint ,color = unknown)
            },

            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MovieAppTheme.colorScheme.cursorColor,
                focusedIndicatorColor = MovieAppTheme.colorScheme.focusIndicatorColor,
                unfocusedLabelColor = MovieAppTheme.colorScheme.unfocusIndicatorColor,
                containerColor = MovieAppTheme.colorScheme.containerColorForButton,
                focusedPlaceholderColor = MovieAppTheme.colorScheme.focusedPlaceHolderColor,
                focusedTextColor = MovieAppTheme.colorScheme.focusedLabelColor,
                unfocusedTextColor = MovieAppTheme.colorScheme.focusedLabelColor
            ),
            modifier = Modifier.weight(1f)
                .fillMaxHeight(),
            textStyle = MovieAppTheme.appTypoTheme.textStyleForSearch,
            shape = RoundedCornerShape(MovieAppTheme.dimensionValue.roundCornerForTextField),
            singleLine = true
            )
        Spacer(modifier = Modifier.width(MovieAppTheme.dimensionValue.spacer1))
        Icon(
            painter = painterResource(id = R.drawable.microphone),
            contentDescription = null,
            tint = MovieAppTheme.colorScheme.iconTint,
            modifier = Modifier.size(MovieAppTheme.dimensionValue.iconSize2)
        )
    }
}