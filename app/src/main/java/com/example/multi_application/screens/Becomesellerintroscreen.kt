package com.example.multi_application.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ---------------------------------------------------------------------------
// Design tokens (kept identical to the rest of the app)
// ---------------------------------------------------------------------------
private val PrimaryBlue = Color(0xFF3366FF)
private val BackgroundGray = Color(0xFFF7F7F9)
private val CardWhite = Color.White
private val HintGray = Color(0xFF9A9AA0)
private val TextDark = Color(0xFF1A1A1A)
private val DividerGray = Color(0xFFE7E7EA)
private val NavyDark = Color(0xFF0F1E3D)
private val GreenBadgeBg = Color(0xFFE1F6E7)
private val GreenBadgeText = Color(0xFF1E9E4A)
private val HighlightPurple = Color(0xFF7C6FF0)
private val GradientStart = Color(0xFF6A5AE0)
private val GradientEnd = Color(0xFF3366FF)

// ---------------------------------------------------------------------------
// Models
// ---------------------------------------------------------------------------
private data class SellerFeature(
    val icon: ImageVector,
    val iconBg: Color,
    val title: String,
    val description: String,
    val badge: String? = null,
    val highlighted: Boolean = false
)

private val sellerFeatures = listOf(
    SellerFeature(
        icon = Icons.Filled.Bolt,
        iconBg = Color(0xFFE9EEFF),
        title = "Quick Onboarding",
        description = "Get your store up and running in less than 24 hours with our streamlined verification process.",
        badge = "Instant"
    ),
    SellerFeature(
        icon = Icons.Filled.QueryStats,
        iconBg = Color(0xFFF1EEFE),
        title = "Advanced Analytics",
        description = "Gain deep insights into customer behavior and sales performance with our premium dashboard.",
        highlighted = true
    ),
    SellerFeature(
        icon = Icons.Filled.Shield,
        iconBg = Color(0xFFE9EEFF),
        title = "Secure Payments",
        description = "Rest easy with end-to-end encrypted transactions and weekly payouts directly to your bank.",
        badge = "Trusted"
    )
)

private val howItWorksSteps = listOf(
    "Register your business profile",
    "List your premium products",
    "Start receiving orders and getting paid"
)

// ---------------------------------------------------------------------------
// Screen
// ---------------------------------------------------------------------------
@Composable
fun BecomeSellerIntroScreen(
    onBackClick: () -> Unit = {},
    onBellClick: () -> Unit = {},
    onGetStartedClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = BackgroundGray,
        bottomBar = { GetStartedBar(onGetStartedClick) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(BackgroundGray),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item { BackTopBar(title = "Become a Seller", onBackClick = onBackClick, onBellClick = onBellClick) }

            item { HeroImageBanner() }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Groups, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "PARTNER PROGRAM",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryBlue,
                            letterSpacing = 0.6.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = buildAnnotatedString {
                            append("Empower Your Business with ")
                            withStyle(SpanStyle(color = PrimaryBlue)) { append("MultiMarket") }
                        },
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark,
                        lineHeight = 28.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Join thousands of successful vendors who have transformed their passion into a thriving global enterprise.",
                        fontSize = 13.sp,
                        color = HintGray,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                }
            }

            items(sellerFeatures) { feature ->
                FeatureCard(feature)
            }

            item {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 8.dp)
                ) {
                    Text("How it works", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Spacer(modifier = Modifier.height(10.dp))
                    howItWorksSteps.forEachIndexed { index, step ->
                        HowItWorksRow(number = index + 1, text = step)
                        if (index != howItWorksSteps.lastIndex) {
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HeroImageBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 18.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.linearGradient(colors = listOf(Color(0xFFDCC7A6), Color(0xFFB89A78)))
            )
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HeroChip(icon = Icons.Filled.Public, text = "GLOBAL REACH")
            HeroChip(icon = Icons.Filled.TrendingUp, text = "FAST GROWTH")
        }
    }
}

@Composable
private fun HeroChip(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Black.copy(alpha = 0.45f))
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(11.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}

@Composable
private fun FeatureCard(feature: SellerFeature) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 12.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardWhite)
            .then(
                if (feature.highlighted) {
                    Modifier.border(1.5.dp, HighlightPurple, RoundedCornerShape(16.dp))
                } else {
                    Modifier
                }
            )
            .padding(14.dp)
    ) {
        if (feature.badge != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(20.dp))
                    .background(GreenBadgeBg)
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(feature.badge, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GreenBadgeText)
            }
        }
        Row(verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(feature.iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(feature.icon, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f).padding(end = if (feature.badge != null) 56.dp else 0.dp)) {
                Text(feature.title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
                Spacer(modifier = Modifier.height(3.dp))
                Text(feature.description, fontSize = 12.sp, color = HintGray, lineHeight = 16.sp)
            }
        }
    }
}

@Composable
private fun HowItWorksRow(number: Int, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(Color(0xFFE9EEFF)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                number.toString().padStart(2, '0'),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryBlue
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(text, fontSize = 13.sp, color = TextDark)
    }
}

@Composable
private fun GetStartedBar(onGetStartedClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardWhite)
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Brush.horizontalGradient(colors = listOf(GradientStart, GradientEnd)))
                .clickable(onClick = onGetStartedClick),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Get Started", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Filled.ArrowForward, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
            }
        }
    }
}