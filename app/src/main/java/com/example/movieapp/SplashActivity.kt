package com.example.movieapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            //IntroScreenPreview()
            IntroScreen {
                startActivity(Intent(this,LoginActivity::class.java))
            }
        }
    }
}
@Preview
@Composable
fun IntroScreenPreview(){
    val listState = rememberLazyListState()
    LaunchedEffect(Unit) {
        snapshotFlow { listState.firstVisibleItemScrollOffset }
            .collect { Log.d("DUCLUONG","The value is $it") }
    }
    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn(state = listState) {
            items(100){
                it->
                Text(text = "$it", modifier = Modifier.fillMaxWidth().padding(10.dp).height(100.dp))
            }
        }
    }

}
@Composable
fun IntroScreen(getInClick:()->Unit){
    MovieAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MovieAppTheme.colorScheme.backGroundColor)
        ){

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ){
                Spacer(modifier = Modifier.height(MovieAppTheme.dimensionValue.spacer1*8))
                HeaderSection()
                FooterSection(getInClick)
            }
        }
    }

}
@Composable
fun HeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
    ){
        Image(painter = painterResource(id = R.drawable.bg2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.matchParentSize()
        ){
            Image(painter = painterResource(id = R.drawable.woman),
                contentDescription = null,
                modifier = Modifier.size(360.dp))
            Spacer (modifier= Modifier.height(MovieAppTheme.dimensionValue.spacer1*4))
            Text(
                text = "Watch Videos in \nVirtual Reality",
                style = MovieAppTheme.appTypoTheme.textRecommend.copy(fontSize = 40.sp, textAlign = TextAlign.Center)
            )
            Spacer (modifier=Modifier.height(MovieAppTheme.dimensionValue.spacer1*4))
            Text(
                text = "Download and watch offline\nwherever you are",
                style=MovieAppTheme.appTypoTheme.textFieldTitle.copy(textAlign = TextAlign.Center, fontSize = 18.sp, color = Color.White.copy(alpha = 0.5f))
            )
        }
    }
}


@Composable
fun FooterSection(onGetInClick:()->Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ){
        Image(painter = painterResource(id = R.drawable.bg2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize())
        Button(
            onClick = onGetInClick,
            modifier = Modifier.size(200.dp,50.dp).align(Alignment.Center),
            shape = RoundedCornerShape(MovieAppTheme.dimensionValue.roundCornerForButton),
            border = BorderStroke(
                width = MovieAppTheme.dimensionValue.borderWidthForButton,
                brush = Brush.linearGradient(
                colors = MovieAppTheme.colorScheme.linearGradientColorsForButton
            )),
            colors = ButtonDefaults.buttonColors(
                containerColor = MovieAppTheme.colorScheme.containerColorForButton,
                contentColor = MovieAppTheme.colorScheme.contentColorForButton
            )
        ){
            Text(text = "Get In" , style = MovieAppTheme.appTypoTheme.textFieldTitle)
        }
    }
}
@Composable
fun CustomDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .size(250.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column {
                Text("Tùy chỉnh Dialog", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onDismiss) {
                    Text("Đóng")
                }
            }
        }
    }
}

@Composable
fun MyDialog(showDialog: Boolean, onDismiss: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Thông báo") },
            text = { Text("Bạn có chắc muốn xóa không?") },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("Đồng ý")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Hủy")
                }
            }
        )
    }
}
