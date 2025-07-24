package com.example.movieapp

import com.google.firebase.auth.FirebaseUser

sealed class LoginResult {
    data class Success(val user: FirebaseUser) : LoginResult()
    data class Failure(val error: String) : LoginResult()
}