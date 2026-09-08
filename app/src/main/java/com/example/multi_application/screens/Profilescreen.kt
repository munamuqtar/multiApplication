package com.example.multi_application.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ---------------------------------------------------------------------------
// Shared design tokens (kept identical to the rest of the app)
// ---------------------------------------------------------------------------
private val PrimaryBlue = Color(0xFF3366FF)
private val BackgroundGray = Color(0xFFF7F7F9)
private val CardWhite = Color.White
private val HintGray = Color(0xFF9A9AA0)
private val TextDark = Color(0xFF1A1A1A)
private val GreenBadgeBg = Color(0xFFE1F6E7)
private val GreenBadgeText = Color(0xFF1E9E4A)
private val GoldBadgeBg = Color(0xFFF6EFDC)
private val GoldBadgeText = Color(0xFFB8860B)
private val DividerGray = Color(0xFFE7E7EA)
private val OnlineGreen = Color(0xFF2ECC71)
private val NavyDark = Color(0xFF0F1E3D)
private val RedLogout = Color(0xFFE0453C)

// ---------------------------------------------------------------------------
// Models
// ---------------------------------------------------------------------------
data class ProfileUser(
    val name: String,
    val email: String,
    val isVerifiedBuyer: Boolean = true,
    val memberTier: String? = "Gold Member",
    val walletBalance: String = "$1,240.00"
)

private data class ProfileMenuItem(
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val title: String,
    val subtitle: String
)

private val shoppingActivityItems = listOf(
    ProfileMenuItem(Icons.Filled.ShoppingBag, "My Orders", "Track, return, or buy again"),
    ProfileMenuItem(Icons.Outlined.FavoriteBorder, "My Wishlist", "Items you've saved for later"),
    ProfileMenuItem(Icons.Filled.CreditCard, "MultiMarket Wallet", "Balance: ")
)

private val personalInfoItems = listOf(
    ProfileMenuItem(Icons.Filled.Person, "Profile Details", "Name, email, and phone"),
    ProfileMenuItem(Icons.Filled.LocationOn, "Addresses", "Manage shipping locations"),
    ProfileMenuItem(Icons.Filled.CreditCard, "Payment Methods", "Visa, Mastercard, Apple Pay")
)

private val supportLegalItems = listOf(
    ProfileMenuItem(Icons.Filled.Notifications, "Notifications", "Alerts, promotions, and status"),
    ProfileMenuItem(Icons.Filled.Settings, "App Settings", "Privacy, language, and theme"),
    ProfileMenuItem(Icons.Filled.HelpOutline, "Help Center", "FAQ and customer support")
)

val sampleProfileUser = ProfileUser(
    name = "Muna ",
    email = "muna@example.com",
    isVerifiedBuyer = true,
    memberTier = "Gold Member",
    walletBalance = "$1,240.00"
)

// ---------------------------------------------------------------------------
// Screen
// ---------------------------------------------------------------------------
@Composable
fun ProfileScreen(
    user: ProfileUser = sampleProfileUser,
    onNotificationsBellClick: () -> Unit = {},
    onOpenShopClick: () -> Unit = {},
    onMyOrdersClick: () -> Unit = {},
    onMyWishlistClick: () -> Unit = {},
    onWalletClick: () -> Unit = {},
    onProfileDetailsClick: () -> Unit = {},
    onAddressesClick: () -> Unit = {},
    onPaymentMethodsClick: () -> Unit = {},
    onNotificationsSettingsClick: () -> Unit = {},
    onAppSettingsClick: () -> Unit = {},
    onHelpCenterClick: () -> Unit = {},
    onLogOutClick: () -> Unit = {}
) {
    androidx.compose.foundation.lazy.LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item { ProfileTopBar(onNotificationsBellClick) }
        item { ProfileHeader(user) }
        item { StartSellingBanner(onOpenShopClick) }
        item {
            SectionLabel("SHOPPING & ACTIVITY")
        }
        item {
            SectionCard {
                MenuRow(shoppingActivityItems[0], onClick = onMyOrdersClick)
                RowDivider()
                MenuRow(shoppingActivityItems[1], onClick = onMyWishlistClick)
                RowDivider()
                MenuRow(
                    shoppingActivityItems[2].copy(subtitle = "Balance: ${user.walletBalance}"),
                    onClick = onWalletClick
                )
            }
        }
        item { SectionLabel("PERSONAL INFORMATION") }
        item {
            SectionCard {
                MenuRow(personalInfoItems[0], onClick = onProfileDetailsClick)
                RowDivider()
                MenuRow(personalInfoItems[1], onClick = onAddressesClick)
                RowDivider()
                MenuRow(personalInfoItems[2], onClick = onPaymentMethodsClick)
            }
        }
        item { SectionLabel("SUPPORT & LEGAL") }
        item {
            SectionCard {
                MenuRow(supportLegalItems[0], onClick = onNotificationsSettingsClick)
                RowDivider()
                MenuRow(supportLegalItems[1], onClick = onAppSettingsClick)
                RowDivider()
                MenuRow(supportLegalItems[2], onClick = onHelpCenterClick)
            }
        }
        item { LogOutButton(onLogOutClick) }
    }
}

