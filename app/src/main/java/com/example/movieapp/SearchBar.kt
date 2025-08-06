package com.example.movieapp

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieapp.ui.theme.BlackGray
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.ui.theme.unknown
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
@Preview
fun SearchBar(hint:String = "",onFilterClick:()->Unit,onSearchListener:(String)->Unit){
    var text by rememberSaveable{
        mutableStateOf("")
    }
    LaunchedEffect(Unit) {
         snapshotFlow { text }
             .debounce(500)
             .distinctUntilChanged()
             .collect { newQuery ->
                 onSearchListener(newQuery)
             }
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(48.dp)
                .background(
                    color = BlackGray,shape= RoundedCornerShape(MovieAppTheme.dimensionValue.roundCornerForTextField)
                )
            ,
            verticalAlignment = Alignment.CenterVertically
        ){

            TextField(value = text, onValueChange = {text = it},
                placeholder = {
                    Text(text=hint ,color = unknown)
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = null,
                        tint = MovieAppTheme.colorScheme.iconTint,
                        modifier = Modifier.padding(start = 8.dp).size(MovieAppTheme.dimensionValue.iconSize2)
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.microphone),
                        contentDescription = null,
                        tint = MovieAppTheme.colorScheme.iconTint,
                        modifier = Modifier.size(MovieAppTheme.dimensionValue.iconSize2)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = MovieAppTheme.colorScheme.cursorColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent,
                    containerColor = Color.Transparent,
                    focusedPlaceholderColor = Color.Transparent,
                    focusedTextColor = Color.Transparent,
                    unfocusedTextColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        keyboardController?.hide()
                        onSearchListener(text)
                    }
                ),
                modifier = Modifier.weight(1f)
                    .fillMaxHeight(),
                textStyle = MovieAppTheme.appTypoTheme.textStyleForSearch,
                singleLine = true
            )
            Spacer(modifier = Modifier.width(MovieAppTheme.dimensionValue.spacer1))


        }
        Spacer(modifier = Modifier.width(20.dp))
        Box(
            modifier = Modifier
                .size(48.dp)
                .clickable { onFilterClick() }
                .background(
                    color = BlackGray,
                    shape = RoundedCornerShape(MovieAppTheme.dimensionValue.roundCornerForTextField),
                )
        ){
            Icon(
                painter = painterResource(R.drawable.filter),
                contentDescription = null,
                tint = MovieAppTheme.colorScheme.iconTint,
                modifier = Modifier.size(MovieAppTheme.dimensionValue.iconSize2).align(Alignment.Center)
            )
        }
    }
}
