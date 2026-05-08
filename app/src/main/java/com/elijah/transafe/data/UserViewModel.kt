package com.elijah.transafe.data

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.navigation.NavHostController
import com.elijah.transafe.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class UserViewModel(var navController: NavHostController, var context: Context) {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val database by lazy { FirebaseDatabase.getInstance() }
    private val storage by lazy { FirebaseStorage.getInstance() }

    fun saveUserProfile(name: String, plateNumber: String, imageUri: Uri?) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(context, "Please log in first", Toast.LENGTH_SHORT).show()
            return
        }

        if (name.isBlank() || plateNumber.isBlank()) {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = currentUser.uid

        if (imageUri != null) {
            uploadProfileImage(userId, name, plateNumber, imageUri)
        } else {
            saveUserData(userId, name, plateNumber, "")
        }
    }

    private fun uploadProfileImage(userId: String, name: String, plateNumber: String, imageUri: Uri) {
        val storageRef = storage.reference.child("profile_images/$userId.jpg")
        
        storageRef.putFile(imageUri)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    saveUserData(userId, name, plateNumber, uri.toString())
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to upload image: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveUserData(userId: String, name: String, plateNumber: String, imageUrl: String) {
        val user = User(userId, name, plateNumber, imageUrl)
        val userRef = database.getReference("Users").child(userId)

        userRef.setValue(user)
            .addOnSuccessListener {
                Toast.makeText(context, "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
                navController.navigateUp()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to update profile: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
