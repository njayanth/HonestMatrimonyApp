package com.example.honestmatrimony

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.honestmatrimony.ui.components.CaptchaSection
import com.example.honestmatrimony.ui.theme.HonestMatrimonyTheme
import com.example.honestmatrimony.utils.CaptchaUtils
import com.example.honestmatrimony.utils.DarkModePrefs
import com.example.honestmatrimony.utils.ValidationUtils

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val darkMode = DarkModePrefs.isDarkMode(this)

        setContent {
            HonestMatrimonyTheme(darkTheme = darkMode) {
                LoginScreen(
                    onCancel = { finish() }
                )
            }
        }
    }
}

@Composable
fun LoginScreen(onCancel: () -> Unit) {

    var userInput by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    var captcha by remember { mutableStateOf(CaptchaUtils.generateCaptcha()) }
    var captchaInput by remember { mutableStateOf("") }
    var captchaError by remember { mutableStateOf("") }

    Scaffold { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
        ) {

            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(24.dp))

            /* ---------- CREDENTIALS CARD ---------- */
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = "Credentials",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = userInput,
                        onValueChange = { userInput = it },
                        label = { Text("User ID / Email / Mobile") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            /* ---------- CAPTCHA CARD ---------- */
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = "Security Check",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.height(12.dp))

                    CaptchaSection(
                        captcha = captcha,
                        captchaInput = captchaInput,
                        onInputChange = { captchaInput = it },
                        onRefresh = {
                            captcha = CaptchaUtils.generateCaptcha()
                            captchaInput = ""
                            captchaError = ""
                        },
                        error = captchaError
                    )
                }
            }

            if (error.isNotEmpty()) {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    when {
                        !ValidationUtils.isValidLoginInput(userInput) ->
                            error = "Invalid User ID / Email / Mobile"

                        password.length < 6 ->
                            error = "Password too short"

                        !CaptchaUtils.verifyCaptcha(captcha, captchaInput) ->
                            captchaError = "Wrong captcha"

                        else -> {
                            error = ""
                            captchaError = ""
                            // TODO: Login API
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            Spacer(Modifier.height(8.dp))

            TextButton(
                onClick = onCancel,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Cancel")
            }
        }
    }
}
