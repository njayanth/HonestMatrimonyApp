package com.example.honestmatrimony

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

class SignUpActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            // ✅ READ DARK MODE INSIDE COMPOSE
            val isDarkMode = remember {
                mutableStateOf(DarkModePrefs.isDarkMode(this))
            }

            HonestMatrimonyTheme(darkTheme = isDarkMode.value) {
                SignUpScreen(
                    onCancel = { finish() }
                )
            }
        }
    }
}

@Composable
fun SignUpScreen(
    onCancel: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    var captcha by remember { mutableStateOf(CaptchaUtils.generateCaptcha()) }
    var captchaInput by remember { mutableStateOf("") }
    var captchaError by remember { mutableStateOf("") }

    Scaffold { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Create Account",
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
                        text = "Account Details",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(10.dp))

                    OutlinedTextField(
                        value = mobile,
                        onValueChange = { mobile = it },
                        label = { Text("Mobile Number") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(10.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(10.dp))

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirm Password") },
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
                        !ValidationUtils.isValidEmail(email) ->
                            error = "Invalid email"

                        !ValidationUtils.isValidMobile(mobile) ->
                            error = "Invalid mobile number"

                        password.length < 6 ->
                            error = "Password too short"

                        password != confirmPassword ->
                            error = "Passwords do not match"

                        !CaptchaUtils.verifyCaptcha(captcha, captchaInput) ->
                            captchaError = "Wrong captcha"

                        else -> {
                            error = ""
                            captchaError = ""
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create Account")
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
