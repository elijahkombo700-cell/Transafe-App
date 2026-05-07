package com.elijah.transafe.data

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.elijah.transafe.models.Trip

class TripViewModel(var navController: NavHostController, var context: Context) {

    fun startTrip() {
        // Logic to initialize a trip record
        Toast.makeText(context, "Trip Started", Toast.LENGTH_SHORT).show()
    }

    fun endTrip(trip: Trip) {
        // Logic to save trip summary
        Toast.makeText(context, "Trip Ended. Summary Saved.", Toast.LENGTH_SHORT).show()
    }

    fun getTrips(): List<Trip> {
        // Placeholder for fetching trip history
        return emptyList()
    }
}
