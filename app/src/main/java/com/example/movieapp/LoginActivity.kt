package com.example.movieapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.movieapp.Repository.AuthRepository
import com.example.movieapp.ViewModel.LoginViewModel
import com.example.movieapp.ViewModel.LoginViewModelFactory
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.facebook.login.widget.LoginButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class LoginActivity : BaseActivity() {
    private lateinit var callbackManager: CallbackManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            FacebookSdk.sdkInitialize(LocalContext.current.applicationContext);
            callbackManager = CallbackManager.Factory.create()
            val repository = AuthRepository()
            val viewModel: LoginViewModel = ViewModelProvider(
                this,
                LoginViewModelFactory(repository, LocalContext.current)
            )[LoginViewModel::class.java]
            LoginScreen(modifier = Modifier.alpha(1f).clickable(enabled = false){}, viewModel=viewModel, activity = this) {
               // viewModel.increaseNumber()
                startActivity(Intent(this,MainActivity::class.java))
            }
            if(viewModel.isLoginLoading){
                CircularLoginProcess()
            }
        }
    }


}

@Preview
@Composable
fun previewLoginScreen(){
}
@Composable
fun LoginScreen(
    modifier: Modifier,
    context: Context = LocalContext.current,
    viewModel: LoginViewModel,
    activity: Activity,
    onLoginClick: () -> Unit

){
    val email = viewModel.email
    val password = viewModel.password
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val callbackManager = remember { CallbackManager.Factory.create() }
    val loginState = viewModel.loginState
    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginResult.Success -> {
                viewModel.onUpdateLoadingLoginStatus()
                Toast.makeText(context, "Xin chào: ${loginState.user.displayName}", Toast.LENGTH_SHORT).show()
                onLoginClick()
            }
            is LoginResult.Failure -> {
                viewModel.onErrorLogin()
                Toast.makeText(context, "Lỗi: ${loginState.error}", Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }
    LaunchedEffect(Unit) {

        (context as? ComponentActivity)?.activityResultRegistry?.register(
            "facebook_login",
            ActivityResultContracts.StartActivityForResult()
        ) {
            callbackManager.onActivityResult(it.resultCode, it.resultCode, it.data)
        }

    }
    LaunchedEffect(Unit) {
        viewModel.number2.collect{

        }
    }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        result.data?.let { viewModel.loginWithGoogle(it) }
    }
    MovieAppTheme{
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = MovieAppTheme.colorScheme.backGroundColor)

        ){
            Image(painter = painterResource(id = R.drawable.bg1),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier.matchParentSize())
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(
                        horizontal = MovieAppTheme.dimensionValue.horizontalPadding,
                        vertical = MovieAppTheme.dimensionValue.verticalPadding
                    )
            ) {
                Spacer(modifier = modifier.height(MovieAppTheme.dimensionValue.spacer1*10))
                Image(
                    painter = painterResource(R.drawable.woman),
                    contentDescription = null,
                    modifier = modifier
                        .size(200.dp)
                )
                Spacer(modifier = modifier.height(MovieAppTheme.dimensionValue.spacer1*4))
                Text(
                    text="Welcome to the mini theater",
                    modifier = modifier.width(200.dp),
                    style = MovieAppTheme.appTypoTheme.buttonTitle,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = modifier.height(MovieAppTheme.dimensionValue.spacer1*4))
                Email(
                    email = email,
                    modifier = modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
                ){
                    viewModel.email=it
                }
                Spacer(modifier = modifier.height(MovieAppTheme.dimensionValue.spacer1*3))
                Password(
                    password =password,
                    modifier = modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                    coroutineScope = coroutineScope,
                    scrollState = scrollState
                ){
                    viewModel.password=it
                }
                Spacer(modifier = modifier.height(MovieAppTheme.dimensionValue.spacer1*3))
                Text(
                    text  = "Forgot your password ?",
                    style = MovieAppTheme.appTypoTheme.textFieldOutline.copy(fontSize = 13.sp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(MovieAppTheme.dimensionValue.spacer1*3))
                GradientButton(
                    text = "Login",
                    onClickButton = onLoginClick,
                    modifier = modifier.size(180.dp,50.dp))
                Spacer(modifier = Modifier.height(MovieAppTheme.dimensionValue.spacer1*3))
                Row(horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.width(140.dp)) {
                    OtherWayToLoginButton(
                        text = "Google",
                        onClick = { onGoogleButtonClick(viewModel,launcher,activity) },
                        modifier = Modifier.size(MovieAppTheme.dimensionValue.iconSize),
                        resourceImage = R.drawable.google
                    )
                    OtherWayToLoginButton(
                        text = "FaceBook",
                        onClick = {
                            onFacebookButtonClick(viewModel,context,callbackManager)
                            },
                        modifier = Modifier.size(MovieAppTheme.dimensionValue.iconSize),
                        resourceImage = R.drawable.facebook
                    )
                }
                Spacer(modifier = Modifier.height(200.dp))
                Box( modifier = Modifier.fillMaxSize()){
                    Image(painter = painterResource(id = R.drawable.bg1),contentDescription = null, modifier = Modifier.fillMaxSize())
                }
            }

        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Email(
    email:String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions=KeyboardOptions.Default,
    onValueChange: (String) -> Unit
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
            value =email ,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = "Email",
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Password(
    password:String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions=KeyboardOptions.Default,
    coroutineScope:CoroutineScope?=null,
    scrollState:ScrollState?=null,
    onValueChange:(String)->Unit,
    ){
    val keyboardController= LocalSoftwareKeyboardController.current
    var isShowPassword by rememberSaveable {
        mutableStateOf(false)
    }
    OutlinedTextField(
        value = password,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = "Password",
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
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                coroutineScope?.launch {
                    scrollState?.animateScrollTo(-300)
                }
            }
        ),
        visualTransformation = if(isShowPassword) VisualTransformation.None else PasswordVisualTransformation() ,
        trailingIcon = {
            Icon(
                painter = painterResource(
                    id = if(isShowPassword) R.drawable.hide else R.drawable.show ),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        isShowPassword = !isShowPassword
                    },
                tint = Color.White
            )
        },
        modifier = modifier
            .onFocusEvent {
                if (it.isFocused) {
                    coroutineScope?.launch {
                        scrollState?.animateScrollTo(300)
                    }
                }
            }
            .height(MovieAppTheme.dimensionValue.heightForTextField)
            .fillMaxWidth()
            .border(
                BorderStroke(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = MovieAppTheme.colorScheme.linearGradientColorsForTextField
                    ),
                ),
                shape = RoundedCornerShape(MovieAppTheme.dimensionValue.roundCornerForTextField)
            )
    )
}
@Composable
fun GradientButton(
    text:String,
    onClickButton:()-> Unit,
    modifier: Modifier = Modifier
){
    Button(
        onClick=  onClickButton ,
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

fun onFacebookButtonClick(
    viewModel:LoginViewModel,
    context: Context,
    callbackManager:CallbackManager
){
    val activity = context as? Activity
    activity?.let{
        LoginManager.getInstance().logInWithReadPermissions(
            it,
            listOf("email", "public_profile")
        )
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<com.facebook.login.LoginResult> {
                override fun onCancel() {
                    Toast.makeText(context, "Đã hủy", Toast.LENGTH_SHORT).show()
                    viewModel.onErrorLogin()

                }
                override fun onError(error: FacebookException) {
                    Toast.makeText(context, "Lỗi: ${error.message}", Toast.LENGTH_SHORT).show()
                    viewModel.onErrorLogin()
                }

                override fun onSuccess(result: com.facebook.login.LoginResult) {
                    viewModel.loginWithFacebookToken(result.accessToken)
                }
            })
    }
    viewModel.onUpdateLoadingLoginStatus()
}
fun onGoogleButtonClick(
    viewModel:LoginViewModel,
    launcher:ManagedActivityResultLauncher<Intent,ActivityResult>,
    activity: Activity
){
    val intent = viewModel.getSignInIntent(activity)
    launcher.launch(intent)
    viewModel.onUpdateLoadingLoginStatus()
}


@Composable
fun CircularLoginProcess(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent.copy(alpha = 0.5f))
            .clickable(enabled = false){ }
    ){
        CircularProgressIndicator(
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .size(60.dp)
                .background(color = Color.Transparent)
        )
    }
}