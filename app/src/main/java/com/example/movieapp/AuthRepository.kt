package com.example.movieapp

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthRepository (
) {
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

    suspend fun loginWithGoogleToken(token: String?): LoginResult? {
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
    fun getGoogleSignInClient(activity: Context):GoogleSignInClient{
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(activity, gso)
    }
}
