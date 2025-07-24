package com.example.movieapp.ViewModel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.LoginResult
import com.example.movieapp.R
import com.example.movieapp.Repository.AuthRepository
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch

class LoginViewModel constructor(
    private val repository: AuthRepository,
    private val context: Context
) : ViewModel(){
    var loginState by mutableStateOf<LoginResult?>(null)
        private set

    fun loginWithFacebookToken(token: AccessToken) {
        viewModelScope.launch {
            loginState = repository.loginWithFacebookToken(token.token)
        }
    }
    var email by mutableStateOf("")
    var password by mutableStateOf("")


    fun loginWithGoogle(data:Intent){
        viewModelScope.launch {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            val result = repository.firebaseAuthWithGoogle(account.idToken)
            loginState = result
        }
    }

    private val googleSignInClient: GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.client_id))
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }
    fun getSignInIntent(): Intent = googleSignInClient.signInIntent

}