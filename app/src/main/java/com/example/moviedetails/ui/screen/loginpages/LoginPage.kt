package com.example.moviedetails.ui.screen.loginpages

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moviedetails.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider

@SuppressLint("RememberReturnType")
@Composable
fun LoginPage(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val googleSignInClient = remember { getGoogleSignInClient(context) }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(Exception::class.java)
                account?.let {
                    firebaseAuthWithGoogle(it.idToken ?: "", auth, navController, context)
                }
            } catch (e: Exception) {
                Log.w("LoginPage", "Google sign in failed", e)
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
//        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(colors = CardDefaults.cardColors(
            containerColor = Color.Cyan,),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .size(width = 400.dp, height = 200.dp)
                .padding(5.dp)) {
            Column(
                modifier = Modifier
                    .padding(10.dp),
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
        Text(
            text = "Continue Login Using",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
//        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.dp),
            horizontalArrangement = Arrangement.Center

        ) {
            Image(
                painter = painterResource(id = R.drawable.google_icons),
                contentDescription = "Login with Google",
                modifier = Modifier
                    .size(100.dp)
                    .clickable {
                        val signInIntent = googleSignInClient.signInIntent
                        launcher.launch(signInIntent)
                    }
                    .padding(10.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(100.dp))

            Image(
                painter = painterResource(id = R.drawable.githublogo),
                contentDescription = "Login with GitHub",
                modifier = Modifier
                    .size(100.dp)
                    .clickable {
                        github(auth, context, navController)
                    }
                    .padding(20.dp),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Id") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(15.dp))

        Button(

            onClick = {
                if (email.isBlank() || password.isBlank()){
                    Toast.makeText(context, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show()

                }
                else LoginUser(auth, email, password, navController, context)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log In")
        }
        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {
                navController.navigate("signup")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go to Sign Up")
        }
    }
}

fun LoginUser(
    auth: FirebaseAuth,
    email: String,
    password: String,
    navController: NavHostController,
    context: android.content.Context
) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success")
                val user = auth.currentUser
                updateUI(user)
                navController.navigate("welcome/ ${task.result.user?.email} ")
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.exception)
                Toast.makeText(
                    context,
                    "Authentication failed. Try log in using valid Email ID and Password",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }

}

private fun updateUI(user: FirebaseUser?) {
}

fun getGoogleSignInClient(context: android.content.Context): GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.your_web_client_id)) // Add this in strings.xml
        .requestEmail()
        .build()
    return GoogleSignIn.getClient(context, gso)
}

fun firebaseAuthWithGoogle(
    idToken: String,
    auth: FirebaseAuth,
    navController: NavHostController,
    context: android.content.Context
) {
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    auth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                user?.let {
                    navController.navigate("welcome/${it.displayName ?: "User"}")
                }
            } else {
                Toast.makeText(context, "Google Authentication Failed.", Toast.LENGTH_SHORT).show()
                Log.w("LoginPage", "signInWithCredential:failure", task.exception)
            }
        }
}

fun github(auth: FirebaseAuth, context: android.content.Context, navController: NavHostController,) {
    val provider = OAuthProvider.newBuilder("github.com")
    provider.addCustomParameter("login", "hemanth.arvapalli@technconsultant.tech")
    provider.scopes = listOf("user:email")

    val pendingResultTask = auth.pendingAuthResult
    if (pendingResultTask != null) {
        pendingResultTask
            .addOnSuccessListener {
                navController.navigate("welcome/ ${it.user?.email} ")

            }
            .addOnFailureListener {
                Toast.makeText(context,"Failed to github", Toast.LENGTH_SHORT).show()

            }
    } else {
        auth
            .startActivityForSignInWithProvider(context as Activity, provider.build())
            .addOnSuccessListener {
                navController.navigate("welcome/ ${it.user?.email} ")
            }
            .addOnFailureListener {
                Toast.makeText(context,"Failed to github", Toast.LENGTH_SHORT).show()
            }

    }

}