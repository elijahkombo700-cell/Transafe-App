package com.elijah.transafe.data

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.elijah.transafe.models.User
import com.elijah.transafe.navigation.ROUTE_HOME
import com.elijah.transafe.navigation.ROUTE_LOGIN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AuthViewModel(var navController: NavHostController, var context: Context) {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val database by lazy { FirebaseDatabase.getInstance() }

    fun signup(name: String, email: String, pass: String, plateNumber: String) {
        if (name.isBlank() || email.isBlank() || pass.isBlank() || plateNumber.isBlank()) {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser?.uid ?: ""
                val user = User(userId, name, plateNumber, "")
                database.getReference("Users").child(userId).setValue(user).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(context, "Account created successfully", Toast.LENGTH_SHORT).show()
                        navController.navigate(ROUTE_HOME) {
                            popUpTo(ROUTE_LOGIN) { inclusive = true }
                        }
                    } else {
                        Toast.makeText(context, "Failed to save user data", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "Signup failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun login(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Save login info to database
                val userId = auth.currentUser?.uid ?: ""
                val loginData = mapOf(
                    "email" to email,
                    "lastLogin" to System.currentTimeMillis()
                )

                database.getReference("Users").child(userId).child("loginHistory")
                    .push().setValue(loginData)

                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                navController.navigate(ROUTE_HOME) {
                    popUpTo(ROUTE_LOGIN) { inclusive = true }
                }
            } else {
                Toast.makeText(context, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun logout() {
        auth.signOut()
        navController.navigate(ROUTE_LOGIN) {
            popUpTo(ROUTE_HOME) { inclusive = true }
        }
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}
