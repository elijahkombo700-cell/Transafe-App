package com.elijah.transafe.ui.theme.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.elijah.transafe.R
import com.elijah.transafe.ui.theme.OrangeMain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboardscreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Safety Dashboard", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // Hero Section: Awareness Campaign
            item {
                SafetyHeroSection()
            }

            // Quick Tips: Horizontal Scroll
            item {
                SectionHeader("Daily Safety Tips", "See all")
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(safetyTips) { tip ->
                        SafetyTipCard(tip)
                    }
                }
            }

            // Educational Modules: List
            item {
                SectionHeader("Educational Modules", null)
                Spacer(modifier = Modifier.height(12.dp))
            }

            items(educationModules) { module ->
                EducationModuleItem(module)
            }

            // Emergency Contacts Quick Access
            item {
                EmergencySection()
            }
        }
    }
}

@Composable
fun SafetyHeroSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.safety),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "Arrive Alive",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Safe driving saves lives. Join our mission for 0 road fatalities.",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, actionText: String?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        if (actionText != null) {
            Text(
                text = actionText,
                color = OrangeMain,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { }
            )
        }
    }
}

@Composable
fun SafetyTipCard(tip: SafetyTip) {
    Card(
        modifier = Modifier.width(240.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(tip.icon, contentDescription = null, tint = OrangeMain)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = tip.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = tip.description, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun EducationModuleItem(module: EducationModule) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(OrangeMain.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(module.icon, contentDescription = null, tint = OrangeMain)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = module.title, fontWeight = FontWeight.Bold)
                Text(text = module.duration, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            CircularProgressIndicator(
                progress = { module.progress },
                modifier = Modifier.size(24.dp),
                color = OrangeMain,
                strokeWidth = 3.dp,
                trackColor = Color.LightGray.copy(alpha = 0.3f),
            )
        }
    }
}

@Composable
fun EmergencySection() {
    Column {
        Text("Emergency Response", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            EmergencyButton(Icons.Default.Phone, "Call Police", Color(0xFFE53935), Modifier.weight(1f))
            EmergencyButton(Icons.Default.LocalHospital, "Ambulance", Color(0xFF43A047), Modifier.weight(1f))
        }
    }
}

@Composable
fun EmergencyButton(icon: ImageVector, label: String, color: Color, modifier: Modifier) {
    Button(
        onClick = { },
        modifier = modifier.height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(label, fontWeight = FontWeight.Bold)
    }
}

data class SafetyTip(val icon: ImageVector, val title: String, val description: String)
data class EducationModule(val icon: ImageVector, val title: String, val duration: String, val progress: Float)

val safetyTips = listOf(
    SafetyTip(Icons.Default.Speed, "Keep Distance", "Maintain 2s gap between cars."),
    SafetyTip(Icons.Default.Vibration, "No Phones", "Distraction is the #1 cause of crashes."),
    SafetyTip(Icons.Default.Nightlight, "Rest Well", "Never drive while feeling drowsy.")
)

val educationModules = listOf(
    EducationModule(Icons.Default.Traffic, "Understanding Road Signs", "10 mins", 0.7f),
    EducationModule(Icons.Default.CarRepair, "Vehicle Maintenance 101", "15 mins", 0.3f),
    EducationModule(Icons.Default.Cloud, "Driving in Bad Weather", "8 mins", 0f)
)

@Preview(showBackground = true)
@Composable
fun DashboardscreenPreview() {
    Dashboardscreen(navController = rememberNavController())
}
