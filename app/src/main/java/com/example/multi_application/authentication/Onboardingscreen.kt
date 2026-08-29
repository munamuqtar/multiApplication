package com.example.multi_application.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PrimaryBlue = Color(0xFF3366FF)

data class OnboardingPage(
    val title: String,
    val description: String
)

private val onboardingPages = listOf(
    OnboardingPage(
        title = "Everything You Need, One Marketplace",
        description = "Discover a curated selection of premium products across fashion, electronics, and home essentials. All in one seamless experience."
    ),
    OnboardingPage(
        title = "Shop Securely, Anytime",
        description = "Enjoy safe payments, buyer protection, and fast delivery tracking wherever you are."
    )
)

@Composable
fun OnboardingScreen(
    onSkip: () -> Unit,
    onFinish: () -> Unit
) {
    var pageIndex by remember { mutableStateOf(0) }
    val page = onboardingPages[pageIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Top bar: logo + Skip
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(PrimaryBlue, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "MultiMarket",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
            TextButton(onClick = onSkip) {
                Text("Skip", color = Color(0xFF8A8A8E))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Illustration placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(Color(0xFFDCE3F5), RoundedCornerShape(20.dp))
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = page.title,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1A1A),
            lineHeight = 32.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = page.description,
            fontSize = 14.sp,
            color = Color(0xFF8A8A8E),
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        // Page indicator dots
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            onboardingPages.indices.forEach { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .height(6.dp)
                        .width(if (index == pageIndex) 24.dp else 6.dp)
                        .background(
                            color = if (index == pageIndex) PrimaryBlue else Color(0xFFDADADA),
                            shape = RoundedCornerShape(50)
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (pageIndex < onboardingPages.lastIndex) {
                    pageIndex++
                } else {
                    onFinish()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
        ) {
            Text(
                text = if (pageIndex < onboardingPages.lastIndex) "Next Step  >" else "Get Started",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}