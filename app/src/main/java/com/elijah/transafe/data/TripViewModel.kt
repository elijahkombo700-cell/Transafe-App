package com.elijah.transafe.data

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.navigation.NavHostController
import com.elijah.transafe.models.Trip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.UUID

class TripViewModel(var navController: NavHostController, var context: Context) {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val database by lazy { FirebaseDatabase.getInstance() }
    
    val tripHistory = mutableStateListOf<Trip>()

    fun startTrip(startLocation: String = "Starting Point") {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(context, "Please log in first", Toast.LENGTH_SHORT).show()
            return
        }

        val tripId = UUID.randomUUID().toString()
        val userId = currentUser.uid
        val startTime = System.currentTimeMillis()

        // Initialize a new trip record
        val trip = Trip(
            id = tripId,
            userId = userId,
            startTime = startTime,
            endTime = 0L,
            distance = 0.0,
            safetyScore = 100,
            startLocation = startLocation,
            endLocation = ""
        )

        val tripRef = database.getReference("Trips").child(tripId)
        tripRef.setValue(trip).addOnSuccessListener {
            Toast.makeText(context, "Trip Started", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to start trip: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun endTrip(trip: Trip, endLocation: String, distance: Double, safetyScore: Int) {
        // Update the trip record with summary data
        trip.endTime = System.currentTimeMillis()
        trip.endLocation = endLocation
        trip.distance = distance
        trip.safetyScore = safetyScore

        val tripRef = database.getReference("Trips").child(trip.id)
        tripRef.setValue(trip).addOnSuccessListener {
            Toast.makeText(context, "Trip Ended. Summary Saved.", Toast.LENGTH_SHORT).show()
            navController.navigateUp()
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to save summary: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun fetchTripHistory() {
        val currentUser = auth.currentUser ?: return
        val tripsRef = database.getReference("Trips")
            .orderByChild("userId")
            .equalTo(currentUser.uid)

        tripsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tripHistory.clear()
                for (tripSnapshot in snapshot.children) {
                    val trip = tripSnapshot.getValue(Trip::class.java)
                    trip?.let { tripHistory.add(it) }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error fetching history: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getTrips(): List<Trip> {
        if (tripHistory.isEmpty()) {
            fetchTripHistory()
        }
        return tripHistory
    }
}
