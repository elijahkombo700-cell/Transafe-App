package com.elijah.transafe.data

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.navigation.NavHostController
import com.elijah.transafe.models.Report

class ReportViewModel(var navController: NavHostController, var context: Context) {

    fun submitReport(title: String, description: String, location: String, imageUri: Uri?) {
        if (title.isBlank() || description.isBlank() || location.isBlank()) {
            Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Logic to upload report to Firebase/Backend would go here
        
        Toast.makeText(context, "Report submitted successfully. Thank you for making roads safer!", Toast.LENGTH_LONG).show()
        navController.navigateUp()
    }
}
