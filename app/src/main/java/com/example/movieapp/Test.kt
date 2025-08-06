package com.example.movieapp

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Test : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            screen()
        }
    }
}

@Composable
fun screen(){
    val viewModel:TesrViewModel= viewModel()

    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val state= viewModel.stateFlow.collectAsState()
    // Collect event from SharedFlow
    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect{
            delay(1000)

            scaffoldState.snackbarHostState.showSnackbar(it)
        }

    }
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        padding->
        Box(
            modifier = Modifier.fillMaxSize().background(color = Color.White),

        ){
            Button(
                modifier = Modifier.align(Alignment.Center),
                onClick = {
                    viewModel.triggerSnackbar()
                }
            ) {
                Text(text = "click")
            }
        }
    }


}

suspend fun dowork(){
    delay(2000)
    Log.d("DUCLUONNG", Thread.currentThread().name)
}