// ---------------------------------------------------------------------------
// Top bar
// ---------------------------------------------------------------------------
@Composable
private fun ProfileTopBar(onNotificationsBellClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Account", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
        IconButton(onClick = onNotificationsBellClick, modifier = Modifier.size(32.dp)) {
            Icon(Icons.Filled.Notifications, contentDescription = "Notifications", tint = TextDark)
        }
    }
}

// ---------------------------------------------------------------------------
// Header: avatar, name, email, badges
// ---------------------------------------------------------------------------
@Composable
private fun ProfileHeader(user: ProfileUser) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 8.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE7E7EA)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = null,
                    tint = HintGray,
                    modifier = Modifier.size(42.dp)
                )
            }
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(3.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(OnlineGreen)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(user.name, fontSize = 19.sp, fontWeight = FontWeight.Bold, color = TextDark)
        Spacer(modifier = Modifier.height(2.dp))
        Text(user.email, fontSize = 13.sp, color = HintGray)

        Spacer(modifier = Modifier.height(10.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            if (user.isVerifiedBuyer) {
                Badge(
                    text = "Verified Buyer",
                    icon = Icons.Filled.Verified,
                    backgroundColor = GreenBadgeBg,
                    contentColor = GreenBadgeText
                )
            }
            user.memberTier?.let { tier ->
                Badge(
                    text = tier,
                    icon = Icons.Filled.WorkspacePremium,
                    backgroundColor = GoldBadgeBg,
                    contentColor = GoldBadgeText
                )
            }
        }
    }
}

@Composable
private fun Badge(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    backgroundColor: Color,
    contentColor: Color
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = contentColor, modifier = Modifier.size(12.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = contentColor)
    }
}

// ---------------------------------------------------------------------------
// "Start Selling" promo banner -> becomes the entry point into the
// Become-a-Seller flow when the user taps "Open Shop".
// ---------------------------------------------------------------------------
@Composable
private fun StartSellingBanner(onOpenShopClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 24.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(NavyDark, Color(0xFF1B3A6B))
                )
            )
            .clickable(onClick = onOpenShopClick)
    ) {
        // Decorative image placeholder on the right, echoing the screenshot's photo panel.
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .width(110.dp)
                .clip(RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp))
                .background(Color(0xFFD9C9B8).copy(alpha = 0.35f))
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(14.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(OnlineGreen)
                .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
            Text("EARN MORE", fontSize = 9.sp, color = Color.White, fontWeight = FontWeight.Bold)
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 14.dp, end = 100.dp, bottom = 14.dp)
        ) {
            Text(
                "Start Selling",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                "Turn your passion into profit on MultiMarket.",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 11.sp,
                lineHeight = 14.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .clickable(onClick = onOpenShopClick)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text("Become Seller", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = NavyDark)
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Grouped settings list
// ---------------------------------------------------------------------------
@Composable
private fun SectionLabel(text: String) {
    Text(
        text,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color = HintGray,
        letterSpacing = 0.6.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 8.dp)
    )
}

@Composable
private fun SectionCard(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 20.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardWhite)
    ) {
        content()
    }
}

@Composable
private fun MenuRow(item: ProfileMenuItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(BackgroundGray),
            contentAlignment = Alignment.Center
        ) {
            Icon(item.icon, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.size(17.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(item.title, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextDark)
            Text(item.subtitle, fontSize = 12.sp, color = HintGray)
        }
        Icon(
            Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = HintGray,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun RowDivider() {
    Divider(color = DividerGray, modifier = Modifier.padding(start = 62.dp))
}

// ---------------------------------------------------------------------------
// Log out
// ---------------------------------------------------------------------------
@Composable
private fun LogOutButton(onLogOutClick: () -> Unit) {
    OutlinedButton(
        onClick = onLogOutClick,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = RedLogout),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(50.dp)
    ) {
        Icon(Icons.Filled.Logout, contentDescription = null, modifier = Modifier.size(17.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text("Log Out", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}