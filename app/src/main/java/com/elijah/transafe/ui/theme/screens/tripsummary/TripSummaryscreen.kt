package com.elijah.transafe.ui.theme.screens.tripsummary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.elijah.transafe.navigation.ROUTE_HOME
import com.elijah.transafe.ui.theme.NewBackground
import com.elijah.transafe.ui.theme.OrangeMain
import com.elijah.transafe.ui.theme.Slate100
import com.elijah.transafe.ui.theme.Slate500
import com.elijah.transafe.ui.theme.Slate700
import com.elijah.transafe.ui.theme.SuccessGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripSummaryscreen(navController: NavHostController) {
    Scaffold(
        containerColor = Slate100,
        topBar = {
            TopAppBar(
                title = { Text("Trip Summary", fontWeight = FontWeight.Bold, color = OrangeMain) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(ROUTE_HOME) { popUpTo(ROUTE_HOME) { inclusive = true } } }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = OrangeMain)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = NewBackground
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            // Completion Status
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(120.dp)
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = CircleShape,
                    color = SuccessGreen.copy(alpha = 0.1f)
                ) {}
                Surface(
                    modifier = Modifier.size(80.dp),
                    shape = CircleShape,
                    color = SuccessGreen,
                    shadowElevation = 8.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Default.Check, 
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Trip Completed!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = Slate700
            )
            Text(
                text = "You arrived safely. Well done!",
                color = Slate500,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Summary Card
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    SummaryItem(Icons.Default.Timer, "Duration", "25 mins")
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 16.dp),
                        color = Slate100
                    )
                    SummaryItem(Icons.Default.LocationOn, "Distance", "12.4 km")
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 16.dp),
                        color = Slate100
                    )
                    SummaryItem(Icons.Default.CheckCircle, "Safety Points", "+50 XP", isSuccess = true)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { navController.navigate(ROUTE_HOME) { popUpTo(ROUTE_HOME) { inclusive = true } } },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OrangeMain),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text("BACK TO HOME", fontWeight = FontWeight.Bold, fontSize = 16.sp, letterSpacing = 1.sp)
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SummaryItem(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    isSuccess: Boolean = false
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(48.dp),
            shape = RoundedCornerShape(14.dp),
            color = if (isSuccess) SuccessGreen.copy(alpha = 0.1f) else Slate100,
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSuccess) SuccessGreen else OrangeMain,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = Slate500
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = if (isSuccess) SuccessGreen else Slate700
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TripSummaryScreenPreview() {
    val navController = rememberNavController()
    TripSummaryscreen(navController = navController)
}
