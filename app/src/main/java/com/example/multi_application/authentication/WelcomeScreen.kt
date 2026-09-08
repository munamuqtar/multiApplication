package com.example.multi_application.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.multi_application.R

// ---------- Shared palette (green) ----------
private val PrimaryGreen = Color(0xFF16A34A)
private val LightGreenBg = Color(0xFFE9F8EF)
private val DotInactive = Color(0xFFD7EFDF)
private val TitleDark = Color(0xFF1A1A2E)
private val SubtitleGray = Color(0xFF8A8A9A)

/**
 * Public entry point used by the NavHost:
 *   composable(Routes.ONBOARDING) {
 *       OnboardingScreen(onSkip = { ... }, onFinish = { ... })
 *   }
 *
 * Internally walks the user through 4 screens:
 *   1) OnboardingHeroScreen  — full green welcome screen ("Get Started")
 *   2) OnboardingScreenOne   — "unlock the future of Multi-Store Shopping"
 *   3) OnboardingScreenTwo   — "Uncover Exciting Deals from nearby stores"
 *   4) OnboardingScreenThree — "Shop from Different Stores in one place" (Let's Go -> onFinish)
 *
 * "skip" on any of the info pages calls onSkip() immediately.
 */
@Composable
fun OnboardingScreen(
    onSkip: () -> Unit,
    onFinish: () -> Unit
) {
    var step by remember { mutableStateOf(0) } // 0 = hero, 1..3 = info pages

    when (step) {
        0 -> OnboardingHeroScreen(
            onGetStarted = { step = 1 }
        )

        1 -> OnboardingScreenOne(
            onSkip = onSkip,
            onNext = { step = 2 }
        )

        2 -> OnboardingScreenTwo(
            onSkip = onSkip,
            onNext = { step = 3 }
        )

        else -> OnboardingScreenThree(
            onSkip = onSkip,
            onFinish = onFinish
        )
    }
}

// =====================================================================================
// SCREEN 1 — Hero / welcome (full green screen)
// =====================================================================================
@Composable
private fun OnboardingHeroScreen(
    onGetStarted: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryGreen)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // Top heading — left aligned
            Text(
                text = "Multi\nShopping\nApp",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                lineHeight = 26.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            // Centered logo + welcome copy
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.Transparent)
                        .border(
                            width = 2.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(20.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingBag,
                        contentDescription = "Multi shopping app logo",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Welcome To",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Multi Shopping App Everything you need all in one place.",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.85f),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        // Bottom white dome — large rounded sheet, button anchored near its bottom edge
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(260.dp)
                .clip(RoundedCornerShape(topStart = 200.dp, topEnd = 200.dp))
                .background(Color.White)
        ) {
            Button(
                onClick = onGetStarted,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 56.dp)
                    .fillMaxWidth(0.7f)
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
            ) {
                Text(
                    text = "Get Started",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
    }
}

// =====================================================================================
// SCREEN 2 — "unlock the future of Multi-Store Shopping"
// =====================================================================================
@Composable
private fun OnboardingScreenOne(
    onSkip: () -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onSkip) {
                Text("skip", color = SubtitleGray, fontSize = 13.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Illustration — smaller, centered square, not full width
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_shopping_logo),
                contentDescription = "Illustration of multiple connected stores",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(400.dp)
            )
        }

        // Pushes the title/description further down, away from the image
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "unlock the future of",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = TitleDark,
            lineHeight = 28.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Multi-Store System",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryGreen,
            lineHeight = 28.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Explore a wide range of products from fashion and technology to furniture, groceries, and more. Find everything you need in one place.",
            fontSize = 17.sp,
            color = SubtitleGray,
            lineHeight = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        // Bottom row pulled up — smaller bottom padding
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(7.dp)
                        .background(PrimaryGreen, CircleShape)
                )
                Box(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(7.dp)
                        .background(DotInactive, CircleShape)
                )
                Box(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(7.dp)
                        .background(DotInactive, CircleShape)
                )
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(PrimaryGreen, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                TextButton(onClick = onNext) {
                    Text(">", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// =====================================================================================
// SCREEN 3 — "Uncover Exciting Deals from nearby stores"
// =====================================================================================
@Composable
private fun OnboardingScreenTwo(
    onSkip: () -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onSkip) {
                Text("skip", color = SubtitleGray, fontSize = 13.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Illustration — smaller, centered square, not full width
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_store_pin),
                contentDescription = "Map of nearby stores",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(400.dp)
            )
        }

        // Pushes the title/description further down, away from the image
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Shop Easily",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryGreen,
            lineHeight = 28.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Shop With Multi Application",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = TitleDark,
            lineHeight = 28.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Browse hundreds of local shops and marketplaces, follow your " +
                    "favorites, and get notified the moment a new deal drops near you.",
            fontSize = 17.sp,
            color = SubtitleGray,
            lineHeight = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        // Bottom row pulled up — smaller bottom padding
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(7.dp)
                        .background(DotInactive, CircleShape)
                )
                Box(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(7.dp)
                        .background(PrimaryGreen, CircleShape)
                )
                Box(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(7.dp)
                        .background(DotInactive, CircleShape)
                )
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(PrimaryGreen, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                TextButton(onClick = onNext) {
                    Text(">", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// =====================================================================================
// SCREEN 4 — "Shop from Different Stores in one place" (final screen, Let's Go CTA)
// =====================================================================================
@Composable
private fun OnboardingScreenThree(
    onSkip: () -> Unit,
    onFinish: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onSkip) {
                Text("skip", color = SubtitleGray, fontSize = 13.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Illustration — smaller, centered square, not full width
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_store_pin1),
                contentDescription = "Multiple stores in one place",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(400.dp)
            )
        }

        // Pushes the title/description further down, away from the image
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Buy, Sell & Grow",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryGreen,
            lineHeight = 28.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Find great products or list your own items for sale. Connect with buyers and sellers and make shopping more rewarding.",
            fontSize = 17.sp,
            color = SubtitleGray,
            lineHeight = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        // Bottom row pulled up — smaller bottom padding
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(7.dp)
                        .background(DotInactive, CircleShape)
                )
                Box(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(7.dp)
                        .background(DotInactive, CircleShape)
                )
                Box(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(7.dp)
                        .background(PrimaryGreen, CircleShape)
                )
            }

            Button(
                onClick = onFinish,
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                modifier = Modifier.height(44.dp)
            ) {
                Text(
                    text = "Let's Go",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}