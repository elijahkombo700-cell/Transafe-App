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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elijah.transafe.R
import com.elijah.transafe.ui.theme.TransafeTheme
import com.elijah.transafe.ui.theme.OrangeMain
import com.elijah.transafe.ui.theme.AlertRed
import com.elijah.transafe.ui.theme.SuccessGreen

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
    var isInsideBlackspot by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var showReportSheet by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        // 1. Map View (Placeholder with subtle overlay)
        MapPlaceholder(modifier = Modifier.fillMaxSize())

        // Safety "Blackspot" Detection Glow
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
                            listOf(AlertRed.copy(alpha = 0.3f), Color.Transparent, Color.Transparent)
                        )
                    )
            )
        }

        // 2. Top Navigation & Alerts
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SearchBar(
                onProfileClick = { navController?.navigate(ROUTE_PROFILE) },
                onDashboardClick = { navController?.navigate(ROUTE_DASHBOARD) }
            )

            SafetyHubButton(onClick = { navController?.navigate(ROUTE_DASHBOARD) })
            
            AlertBanner(
                message = if (isInsideBlackspot) "DANGER: Entering Accident Blackspot" else "Heavy traffic reported at Museum Hill",
                timeAgo = if (isInsideBlackspot) "Active Now" else "3 mins ago",
                severity = if (isInsideBlackspot) Severity.HIGH else Severity.MEDIUM
            )
        }

        // 3. Floating Action Buttons
        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SmallFloatingActionButton(
                onClick = { isInsideBlackspot = !isInsideBlackspot },
                containerColor = if (isInsideBlackspot) AlertRed else MaterialTheme.colorScheme.surface,
                contentColor = if (isInsideBlackspot) Color.White else MaterialTheme.colorScheme.onSurface,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(4.dp)
            ) {
                Icon(
                    imageVector = if (isInsideBlackspot) Icons.Default.Warning else Icons.Default.GpsFixed, 
                    contentDescription = "Toggle Detection"
                )
            }

            FloatingActionButton(
                onClick = { showReportSheet = true },
                containerColor = AlertRed,
                contentColor = Color.White,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Report", modifier = Modifier.size(32.dp))
            }
        }

        // 4. Trip Bottom Panel
        BottomPanel(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
            isTripStarted = false,
            onTripToggle = { 
                navController?.navigate(ROUTE_TRIP_ACTIVE)
            }
        )

        // 5. Report Bottom Sheet
        if (showReportSheet) {
            ModalBottomSheet(
                onDismissRequest = { showReportSheet = false },
                sheetState = sheetState,
                containerColor = MaterialTheme.colorScheme.surface,
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
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.traffic),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.4f
        )
        
        // Navigation route visual
        Box(
            modifier = Modifier
                .width(8.dp)
                .fillMaxHeight(0.6f)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), shape = RoundedCornerShape(4.dp))
                .align(Alignment.Center)
        )

        // Location marker
        Surface(
            modifier = Modifier.size(36.dp).align(Alignment.Center),
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Box(modifier = Modifier.size(20.dp).background(MaterialTheme.colorScheme.primary, CircleShape))
                Box(modifier = Modifier.size(12.dp).background(Color.White, CircleShape))
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
        modifier = modifier.fillMaxWidth().height(60.dp),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp).fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onProfileClick) {
                Icon(
                    Icons.Default.AccountCircle, 
                    contentDescription = "Profile", 
                    tint = OrangeMain,
                    modifier = Modifier.size(32.dp)
                )
            }
            Text(
                text = "Search destination...",
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                fontSize = 16.sp
            )
            IconButton(onClick = onDashboardClick) {
                Icon(Icons.Default.Shield, contentDescription = "Safety", tint = OrangeMain)
            }
            VerticalDivider(modifier = Modifier.height(24.dp).padding(horizontal = 8.dp))
            IconButton(onClick = { }) {
                Icon(Icons.Default.Mic, contentDescription = "Voice", tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun SafetyHubButton(onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = OrangeMain.copy(alpha = 0.15f),
        border = androidx.compose.foundation.BorderStroke(1.dp, OrangeMain.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.AutoGraph, contentDescription = null, tint = OrangeMain)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Safety & Education Hub",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = OrangeMain
                )
                Text(
                    text = "Daily tips & traffic awareness",
                    fontSize = 12.sp,
                    color = OrangeMain.copy(alpha = 0.8f)
                )
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = OrangeMain)
        }
    }
}

@Composable
fun AlertBanner(message: String, timeAgo: String, severity: Severity) {
    val containerColor = if (severity == Severity.HIGH) AlertRed else OrangeMain
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Warning, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = message,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Reported $timeAgo",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun IncidentReportGrid(onReportSelected: (String) -> Unit) {
    val items = listOf(
        Triple("Accident", AlertRed, Icons.Default.Warning),
        Triple("Traffic", OrangeMain, Icons.Default.Info),
        Triple("Police", Color(0xFF3B82F6), Icons.Default.Notifications),
        Triple("Hazard", Color(0xFF795548), Icons.Default.Build),
        Triple("Stalled", Color.Gray, Icons.Default.BusAlert),
        Triple("Roadwork", Color(0xFFFBC02D), Icons.Default.Construction)
    )

    Column(modifier = Modifier.padding(24.dp).fillMaxWidth()) {
        Text(
            text = "Report Incident",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Help others stay safe on the road",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.height(240.dp)
        ) {
            items(items) { (label, color, icon) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { onReportSelected(label) }
                ) {
                    Surface(
                        modifier = Modifier.size(64.dp),
                        shape = CircleShape,
                        color = color.copy(alpha = 0.15f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(icon, contentDescription = label, tint = color, modifier = Modifier.size(28.dp))
                        }
                    }
                    Text(
                        text = label, 
                        fontSize = 12.sp, 
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
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
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 12.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(10.dp).background(SuccessGreen, CircleShape))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "ETA: 25 min",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Text(
                        text = "12.4 km • via Thika Superhighway",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                Button(
                    onClick = onTripToggle,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isTripStarted) AlertRed else SuccessGreen
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.height(56.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    Text(
                        text = if (isTripStarted) "END TRIP" else "START TRIP",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        maxLines = 1,
                        softWrap = false
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
