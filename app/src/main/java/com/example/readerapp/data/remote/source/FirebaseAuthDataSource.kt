package com.example.readerapp.data.remote.source

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import androidx.core.net.toUri

class FirebaseAuthDataSource @Inject constructor(
    private val auth: FirebaseAuth
) {

    suspend fun createUserWithEmail(email: String, password: String): Result<FirebaseUser> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()

            Result.success(authResult.user ?: throw Exception("user is null"))
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    suspend fun signInWithEmail(email: String, password: String): Result<FirebaseUser> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()

            Result.success(authResult.user ?: throw Exception("user is null"))
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    suspend fun authWithGoogle(idToken: String): Result<FirebaseUser> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = auth.signInWithCredential(credential).await()

            Result.success(authResult.user ?: throw Exception("user is null"))
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUserProfile(name: String?, photoUri: String?): Result<Unit> {
        return try {
            val user = getCurrentUser() ?: throw Exception("блин, как?!")
            val userUpdate = UserProfileChangeRequest.Builder()
                .apply {
                    name?.let { setDisplayName(it) }
                    photoUri?.let { setPhotoUri(it.toUri()) }
                }
                .build()
            user.updateProfile(userUpdate).await()
            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun signOut() {
        auth.signOut()
    }
}