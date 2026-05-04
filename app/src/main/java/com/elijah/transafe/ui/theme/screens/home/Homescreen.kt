package com.elijah.transafe.ui.theme.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elijah.transafe.R
import com.elijah.transafe.ui.theme.TransafeTheme

import androidx.navigation.NavHostController
import com.elijah.transafe.navigation.ROUTE_PROFILE
import com.elijah.transafe.navigation.ROUTE_REPORT
import com.elijah.transafe.navigation.ROUTE_TRIP_SUMMARY
import com.elijah.transafe.navigation.ROUTE_DASHBOARD
import com.elijah.transafe.navigation.ROUTE_TRIP_ACTIVE

enum class Severity { HIGH, MEDIUM }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Homescreen(navController: NavHostController? = null) {
    var isInsideBlackspot by remember { mutableStateOf(false) } // Simulated GPS/Hazard detection
    val sheetState = rememberModalBottomSheetState()
    var showReportSheet by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Map View (Placeholder)
        MapPlaceholder(modifier = Modifier.fillMaxSize())

        // Safety "Blackspot" Detection Glow (Professional UI Warning Overlay)
        AnimatedVisibility(
            visible = isInsideBlackspot,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Red.copy(alpha = 0.25f), Color.Transparent, Color.Transparent)
                        )
                    )
            )
        }

        // 2. Search Bar & Contextual Alert (Top)
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 48.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SearchBar(
                onProfileClick = { navController?.navigate(ROUTE_PROFILE) },
                onDashboardClick = { navController?.navigate(ROUTE_DASHBOARD) }
            )

            // Quick Access "Safety Hub" Button
            SafetyHubButton(onClick = { navController?.navigate(ROUTE_DASHBOARD) })
            
            // Smart Alert Banner: contextual to current road conditions in Kenya
            AlertBanner(
                message = if (isInsideBlackspot) "DANGER: Entering Accident Blackspot" else "Heavy traffic reported at Museum Hill",
                timeAgo = if (isInsideBlackspot) "Active Now" else "3 mins ago",
                severity = if (isInsideBlackspot) Severity.HIGH else Severity.MEDIUM
            )
        }

        // 3. Floating Action Buttons (Right side)
        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Mock Toggle for Blackspot Demo (Professional Small FAB)
            SmallFloatingActionButton(
                onClick = { isInsideBlackspot = !isInsideBlackspot },
                containerColor = if (isInsideBlackspot) Color.Red else Color.White,
                contentColor = if (isInsideBlackspot) Color.White else Color.Black,
                shape = CircleShape,
            ) {
                Icon(
                    imageVector = if (isInsideBlackspot) Icons.Default.Warning else Icons.Default.LocationOn, 
                    contentDescription = "Toggle Blackspot Detection"
                )
            }

            FloatingActionButton(
                onClick = { showReportSheet = true },
                containerColor = Color.Red,
                contentColor = Color.White,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Report Incident", modifier = Modifier.size(32.dp))
            }
        }

        // 4. Trip Bottom Panel
        BottomPanel(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp, start = 16.dp, end = 16.dp),
            isTripStarted = false,
            onTripToggle = { 
                navController?.navigate(ROUTE_TRIP_ACTIVE)
            }
        )

        // 5. Enhanced Report Bottom Sheet (Kenyan Road Hazards)
        if (showReportSheet) {
            ModalBottomSheet(
                onDismissRequest = { showReportSheet = false },
                sheetState = sheetState,
                containerColor = Color.White,
                dragHandle = { BottomSheetDefaults.DragHandle() }
            ) {
                IncidentReportGrid(onReportSelected = { incidentType ->
                    showReportSheet = false
                    navController?.navigate(ROUTE_REPORT)
                })
            }
        }
    }
}

