package com.example.movieapp

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class LoginViewModel (
    private val repository: AuthRepository,
) : ViewModel(){
    var email by mutableStateOf("")
    var isLoginLoading by mutableStateOf(false)
    private var _number=MutableStateFlow(0)
    var number:StateFlow<Int> =_number
    var number2=number.map {

    }
    fun increaseNumber() {
        viewModelScope.launch {
            _number.value+=1
        }
    }

    var password by mutableStateOf("")
    var loginState by mutableStateOf<LoginResult?>(null)
        private set
    //login with facebook
    fun loginWithFacebookToken(token: AccessToken) {
        viewModelScope.launch {

            loginState = repository.loginWithFacebookToken(token.token)
        }
    }
    //login with google
    fun loginWithGoogle(data:Intent){
        viewModelScope.launch {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            val result = repository.loginWithGoogleToken(account.idToken)
            loginState = result
        }
    }


    fun getSignInIntent(activity:Context): Intent = repository.getGoogleSignInClient(activity).signInIntent

    fun onErrorLogin(){
        if(isLoginLoading) isLoginLoading = !isLoginLoading

    }
    fun onUpdateLoadingLoginStatus(){
        isLoginLoading = !isLoginLoading
    }
}
