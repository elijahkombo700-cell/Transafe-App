package com.elijah.transafe.data

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.navigation.NavHostController
import com.elijah.transafe.models.User

class UserViewModel(var navController: NavHostController, var context: Context) {

    fun saveUserProfile(name: String, plateNumber: String, imageUri: Uri?) {
        if (name.isBlank() || plateNumber.isBlank()) {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Logic to save to Firebase or local DB would go here
        // val user = User(name = name, plateNumber = plateNumber, profileImageUrl = imageUri.toString())
        
        Toast.makeText(context, "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
        navController.navigateUp()
    }
}
