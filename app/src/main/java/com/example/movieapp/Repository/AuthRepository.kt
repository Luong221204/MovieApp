package com.example.movieapp.Repository

import android.content.Intent
import com.example.movieapp.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    suspend fun loginWithFacebookToken(token: String): LoginResult {
        return try {
            val credential = FacebookAuthProvider.getCredential(token)
            val result = auth.signInWithCredential(credential).await()
            val user = result.user
            if (user != null) {
                LoginResult.Success(user)
            } else {
                LoginResult.Failure("Người dùng không tồn tại.")
            }
        } catch (e: Exception) {
            LoginResult.Failure(e.localizedMessage ?: "Lỗi không xác định.")
        }
    }

    suspend fun firebaseAuthWithGoogle(token: String?): LoginResult? {
        return try {
            val credential = GoogleAuthProvider.getCredential(token, null)
            val result =auth.signInWithCredential(credential).await()
            val user = result.user
            if (user != null) {
                LoginResult.Success(user)
            } else {
                LoginResult.Failure("Người dùng không tồn tại.")
            }
        } catch (e: Exception) {
            LoginResult.Failure(e.localizedMessage ?: "Lỗi không xác định.")
        }
    }
}
