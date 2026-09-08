package com.example.multi_application.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ---------- Palette (green) ----------
private val PrimaryGreen = Color(0xFF16A34A)
private val ScreenBg = Color(0xFFF3F3F7)
private val FieldGray = Color.White
private val HintGray = Color(0xFFAEAEB8)
private val TitleDark = Color(0xFF1C1C28)

@Composable
fun VerificationScreen(
    onBack: () -> Unit,
    onContinue: (code: String) -> Unit,
    onResend: () -> Unit
) {
    // One state entry per digit box
    val digits = remember { mutableStateOf(listOf("", "", "", "")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBg)
            .padding(horizontal = 28.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        IconButton(onClick = onBack) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = TitleDark)
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Verification",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TitleDark,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "We sent you a text message\nwith a 4-digit code",
            fontSize = 13.sp,
            color = HintGray,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(36.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            digits.value.forEachIndexed { index, digit ->
                OtpBox(
                    value = digit,
                    onValueChange = { newValue ->
                        if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                            digits.value = digits.value.toMutableList().also { it[index] = newValue }
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { onContinue(digits.value.joinToString("")) },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
        ) {
            Text("CONTINUE  →", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Didn't receive a code? ",
                fontSize = 13.sp,
                color = HintGray
            )
            Text(
                text = "RESEND",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryGreen,
                modifier = Modifier.clickable(onClick = onResend)
            )
        }
    }
}

@Composable
private fun OtpBox(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = androidx.compose.ui.text.TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = TitleDark
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = FieldGray,
            focusedContainerColor = FieldGray,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = PrimaryGreen,
            cursorColor = PrimaryGreen
        ),
        modifier = Modifier.size(56.dp)
    )
}