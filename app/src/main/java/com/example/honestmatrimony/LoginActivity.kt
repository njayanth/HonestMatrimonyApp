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
    var isCaptchaVerified by remember { mutableStateOf(false) }   // Step after captcha
    var isOtpSent by remember { mutableStateOf(false) }           // OTP field enabled
    var otp by remember { mutableStateOf("") }                    // OTP input field
    var showOtpDialog by remember { mutableStateOf(false) }       // Popup


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
                    Spacer(modifier = Modifier.height(12.dp))

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
                                    isCaptchaVerified = true
                                    isOtpSent = true       // enable OTP field
                                    showOtpDialog = true    // show popup
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Verify & Send OTP")
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = otp,
                        onValueChange = { otp = it },
                        label = { Text("OTP") },
                        enabled = isOtpSent,
                        modifier = Modifier.fillMaxWidth()
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

//            Button(
//                onClick = {
//                    when {
//                        !ValidationUtils.isValidLoginInput(userInput) ->
//                            error = "Invalid User ID / Email / Mobile"
//
//                        password.length < 6 ->
//                            error = "Password too short"
//
//                        !CaptchaUtils.verifyCaptcha(captcha, captchaInput) ->
//                            captchaError = "Wrong captcha"
//
//                        else -> {
//                            error = ""
//                            captchaError = ""
//                            // TODO: Login API
//                        }
//                    }
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Login")
//            }
            Button(
                onClick = {
                    if (otp.length < 4) {
                        error = "Invalid OTP"
                    } else {
                        error = ""
                        // TODO: final login API
                    }
                },
                enabled = isOtpSent,   // enabled only after OTP is sent
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
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
        if (showOtpDialog) {
            AlertDialog(
                onDismissRequest = { showOtpDialog = false },
                title = { Text("OTP Sent") },
                text = { Text("An OTP has been sent to your registered email and mobile number.") },
                confirmButton = {
                    Button(onClick = { showOtpDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}
