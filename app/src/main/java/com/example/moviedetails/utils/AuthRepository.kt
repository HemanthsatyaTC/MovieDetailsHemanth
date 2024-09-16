package com.example.moviedetails.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class AuthRepository (private val auth: FirebaseAuth) {

    fun signIn(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful, task.exception?.message)
            }
    }

    fun signUp(
        email: String,
        password: String,
        name: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()
                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { updateTask ->
                            onResult(updateTask.isSuccessful, updateTask.exception?.message)
                        }
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }
}