@Composable
fun MapPlaceholder(modifier: Modifier = Modifier) {
    Box(modifier = modifier.background(Color(0xFFF0F0F0))) {
        Image(
            painter = painterResource(id = R.drawable.traffic),
            contentDescription = "Map Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.5f
        )
        
        // Simulated navigation route line
        Box(
            modifier = Modifier
                .size(width = 6.dp, height = 400.dp)
                .background(Color(0xFF2196F3).copy(alpha = 0.7f), shape = RoundedCornerShape(3.dp))
                .align(Alignment.Center)
        )

        // Simulated current location marker
        Surface(
            modifier = Modifier.size(32.dp).align(Alignment.Center),
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 6.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Box(modifier = Modifier.size(20.dp).background(Color(0xFF2196F3), CircleShape))
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier, 
    onProfileClick: () -> Unit = {},
    onDashboardClick: () -> Unit = {}
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth().height(56.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp).fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onProfileClick) {
                Icon(
                    Icons.Default.AccountCircle, 
                    contentDescription = "Profile", 
                    tint = Color.Gray,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Where to? (e.g. Westlands)",
                color = Color.Gray,
                modifier = Modifier.weight(1f),
                fontSize = 15.sp
            )
            IconButton(onClick = onDashboardClick) {
                Icon(Icons.Default.Shield, contentDescription = "Safety Hub", tint = Color(0xFFF57C00))
            }
            VerticalDivider(modifier = Modifier.height(24.dp).padding(horizontal = 4.dp), color = Color.LightGray)
            IconButton(onClick = { }) {
                Icon(Icons.Default.Mic, contentDescription = "Voice Search", tint = Color(0xFF2196F3))
            }
        }
    }
}

@Composable
fun SafetyHubButton(onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF57C00).copy(alpha = 0.1f),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF57C00).copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.AutoGraph, contentDescription = null, tint = Color(0xFFF57C00))
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Safety & Education Hub",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFFF57C00)
                )
                Text(
                    text = "Learn road safety measures and tips",
                    fontSize = 11.sp,
                    color = Color(0xFFF57C00).copy(alpha = 0.8f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFFF57C00))
        }
    }
}

@Composable
fun AlertBanner(message: String, timeAgo: String, severity: Severity) {
    val containerColor = if (severity == Severity.HIGH) Color(0xFFC62828) else Color(0xFFF57C00)
    
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = containerColor),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Warning, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = message,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Reported $timeAgo",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
fun IncidentReportGrid(onReportSelected: (String) -> Unit) {
    // Structured data for Kenyan-specific reports
    val items: List<Triple<String, Color, ImageVector>> = listOf(
        Triple("Accident", Color.Red, Icons.Default.Warning),
        Triple("Heavy Traffic", Color(0xFFFF9800), Icons.Default.Info),
        Triple("Police Check", Color.Blue, Icons.Default.Notifications),
        Triple("Pothole", Color(0xFF795548), Icons.Default.Build),
        Triple("Stalled Bus", Color.Gray, Icons.Default.Info),
        Triple("Roadwork", Color(0xFFFBC02D), Icons.Default.Build)
    )

    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp).fillMaxWidth()) {
        Text(
            text = "Report Incident",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            color = Color.Black
        )
        Text(
            text = "Help other drivers by sharing what you see",
            color = Color.Gray,
            fontSize = 13.sp
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.height(220.dp)
        ) {
            items(items) { item ->
                val (label, color, icon) = item
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { onReportSelected(label) }
                ) {
                    Surface(
                        modifier = Modifier.size(56.dp),
                        shape = CircleShape,
                        color = color.copy(alpha = 0.12f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = icon,
                                contentDescription = label,
                                tint = color,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }
                    Text(
                        text = label, 
                        fontSize = 11.sp, 
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 8.dp),
                        maxLines = 1
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun BottomPanel(
    modifier: Modifier = Modifier,
    isTripStarted: Boolean,
    onTripToggle: () -> Unit
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(8.dp).background(Color(0xFF4CAF50), CircleShape))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "ETA: 25 min",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black
                        )
                    }
                    Text(
                        text = "12.4 km • via Thika Superhighway",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
                
                Button(
                    onClick = onTripToggle,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isTripStarted) Color(0xFFC62828) else Color(0xFF2E7D32)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(48.dp)
                ) {
                    Text(
                        text = if (isTripStarted) "END TRIP" else "START TRIP",
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomescreenPreview() {
    TransafeTheme {
        Homescreen()
    }
}
