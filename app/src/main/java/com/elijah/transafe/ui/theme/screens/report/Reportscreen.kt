package com.elijah.transafe.ui.theme.screens.report

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.elijah.transafe.data.ReportViewModel
import com.elijah.transafe.ui.theme.AlertRed
import com.elijah.transafe.ui.theme.InfoBlue
import com.elijah.transafe.ui.theme.NewBackground
import com.elijah.transafe.ui.theme.NewRed
import com.elijah.transafe.ui.theme.OrangeMain
import com.elijah.transafe.ui.theme.Slate100
import com.elijah.transafe.ui.theme.Slate500
import com.elijah.transafe.ui.theme.Slate700

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Reportscreen(navController: NavHostController) {
    val context = LocalContext.current
    val reportViewModel = remember { ReportViewModel(navController, context) }
    var selectedIncident by remember { mutableStateOf<String?>(null) }
    var reportDescription by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Slate100,
        topBar = {
            TopAppBar(
                title = { Text("Report Incident", fontWeight = FontWeight.Bold, color = OrangeMain) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Select Incident Type",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Slate700
            )
            Text(
                text = "Help the community by reporting what you see",
                style = MaterialTheme.typography.bodyMedium,
                color = Slate500
            )
            
            Spacer(modifier = Modifier.height(24.dp))

            val items = listOf(
                Triple("Accident", AlertRed, Icons.Default.Warning),
                Triple("Traffic", OrangeMain, Icons.Default.Traffic),
                Triple("Police", InfoBlue, Icons.Default.LocalPolice),
                Triple("Hazard", Color(0xFF795548), Icons.Default.Dangerous),
                Triple("Stalled", Color.Gray, Icons.Default.BusAlert),
                Triple("Roadwork", Color(0xFFFBC02D), Icons.Default.Construction)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(items) { item ->
                    val (label, color, icon) = item
                    val isSelected = selectedIncident == label
                    
                    Surface(
                        onClick = { selectedIncident = label },
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSelected) color.copy(alpha = 0.12f) else Color.White,
                        border = if (isSelected) BorderStroke(2.dp, color) else null,
                        shadowElevation = if (isSelected) 0.dp else 2.dp,
                        modifier = Modifier.height(100.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(8.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                icon, 
                                contentDescription = label, 
                                tint = if (isSelected) color else OrangeMain,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                label, 
                                fontSize = 13.sp, 
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) color else Slate700
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Details & Description", 
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                color = Slate700
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            OutlinedTextField(
                value = reportDescription,
                onValueChange = { reportDescription = it },
                modifier = Modifier.fillMaxWidth().height(140.dp),
                placeholder = { Text("Example: Two cars collided at the roundabout. Emergency services needed.") },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = OrangeMain,
                    unfocusedBorderColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedLabelColor = OrangeMain,
                    unfocusedLabelColor = Slate500
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { 
                    reportViewModel.submitReport(
                        title = selectedIncident ?: "Incident",
                        description = reportDescription,
                        location = "Current Location",
                        imageUri = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp),
                enabled = selectedIncident != null,
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NewRed,
                    contentColor = OrangeMain
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text("SUBMIT REPORT", fontWeight = FontWeight.Bold, letterSpacing = 1.25.sp, fontSize = 16.sp)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ReportscreenPreview() {
    val navController = rememberNavController()
    Reportscreen(navController)
}
