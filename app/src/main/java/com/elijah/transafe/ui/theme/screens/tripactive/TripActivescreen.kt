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
import com.elijah.transafe.ui.theme.AlertRed
import com.elijah.transafe.ui.theme.NewBackground
import com.elijah.transafe.ui.theme.OrangeMain
import com.elijah.transafe.ui.theme.SuccessGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripActivescreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        // 1. Map View (Placeholder)
        Image(
            painter = painterResource(id = R.drawable.traffic),
            contentDescription = "Map Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.5f
        )

        // 2. Navigation Top Bar
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding(),
            color = NewBackground,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.navigate(ROUTE_HOME) },
                    colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "To: Westlands, Nairobi",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = OrangeMain
                    )
                    Text(
                        text = "Navigating via Thika Superhighway",
                        style = MaterialTheme.typography.bodySmall,
                        color = OrangeMain
                    )
                }
            }
        }

        // 3. Traffic Alert Overlay
        Card(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 110.dp, start = 16.dp, end = 16.dp),
            colors = CardDefaults.cardColors(containerColor = OrangeMain.copy(alpha = 0.15f)),
            border = androidx.compose.foundation.BorderStroke(1.dp, OrangeMain.copy(alpha = 0.3f)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Warning, contentDescription = null, tint = OrangeMain)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Slow traffic ahead. ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = OrangeMain
                )
            }
        }

        // 4. Floating Report Button
        LargeFloatingActionButton(
            onClick = { navController.navigate(ROUTE_REPORT) },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 20.dp),
            containerColor = AlertRed,
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
                .navigationBarsPadding()
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 12.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier.size(56.dp),
                            shape = CircleShape,
                            color = SuccessGreen.copy(alpha = 0.15f)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Timer, contentDescription = null, tint = SuccessGreen)
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = "25 min", fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
                            Text(text = "12.4 km remaining", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
                        }
                    }

                    Button(
                        onClick = { 
                            navController.navigate(ROUTE_TRIP_SUMMARY) { 
                                popUpTo(ROUTE_HOME) { inclusive = false } 
                            } 
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AlertRed),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.height(56.dp).padding(start = 16.dp)
                    ) {
                        Text("END TRIP", fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
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
