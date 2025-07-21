package com.example.movieapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            LoginScreen {
                startActivity(Intent(this,MainActivity::class.java))
            }
        }
    }
}

@Preview
@Composable
fun previewLoginScreen(){
    LoginScreen {  }
}
@Composable
fun LoginScreen(onLoginClick :() -> Unit){
    MovieAppTheme{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MovieAppTheme.colorScheme.backGroundColor)
        ){
            Image(painter = painterResource(id = R.drawable.bg1),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize())
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        horizontal = MovieAppTheme.dimensionValue.horizontalPadding,
                        vertical = MovieAppTheme.dimensionValue.verticalPadding)
            ) {
                Spacer(modifier = Modifier.height(MovieAppTheme.dimensionValue.spacer1*10))
                Image(
                    painter = painterResource(R.drawable.woman),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                )
                Spacer(modifier = Modifier.height(MovieAppTheme.dimensionValue.spacer1*4))
                Text(
                    text="Welcome to the mini theater",
                    modifier = Modifier.width(200.dp),
                    style = MovieAppTheme.appTypoTheme.buttonTitle,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(MovieAppTheme.dimensionValue.spacer1*4))
                GradientTextField(
                    hint = "Username",
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(MovieAppTheme.dimensionValue.spacer1*3))
                GradientTextField(
                    hint = "Password" ,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(MovieAppTheme.dimensionValue.spacer1*3))
                Text(
                    text  = "Forgot your password ?",
                    style = MovieAppTheme.appTypoTheme.textFieldOutline.copy(fontSize = 13.sp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(MovieAppTheme.dimensionValue.spacer1*3))
                GradientButton(text = "Login",
                    onClick = onLoginClick,
                    modifier = Modifier.size(180.dp,50.dp))
                Spacer(modifier = Modifier.height(MovieAppTheme.dimensionValue.spacer1*3))
                Row(horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.width(140.dp)) {
                    OtherWayToLoginButton(
                        text = "Google",
                        onClick = {},
                        modifier = Modifier.size(MovieAppTheme.dimensionValue.iconSize),
                        resourceImage = R.drawable.google
                    )
                    OtherWayToLoginButton(
                        text = "FaceBook",
                        onClick = {},
                        modifier = Modifier.size(MovieAppTheme.dimensionValue.iconSize),
                        resourceImage = R.drawable.facebook
                    )
                }
            }
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradientTextField(
    hint :String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions=KeyboardOptions.Default
){
    Box(modifier = modifier
        .height(MovieAppTheme.dimensionValue.heightForTextField)
        .background(
            brush = Brush.linearGradient(
                colors = MovieAppTheme.colorScheme.linearGradientColorsForTextField
            ),
            shape = RoundedCornerShape(MovieAppTheme.dimensionValue.roundCornerForTextField)
        )
        .padding(MovieAppTheme.dimensionValue.borderWidthForTextField)
    ){
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = {
                Text(
                    text = hint,
                    style = MovieAppTheme.appTypoTheme.textFieldOutline.copy(color = Color.White.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                    )
            },
            singleLine = true,
            textStyle = TextStyle(
                color = Color.White,
                textAlign = TextAlign.Start
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MovieAppTheme.colorScheme.focusedBorderColor,
                unfocusedBorderColor = MovieAppTheme.colorScheme.unfocusedBorderColor,
                cursorColor = MovieAppTheme.colorScheme.cursorColor,
                focusedLabelColor = MovieAppTheme.colorScheme.focusedLabelColor,
                unfocusedLabelColor = MovieAppTheme.colorScheme.unfocusedLabelColor
            ),
            keyboardOptions = keyboardOptions,
            modifier = Modifier
                .height(MovieAppTheme.dimensionValue.heightForTextField)
                .fillMaxWidth()
                .background(
                    color = colorResource(R.color.black1),
                    shape = RoundedCornerShape(MovieAppTheme.dimensionValue.roundCornerForTextField)
                )
                .align(Alignment.Center)
        )
    }
}

@Composable
fun GradientButton(
    text:String,
    onClick: ()-> Unit,
    modifier: Modifier = Modifier
){
    Button(
        onClick=onClick,
        modifier = modifier,
        shape  = RoundedCornerShape(MovieAppTheme.dimensionValue.roundCornerForButton),
        border = BorderStroke(
            width = MovieAppTheme.dimensionValue.borderWidthForButton,
            brush = Brush.linearGradient(
                colors = MovieAppTheme.colorScheme.linearGradientColorsForButton
            )
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = MovieAppTheme.colorScheme.containerColorForButton,
            contentColor = MovieAppTheme.colorScheme.contentColorForButton
        )
    ){
        Text(text = text , style = MovieAppTheme.appTypoTheme.textFieldTitle)
    }
}

@Composable
fun OtherWayToLoginButton(
    text:String,
    onClick: () -> Unit,
    modifier: Modifier,
    resourceImage:Int
){
    Image(
        painter = painterResource(id = resourceImage),
        contentDescription = text,
        modifier = modifier
            .clickable(role = Role.Button, onClick = onClick)
        )
}