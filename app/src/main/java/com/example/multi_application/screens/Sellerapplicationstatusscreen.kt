package com.example.multi_application.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
private val InfoBannerBg = Color(0xFFEAF0FF)
private val PendingGray = Color(0xFFD5D5DA)
private val ShieldGreen = Color(0xFF2ECC71)
private val ShieldGold = Color(0xFFE6B325)

// ---------------------------------------------------------------------------
// Models
// ---------------------------------------------------------------------------
enum class TimelineStepState { DONE, ACTIVE, PENDING }

data class ApplicationTimelineStep(
    val title: String,
    val description: String,
    val date: String? = null,
    val state: TimelineStepState
)

data class NextStepItem(
    val icon: ImageVector,
    val title: String,
    val subtitle: String
)

private val sampleTimeline = listOf(
    ApplicationTimelineStep(
        title = "Form Submitted",
        description = "Basic information and business details received.",
        date = "Oct 12",
        state = TimelineStepState.DONE
    ),
    ApplicationTimelineStep(
        title = "Documents Uploaded",
        description = "Tax ID and ID verification documents provided.",
        date = "Oct 12",
        state = TimelineStepState.DONE
    ),
    ApplicationTimelineStep(
        title = "Security Verification",
        description = "Reviewing business credentials and background check.",
        state = TimelineStepState.ACTIVE
    ),
    ApplicationTimelineStep(
        title = "Final Approval",
        description = "Store setup and initial inventory listing access.",
        state = TimelineStepState.PENDING
    )
)

private val sampleNextSteps = listOf(
    NextStepItem(Icons.Filled.MenuBook, "Seller Handbook", "Learn how to list products"),
    NextStepItem(Icons.Filled.Storefront, "Vendor Profile", "Setup your public store identity")
)

// ---------------------------------------------------------------------------
// Screen
// ---------------------------------------------------------------------------
@Composable
fun SellerApplicationStatusScreen(
    applicationId: String = "#MM-9284",
    timeline: List<ApplicationTimelineStep> = sampleTimeline,
    nextSteps: List<NextStepItem> = sampleNextSteps,
    onBackClick: () -> Unit = {},
    onBellClick: () -> Unit = {},
    onSellerHandbookClick: () -> Unit = {},
    onVendorProfileClick: () -> Unit = {},
    onContactSupportClick: () -> Unit = {},
    onFaqClick: () -> Unit = {}
) {
    Scaffold(containerColor = BackgroundGray) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(BackgroundGray),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item { BackTopBar(title = "Application Status", onBackClick = onBackClick, onBellClick = onBellClick) }

            item { ReviewHeroSection() }

            item { UnderReviewBanner() }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(top = 22.dp, bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Track Progress", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Text("App ID: $applicationId", fontSize = 11.sp, color = HintGray)
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    timeline.forEachIndexed { index, step ->
                        TimelineRow(step = step, isLast = index == timeline.lastIndex)
                    }
                }
            }

            item {
                Text(
                    "What's Next?",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 12.dp, bottom = 10.dp)
                )
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    NextStepRow(nextSteps[0], onClick = onSellerHandbookClick)
                    Spacer(modifier = Modifier.height(10.dp))
                    NextStepRow(nextSteps[1], onClick = onVendorProfileClick)
                }
            }

            item { SupportFooter(onContactSupportClick, onFaqClick) }
        }
    }
}

@Composable
private fun ReviewHeroSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(colors = listOf(ShieldGreen, ShieldGold))),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Shield, contentDescription = null, tint = Color.White.copy(alpha = 0.25f), modifier = Modifier.size(46.dp))
                Icon(Icons.Filled.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        Text("Review in Progress", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextDark)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "We've received your business details and are currently performing our checks.",
            fontSize = 12.sp,
            color = HintGray,
            textAlign = TextAlign.Center,
            lineHeight = 16.sp,
            modifier = Modifier.padding(horizontal = 40.dp)
        )
    }
}

@Composable
private fun UnderReviewBanner() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(InfoBannerBg)
            .padding(14.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(Icons.Filled.Schedule, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text("Under Review", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = PrimaryBlue)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                "Our team is currently verifying your business credentials. This usually takes 1-2 business days.",
                fontSize = 11.sp,
                color = PrimaryBlue.copy(alpha = 0.85f),
                lineHeight = 15.sp
            )
        }
    }
}

@Composable
private fun TimelineRow(step: ApplicationTimelineStep, isLast: Boolean) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TimelineDot(step.state)
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(38.dp)
                        .background(if (step.state == TimelineStepState.DONE) PrimaryBlue else DividerGray)
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.padding(bottom = 18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    step.title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = when (step.state) {
                        TimelineStepState.PENDING -> HintGray
                        TimelineStepState.ACTIVE -> PrimaryBlue
                        TimelineStepState.DONE -> TextDark
                    }
                )
                if (step.date != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(step.date, fontSize = 10.sp, color = HintGray)
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                step.description,
                fontSize = 11.sp,
                color = HintGray,
                lineHeight = 15.sp
            )
        }
    }
}

@Composable
private fun TimelineDot(state: TimelineStepState) {
    when (state) {
        TimelineStepState.DONE -> Box(
            modifier = Modifier
                .size(22.dp)
                .clip(CircleShape)
                .background(PrimaryBlue),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
        }

        TimelineStepState.ACTIVE -> Box(
            modifier = Modifier
                .size(22.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(2.dp, PrimaryBlue, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(9.dp)
                    .clip(CircleShape)
                    .background(PrimaryBlue)
            )
        }

        TimelineStepState.PENDING -> Box(
            modifier = Modifier
                .size(22.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(2.dp, PendingGray, CircleShape)
        )
    }
}

@Composable
private fun NextStepRow(item: NextStepItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(CardWhite)
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFE9EEFF)),
            contentAlignment = Alignment.Center
        ) {
            Icon(item.icon, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.size(16.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(item.title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
            Text(item.subtitle, fontSize = 11.sp, color = HintGray)
        }
        Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = HintGray, modifier = Modifier.size(16.dp))
    }
}

@Composable
private fun SupportFooter(onContactSupportClick: () -> Unit, onFaqClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.clickable(onClick = onContactSupportClick),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.SupportAgent, contentDescription = null, tint = HintGray, modifier = Modifier.size(13.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Contact Support", fontSize = 11.sp, color = HintGray)
        }
        Spacer(modifier = Modifier.width(20.dp))
        Row(
            modifier = Modifier.clickable(onClick = onFaqClick),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.HelpOutline, contentDescription = null, tint = HintGray, modifier = Modifier.size(13.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("FAQs", fontSize = 11.sp, color = HintGray)
        }
    }
}