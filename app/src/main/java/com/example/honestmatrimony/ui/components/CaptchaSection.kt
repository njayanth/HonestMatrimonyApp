package com.example.honestmatrimony.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.honestmatrimony.utils.Captcha

@Composable
fun CaptchaSection(
    captcha: Captcha,
    captchaInput: String,
    onInputChange: (String) -> Unit,
    onRefresh: () -> Unit,
    error: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(captcha.question, fontSize = 18.sp, modifier = Modifier.weight(1f))
        IconButton(onClick = onRefresh) {
            Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
        }
    }

    OutlinedTextField(
        value = captchaInput,
        onValueChange = onInputChange,
        label = { Text("Captcha") },
        modifier = Modifier.fillMaxWidth()
    )

    if (error.isNotEmpty()) {
        Text(error, color = Color.Red, fontSize = 12.sp)
    }
}
