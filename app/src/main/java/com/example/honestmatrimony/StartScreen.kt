package com.example.honestmatrimony

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.honestmatrimony.utils.DarkModePrefs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(
    onToggleDarkMode: (Boolean) -> Unit
) {
    val context = LocalContext.current

    var showInfoDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }
    var isDarkMode by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Honest Matrimony") },
                actions = {
                    IconButton(onClick = { showInfoDialog = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_info),
                            contentDescription = "Info"
                        )
                    }
                    IconButton(onClick = { showSettingsDialog = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_settings),
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                val logoId =
                    if (isDarkMode) R.drawable.hmlogob else R.drawable.hmlogow

                Image(
                    painter = painterResource(id = logoId),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .aspectRatio(1f)
                )

                Spacer(modifier = Modifier.height(48.dp))

                RoundedButton("Sign Up") {
                    context.startActivity(
                        Intent(context, SignUpActivity::class.java)
                    )
                }

                RoundedButton("Login") {
                    context.startActivity(
                        Intent(context, LoginActivity::class.java)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                OutlinedRoundedButton("Coming Soon")
                OutlinedRoundedButton("Coming Soon")
                OutlinedRoundedButton("Coming Soon")
            }
        }
    }

    /* -------- INFO DIALOG -------- */
    if (showInfoDialog) {
        AlertDialog(
            onDismissRequest = { showInfoDialog = false },
            title = { Text("About Honest Matrimony") },
            text = {
                Text(
                    "Version: 1.0.0\n" +
                            "© 2026 Honest Matrimony\n" +
                            "All rights reserved."
                )
            },
            confirmButton = {
                Button(onClick = { showInfoDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    /* -------- SETTINGS DIALOG -------- */
    if (showSettingsDialog) {
        AlertDialog(
            onDismissRequest = { showSettingsDialog = false },
            title = { Text("Settings") },
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Enable Dark Mode",
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = {
                            isDarkMode = it
                            DarkModePrefs.setDarkMode(context, it)   // ✅ THIS WAS MISSING
                            onToggleDarkMode(it)
                        }
                    )

                }
            },
            confirmButton = {
                Button(onClick = { showSettingsDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}

/* -------- BUTTONS -------- */

@Composable
fun RoundedButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(28.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(vertical = 6.dp)
    ) {
        Text(text = text, fontSize = 16.sp)
    }
}

@Composable
fun OutlinedRoundedButton(
    text: String
) {
    OutlinedButton(
        onClick = { },
        shape = RoundedCornerShape(28.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .padding(vertical = 4.dp)
    ) {
        Text(text = text, fontSize = 14.sp)
    }
}
