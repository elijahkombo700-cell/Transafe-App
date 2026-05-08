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
import com.elijah.transafe.ui.theme.AlertRed
import com.elijah.transafe.ui.theme.NewBackground
import com.elijah.transafe.ui.theme.OrangeMain
import com.elijah.transafe.ui.theme.Slate100
import com.elijah.transafe.ui.theme.Slate500
import com.elijah.transafe.ui.theme.Slate700
import com.elijah.transafe.ui.theme.SuccessGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboardscreen(navController: NavHostController) {
    Scaffold(
        containerColor = Slate100,
        topBar = {
            TopAppBar(
                title = { Text("Safety Dashboard", fontWeight = FontWeight.Bold, color = OrangeMain) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = OrangeMain)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = NewBackground
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Hero Section: Awareness Campaign
            item {
                Spacer(modifier = Modifier.height(8.dp))
                SafetyHeroSection()
            }

            // Quick Tips: Horizontal Scroll
            item {
                SectionHeader("Daily Safety Tips", "See all")
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(safetyTips) { tip ->
                        SafetyTipCard(tip)
                    }
                }
            }

            // Educational Modules: List
            item {
                SectionHeader("Educational Modules", null)
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
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
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
                    .background(Color.Black.copy(alpha = 0.45f))
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "Arrive Alive",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Safe driving saves lives. Join our mission for 0 road fatalities.",
                    color = Color.White.copy(alpha = 0.9f),
                    style = MaterialTheme.typography.bodyMedium
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
        Text(
            text = title, 
            style = MaterialTheme.typography.titleLarge, 
            fontWeight = FontWeight.Bold,
            color = Slate700
        )
        if (actionText != null) {
            Text(
                text = actionText,
                color = OrangeMain,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.clickable { }
            )
        }
    }
}

@Composable
fun SafetyTipCard(tip: SafetyTip) {
    ElevatedCard(
        modifier = Modifier.width(260.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Surface(
                color = OrangeMain.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    tip.icon, 
                    contentDescription = null, 
                    tint = OrangeMain,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = tip.title, 
                fontWeight = FontWeight.Bold, 
                fontSize = 17.sp,
                color = Slate700
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = tip.description, 
                style = MaterialTheme.typography.bodySmall, 
                color = Slate500,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun EducationModuleItem(module: EducationModule) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(OrangeMain.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(module.icon, contentDescription = null, tint = OrangeMain)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = module.title, 
                    fontWeight = FontWeight.Bold,
                    color = Slate700,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = module.duration, 
                    style = MaterialTheme.typography.bodySmall, 
                    color = Slate500
                )
            }
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { module.progress },
                    modifier = Modifier.size(32.dp),
                    color = OrangeMain,
                    strokeWidth = 4.dp,
                    trackColor = Slate100,
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                )
                if (module.progress >= 1f) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = OrangeMain, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Composable
fun EmergencySection() {
    Column(modifier = Modifier.padding(top = 8.dp)) {
        Text(
            "Emergency Response", 
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            color = Slate700
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            EmergencyButton(Icons.Default.Phone, "Call Police", AlertRed, Modifier.weight(1f))
            EmergencyButton(Icons.Default.LocalHospital, "Ambulance", SuccessGreen, Modifier.weight(1f))
        }
    }
}

@Composable
fun EmergencyButton(icon: ImageVector, label: String, color: Color, modifier: Modifier) {
    Button(
        onClick = { },
        modifier = modifier.height(60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(18.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(label, fontWeight = FontWeight.Bold, fontSize = 14.sp)
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
