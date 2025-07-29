package com.example.movieapp.ViewModel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.LoginResult
import com.example.movieapp.R
import com.example.movieapp.Repository.AuthRepository
import com.example.movieapp.domain.CastModel
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


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
