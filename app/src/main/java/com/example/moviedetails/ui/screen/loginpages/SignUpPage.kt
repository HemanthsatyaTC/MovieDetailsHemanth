package com.example.moviedetails.ui.screen.loginpages

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@Composable
fun SignUpPage(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(colors = CardDefaults.cardColors(
            containerColor = Color.Cyan,),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .size(width = 400.dp, height = 200.dp)
                .padding(16.dp)) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome to Movie Details",
                    color = Color.Red,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value = email,
            onValueChange = {
                email = it
                emailError = !it.contains("@")
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError
        )
        if (emailError) {
            Text(
                text = "Invalid email address",
                color = MaterialTheme.colors.error,
                modifier = Modifier.align(Alignment.Start)
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = it.length < 8
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = passwordError
        )
        if (passwordError) {
            Text(
                text = "Password must be at least 8 characters",
                color = MaterialTheme.colors.error,
                modifier = Modifier.align(Alignment.Start)
            )
        }
        Spacer(modifier = Modifier.height(15.dp))

        Button(onClick = {
            if (email.isBlank() || password.isBlank()){
                Toast.makeText(context, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show()

            }
            else SignInUser(auth, email, password, name, navController, context)
        }, modifier = Modifier.fillMaxWidth())
        {
            Text("Sign Up")
        }
        Spacer(modifier = Modifier.height(15.dp))

        Button(onClick = {
            navController.navigate("login")

        }, modifier = Modifier.fillMaxWidth())
        {
            Text("Back to Log In")
        }
    }
}

fun SignInUser(
    auth: FirebaseAuth,
    email: String,
    password: String,
    name: String,
    navController: NavHostController,
    context: android.content.Context
) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "createUserWithEmail:success")
                val user = auth.currentUser
                updateUI(user)
                navController.navigate("welcome/$name")

            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                Toast.makeText(
                    context,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }

}

private fun updateUI(user: FirebaseUser?) {
}
