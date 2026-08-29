package com.example.multi_application.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
private val InfoBoxBg = Color(0xFFF1F3F7)
private val GradientStart = Color(0xFF6A5AE0)
private val GradientEnd = Color(0xFF3366FF)

// ---------------------------------------------------------------------------
// Screen
// ---------------------------------------------------------------------------
@Composable
fun SellerApplicationScreen(
    initialFullName: String = "Ahmed Ali",
    initialBusinessEmail: String = "ahmed@example.com",
    initialMobileNumber: String = "+971 50 123 4567",
    currentStep: Int = 1,
    totalSteps: Int = 3,
    onBackClick: () -> Unit = {},
    onBellClick: () -> Unit = {},
    onContinueClick: (fullName: String, businessEmail: String, mobileNumber: String) -> Unit = { _, _, _ -> },
    onProfileNavClick: () -> Unit = {},
    onOrdersNavClick: () -> Unit = {},
    onWishlistNavClick: () -> Unit = {}
) {
    var fullName by remember { mutableStateOf(initialFullName) }
    var businessEmail by remember { mutableStateOf(initialBusinessEmail) }
    var mobileNumber by remember { mutableStateOf(initialMobileNumber) }

    Scaffold(
        containerColor = BackgroundGray,
        bottomBar = {
            ShopFlowBottomBar(
                selectedTab = ShopFlowTab.SELL,
                onProfileClick = onProfileNavClick,
                onOrdersClick = onOrdersNavClick,
                onWishlistClick = onWishlistNavClick,
                onSellClick = {}
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(BackgroundGray),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item { BackTopBar(title = "Seller Registration", onBackClick = onBackClick, onBellClick = onBellClick) }

            item { StepProgress(currentStep = currentStep, totalSteps = totalSteps) }

            item { JourneyBanner() }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp).padding(top = 4.dp, bottom = 14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Person, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Personal Information", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PrimaryBlue)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "We need a few details about you to verify your identity and set up your account.",
                        fontSize = 12.sp,
                        color = HintGray,
                        lineHeight = 16.sp
                    )
                }
            }

            item {
                FormField(
                    label = "Full Legal Name",
                    value = fullName,
                    onValueChange = { fullName = it },
                    leadingIcon = Icons.Filled.Person
                )
            }
            item {
                FormField(
                    label = "Business Email",
                    value = businessEmail,
                    onValueChange = { businessEmail = it },
                    leadingIcon = Icons.Filled.Email
                )
            }
            item {
                FormField(
                    label = "Mobile Number",
                    value = mobileNumber,
                    onValueChange = { mobileNumber = it },
                    leadingIcon = Icons.Filled.Phone
                )
            }

            item { SecurityNote() }

            item {
                ContinueButton(
                    label = "Continue to Business Info",
                    onClick = { onContinueClick(fullName, businessEmail, mobileNumber) }
                )
            }
        }
    }
}

@Composable
private fun StepProgress(currentStep: Int, totalSteps: Int) {
    val progress = currentStep.toFloat() / totalSteps.toFloat()
    Column(modifier = Modifier.padding(horizontal = 20.dp).padding(bottom = 14.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "STEP $currentStep OF $totalSteps",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = HintGray,
                letterSpacing = 0.4.sp
            )
            Text(
                "${(progress * 100).toInt()}% Complete",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryBlue
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(DividerGray)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(3.dp))
                    .background(PrimaryBlue)
            )
        }
    }
}

@Composable
private fun JourneyBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 18.dp)
            .height(110.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(Brush.linearGradient(colors = listOf(GradientStart, GradientEnd)))
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.BottomStart)) {
            Text("Start Your Journey", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Join our premium community of over 5,000 verified vendors.",
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.85f),
                lineHeight = 14.sp
            )
        }
    }
}

@Composable
private fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 14.dp)
    ) {
        Text(label, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = TextDark)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = { Icon(leadingIcon, contentDescription = null, tint = HintGray, modifier = Modifier.size(18.dp)) },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = CardWhite,
                focusedContainerColor = CardWhite,
                unfocusedBorderColor = DividerGray,
                focusedBorderColor = PrimaryBlue
            )
        )
    }
}

@Composable
private fun SecurityNote() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 18.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(InfoBoxBg)
            .padding(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(Icons.Filled.Info, contentDescription = null, tint = HintGray, modifier = Modifier.size(15.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            "Your data is protected by our professional security protocols. We never share personal info with buyers.",
            fontSize = 11.sp,
            color = HintGray,
            lineHeight = 15.sp
        )
    }
}

@Composable
private fun ContinueButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(50.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
    ) {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        Spacer(modifier = Modifier.width(8.dp))
        Icon(Icons.Filled.ArrowForward, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
    }
}