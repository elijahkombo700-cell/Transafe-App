package com.elijah.transafe.ui.theme.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.elijah.transafe.R
import com.elijah.transafe.data.UserViewModel
import com.elijah.transafe.ui.theme.NewBackground
import com.elijah.transafe.ui.theme.OrangeMain
import com.elijah.transafe.ui.theme.Slate100
import com.elijah.transafe.ui.theme.Slate500
import com.elijah.transafe.ui.theme.Slate700

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profilescreen(navController: NavHostController) {
    val context = LocalContext.current
    val userViewModel = remember { UserViewModel(navController, context) }
    
    // State for user details
    var name by remember { mutableStateOf("John Doe") }
    var plateNumber by remember { mutableStateOf("KDA 123A") }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    // Image picker launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri
    }

    Scaffold(
        containerColor = Slate100,
        topBar = {
            TopAppBar(
                title = { Text("Profile Settings", fontWeight = FontWeight.Bold, color = OrangeMain) },
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Profile Picture Section
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.size(140.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { launcher.launch("image/*") },
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 8.dp,
                    border = androidx.compose.foundation.BorderStroke(3.dp, OrangeMain.copy(alpha = 0.6f))
                ) {
                    if (profileImageUri != null) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = "Selected",
                                tint = Color(0xFF10B981),
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.profile),
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                
                SmallFloatingActionButton(
                    onClick = { launcher.launch("image/*") },
                    containerColor = OrangeMain,
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier.size(42.dp)
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "Change Photo", modifier = Modifier.size(20.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Personalize your profile", 
                style = MaterialTheme.typography.bodyMedium,
                color = Slate500
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Input Fields Card
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "Account Details",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Slate700
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Full Name") },
                        placeholder = { Text("Enter your name") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = OrangeMain) },
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = OrangeMain,
                            focusedLabelColor = OrangeMain,
                            unfocusedBorderColor = Slate100,
                            unfocusedLabelColor = Slate500,
                            focusedContainerColor = Slate100.copy(alpha = 0.3f),
                            unfocusedContainerColor = Slate100.copy(alpha = 0.3f)
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = plateNumber,
                        onValueChange = { plateNumber = it },
                        label = { Text("Vehicle Plate Number") },
                        placeholder = { Text("e.g. KDA 123A") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null, tint = OrangeMain) },
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = OrangeMain,
                            focusedLabelColor = OrangeMain,
                            unfocusedBorderColor = Slate100,
                            unfocusedLabelColor = Slate500,
                            focusedContainerColor = Slate100.copy(alpha = 0.3f),
                            unfocusedContainerColor = Slate100.copy(alpha = 0.3f)
                        ),
                        singleLine = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(56.dp))

            // Save Button
            Button(
                onClick = { 
                    userViewModel.saveUserProfile(name, plateNumber, profileImageUri)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OrangeMain),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text(text = "SAVE CHANGES", fontWeight = FontWeight.Bold, fontSize = 16.sp, letterSpacing = 1.25.sp)
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfilescreenPreview() {
    val navController = rememberNavController()
    Profilescreen(navController)
}
