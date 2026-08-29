package com.example.multi_application.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ---------------------------------------------------------------------------
// Shared tokens (mirrors ProfileScreen.kt)
// ---------------------------------------------------------------------------
private val PrimaryBlue = Color(0xFF3366FF)
private val TextDark = Color(0xFF1A1A1A)
private val NavUnselectedGray = Color(0xFF9A9AA0)
private val NavBarWhite = Color.White
private val NavDividerGray = Color(0xFFE7E7EA)

// ---------------------------------------------------------------------------
// Top bar: "✦ Title" on the left, notification bell on the right.
// Used identically by My Orders, Wishlist, and Addresses.
// ---------------------------------------------------------------------------
@Composable
fun ScreenTopBar(title: String, onBellClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Filled.AutoAwesome,
                contentDescription = null,
                tint = PrimaryBlue,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextDark)
        }
        IconButton(onClick = onBellClick, modifier = Modifier.size(32.dp)) {
            Icon(Icons.Filled.Notifications, contentDescription = "Notifications", tint = TextDark)
        }
    }
}

// ---------------------------------------------------------------------------
// Top bar with a back chevron + centered title, used by multi-step flows
// that need back navigation (e.g. the seller onboarding flow).
// ---------------------------------------------------------------------------
@Composable
fun BackTopBar(
    title: String,
    onBackClick: () -> Unit = {},
    onBellClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(top = 12.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick, modifier = Modifier.size(32.dp)) {
            Icon(Icons.Filled.ChevronLeft, contentDescription = "Back", tint = TextDark)
        }
        Text(
            title,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextDark,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onBellClick, modifier = Modifier.size(32.dp)) {
            Icon(Icons.Filled.Notifications, contentDescription = "Notifications", tint = TextDark)
        }
    }
}

// ---------------------------------------------------------------------------
// Local bottom nav row used by the shopping-flow screens (Orders / Wishlist /
// Addresses / Become-a-Seller). These routes sit outside the app's main
// bottom-nav tabs, so each screen renders its own bar, same as SellerProfileScreen.
// ---------------------------------------------------------------------------
enum class ShopFlowTab { PROFILE, ORDERS, WISHLIST, SELL }

@Composable
fun ShopFlowBottomBar(
    selectedTab: ShopFlowTab,
    onProfileClick: () -> Unit = {},
    onOrdersClick: () -> Unit = {},
    onWishlistClick: () -> Unit = {},
    onSellClick: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxWidth().background(NavBarWhite)) {
        Divider(color = NavDividerGray)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ShopFlowNavItem(
                label = "Profile",
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person,
                selected = selectedTab == ShopFlowTab.PROFILE,
                onClick = onProfileClick
            )
            ShopFlowNavItem(
                label = "Orders",
                selectedIcon = Icons.Filled.Inventory2,
                unselectedIcon = Icons.Outlined.Inventory2,
                selected = selectedTab == ShopFlowTab.ORDERS,
                onClick = onOrdersClick
            )
            ShopFlowNavItem(
                label = "Wishlist",
                selectedIcon = Icons.Filled.Favorite,
                unselectedIcon = Icons.Outlined.FavoriteBorder,
                selected = selectedTab == ShopFlowTab.WISHLIST,
                onClick = onWishlistClick
            )
            ShopFlowNavItem(
                label = "Sell",
                selectedIcon = Icons.Filled.Storefront,
                unselectedIcon = Icons.Outlined.Storefront,
                selected = selectedTab == ShopFlowTab.SELL,
                onClick = onSellClick
            )
        }
    }
}

@Composable
private fun ShopFlowNavItem(
    label: String,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    val tint = if (selected) PrimaryBlue else NavUnselectedGray
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp)
    ) {
        Icon(
            if (selected) selectedIcon else unselectedIcon,
            contentDescription = label,
            tint = tint,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            label,
            fontSize = 10.sp,
            color = tint,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}