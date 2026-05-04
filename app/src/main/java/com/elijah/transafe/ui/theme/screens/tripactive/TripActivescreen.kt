package com.elijah.transafe.ui.theme.screens.tripactive

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.elijah.transafe.R
import com.elijah.transafe.navigation.ROUTE_HOME
import com.elijah.transafe.navigation.ROUTE_REPORT
import com.elijah.transafe.navigation.ROUTE_TRIP_SUMMARY

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripActivescreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Map View (Placeholder)
        Image(
            painter = painterResource(id = R.drawable.traffic),
            contentDescription = "Map Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.6f
        )

        // 2. Navigation Top Bar
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding(),
            color = Color.White.copy(alpha = 0.9f),
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.navigate(ROUTE_HOME) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "To: Westlands, Nairobi",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "Navigating via Thika Rd",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        // 3. Traffic Alert Overlay
        Card(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 100.dp, start = 16.dp, end = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFB74D)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFF57C00))
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Traffic ahead in 2 km. ETA +5 mins",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFE65100)
                )
            }
        }

        // 4. Floating Report Button
        FloatingActionButton(
            onClick = { navController.navigate(ROUTE_REPORT) },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            containerColor = Color.Red,
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Row(modifier = Modifier.padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("REPORT", fontWeight = FontWeight.Bold)
            }
        }

        // 5. Bottom Status Panel
        ElevatedCard(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier.size(48.dp),
                            shape = CircleShape,
                            color = Color(0xFFE8F5E9)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Timer, contentDescription = null, tint = Color(0xFF2E7D32))
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = "25 min", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                            Text(text = "12 km", color = Color.Gray, fontSize = 14.sp)
                        }
                    }

                    Button(
                        onClick = { navController.navigate(ROUTE_TRIP_SUMMARY) { popUpTo(ROUTE_HOME) { inclusive = true } } },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(56.dp).width(120.dp)
                    ) {
                        Text("END TRIP", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TripActivescreenPreview() {
    TripActivescreen(navController = rememberNavController())
}
