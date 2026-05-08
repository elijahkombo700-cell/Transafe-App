package com.elijah.transafe.data

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.navigation.NavHostController
import com.elijah.transafe.models.Report
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class ReportViewModel(var navController: NavHostController, var context: Context) {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val database by lazy { FirebaseDatabase.getInstance() }
    private val storage by lazy { FirebaseStorage.getInstance() }

    fun submitReport(title: String, description: String, location: String, imageUri: Uri?) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(context, "Please log in to submit a report", Toast.LENGTH_SHORT).show()
            return
        }

        if (title.isBlank() || description.isBlank() || location.isBlank()) {
            Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val reportId = UUID.randomUUID().toString()
        val userId = currentUser.uid
        val timestamp = System.currentTimeMillis()

        if (imageUri != null) {
            uploadImageAndReport(reportId, userId, title, description, location, timestamp, imageUri)
        } else {
            saveReportToDatabase(reportId, userId, title, description, location, timestamp, "")
        }
    }

    private fun uploadImageAndReport(
        reportId: String,
        userId: String,
        title: String,
        description: String,
        location: String,
        timestamp: Long,
        imageUri: Uri
    ) {
        val storageRef = storage.reference.child("reports/$reportId.jpg")
        
        storageRef.putFile(imageUri)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    saveReportToDatabase(reportId, userId, title, description, location, timestamp, uri.toString())
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to upload image: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveReportToDatabase(
        reportId: String,
        userId: String,
        title: String,
        description: String,
        location: String,
        timestamp: Long,
        imageUrl: String
    ) {
        val report = Report(reportId, userId, title, description, location, timestamp, imageUrl)
        val reportsRef = database.getReference("Reports").child(reportId)

        reportsRef.setValue(report)
            .addOnSuccessListener {
                Toast.makeText(context, "Report submitted successfully!", Toast.LENGTH_LONG).show()
                navController.navigateUp()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to submit report: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
