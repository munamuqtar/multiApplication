package com.example.multi_application.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private val PrimaryBlue = Color(0xFF3366FF)
private val BackgroundGray = Color(0xFFF5F5F7)

@Composable
fun SplashScreen(
    onTimeout: () -> Unit
) {
    // Auto-navigate after a delay, mimicking a splash screen
    LaunchedEffect(Unit) {
        delay(2000)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Illustration placeholder (replace with Image(painterResource(...)))
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color(0xFFDCE3F5), RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Logo badge
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(PrimaryBlue, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.ShoppingBag,
                    contentDescription = "MultiMarket logo",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "MultiMarket",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Buy  •  Sell  •  Discover",
                fontSize = 13.sp,
                color = Color(0xFF8A8A8E)
            )

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "PREMIUM MARKETPLACE",
                fontSize = 10.sp,
                letterSpacing = 1.sp,
                color = Color(0xFFB0B0B5),
                textAlign = TextAlign.Center
            )
        }
    }
}