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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
private val MapBg = Color(0xFFE4E9F0)
private val MapAccentPurple = Color(0xFF8B7CF6)
private val DefaultBadgeBg = Color(0xFFE9EEFF)

// ---------------------------------------------------------------------------
// Models
// ---------------------------------------------------------------------------
data class SavedAddress(
    val id: String,
    val label: String,
    val isDefault: Boolean = false,
    val addressLine: String,
    val contactLine: String,
    val icon: ImageVector
)

val sampleAddresses = listOf(
    SavedAddress(
        id = "a1",
        label = "Private Residence",
        isDefault = true,
        addressLine = "4521 Emerald Bay Drive, Apt 4B, Miami, FL 33131, United States",
        contactLine = "Ahmed Ali  •  +1 (555) 012-3456",
        icon = Icons.Filled.Home
    ),
    SavedAddress(
        id = "a2",
        label = "Design Studio HQ",
        isDefault = false,
        addressLine = "1200 Brickell Avenue, Suite 800, Miami, FL 33131, United States",
        contactLine = "Ahmed Ali  •  +1 (555) 987-6543",
        icon = Icons.Filled.Business
    )
)

// ---------------------------------------------------------------------------
// Screen
// ---------------------------------------------------------------------------
@Composable
fun AddressesScreen(
    addresses: List<SavedAddress> = sampleAddresses,
    onBellClick: () -> Unit = {},
    onAddressClick: (SavedAddress) -> Unit = {},
    onAddressMenuClick: (SavedAddress) -> Unit = {},
    onAddNewAddressClick: () -> Unit = {},
    onProfileNavClick: () -> Unit = {},
    onOrdersNavClick: () -> Unit = {},
    onWishlistNavClick: () -> Unit = {},
    onSellNavClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = BackgroundGray,
        bottomBar = {
            ShopFlowBottomBar(
                selectedTab = ShopFlowTab.ORDERS,
                onProfileClick = onProfileNavClick,
                onOrdersClick = onOrdersNavClick,
                onWishlistClick = onWishlistNavClick,
                onSellClick = onSellNavClick
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNewAddressClick,
                containerColor = PrimaryBlue,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add address")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(BackgroundGray),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item { ScreenTopBar(title = "Delivery Addresses", onBellClick = onBellClick) }

            item { MapPreviewCard() }

            item {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 20.dp, bottom = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Saved Locations", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        Text("${addresses.size} Total", fontSize = 11.sp, color = HintGray)
                    }
                    Text(
                        "Manage your primary shipping destinations",
                        fontSize = 12.sp,
                        color = HintGray,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            items(addresses) { address ->
                AddressCard(
                    address = address,
                    onClick = { onAddressClick(address) },
                    onMenuClick = { onAddressMenuClick(address) }
                )
            }

            item { MissingLocationCard(onAddNewAddressClick) }
        }
    }
}

@Composable
private fun MapPreviewCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(160.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(MapBg)
            .border(2.dp, MapAccentPurple, RoundedCornerShape(18.dp))
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(38.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.LocationOn, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.size(20.dp))
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.MyLocation, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text("Precise delivery in Miami", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
                Text("Using high-accuracy GPS pins", fontSize = 10.sp, color = HintGray)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = HintGray, modifier = Modifier.size(14.dp))
        }
    }
}

@Composable
private fun AddressCard(
    address: SavedAddress,
    onClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 12.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardWhite)
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(DefaultBadgeBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(address.icon, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.size(17.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(address.label, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
                if (address.isDefault) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(DefaultBadgeBg)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text("DEFAULT", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = PrimaryBlue)
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(address.addressLine, fontSize = 12.sp, color = HintGray, lineHeight = 16.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(address.contactLine, fontSize = 11.sp, color = HintGray)
        }
        Icon(
            Icons.Filled.MoreVert,
            contentDescription = null,
            tint = HintGray,
            modifier = Modifier
                .size(18.dp)
                .clickable(onClick = onMenuClick)
        )
    }
}

@Composable
private fun MissingLocationCard(onAddNewAddressClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 4.dp, bottom = 20.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardWhite)
            .padding(vertical = 24.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(BackgroundGray),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.LocationOn, contentDescription = null, tint = HintGray, modifier = Modifier.size(18.dp))
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text("Missing a location?", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
        Text(
            "Add a new address to speed up your next checkout experience.",
            fontSize = 11.sp,
            color = HintGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 2.dp, start = 12.dp, end = 12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(
            onClick = onAddNewAddressClick,
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryBlue)
        ) {
            Text("Add New Address", fontSize = 12.sp, fontWeight = FontWeight.Medium)
        }
    }
}