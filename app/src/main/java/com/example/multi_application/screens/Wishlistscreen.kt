package com.example.multi_application.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
private val RedRemove = Color(0xFFE0453C)
private val StarGold = Color(0xFFF5A623)

// ---------------------------------------------------------------------------
// Models
// ---------------------------------------------------------------------------
data class WishlistItem(
    val id: String,
    val name: String,
    val category: String,
    val rating: Double,
    val reviewCount: Int,
    val price: String,
    val isNew: Boolean = false,
    val imageBg: Color
)

val sampleWishlistItems = listOf(
    WishlistItem("w1", "Minimalist Ceramic Vase", "Home Decor", 4.8, 124, "$45.00", isNew = true, imageBg = Color(0xFFEFE7DC)),
    WishlistItem("w2", "Premium Leather Sneakers", "Footwear", 4.9, 88, "$120.00", imageBg = Color(0xFFF2603E)),
    WishlistItem("w3", "Wireless Headphones", "Electronics", 4.7, 230, "$199.00", imageBg = Color(0xFF2B2B2E)),
    WishlistItem("w4", "Gold Wristwatch", "Accessories", 5.0, 42, "$250.00", imageBg = Color(0xFFEFE3D0))
)

// ---------------------------------------------------------------------------
// Screen
// ---------------------------------------------------------------------------
@Composable
fun WishlistScreen(
    items: List<WishlistItem> = sampleWishlistItems,
    onBellClick: () -> Unit = {},
    onClearAllClick: () -> Unit = {},
    onItemClick: (WishlistItem) -> Unit = {},
    onRemoveItemClick: (WishlistItem) -> Unit = {},
    onAddToCartClick: (WishlistItem) -> Unit = {},
    onProfileNavClick: () -> Unit = {},
    onOrdersNavClick: () -> Unit = {},
    onSellNavClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = BackgroundGray,
        bottomBar = {
            ShopFlowBottomBar(
                selectedTab = ShopFlowTab.WISHLIST,
                onProfileClick = onProfileNavClick,
                onOrdersClick = onOrdersNavClick,
                onWishlistClick = {},
                onSellClick = onSellNavClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(BackgroundGray)
        ) {
            ScreenTopBar(title = "Wishlist", onBellClick = onBellClick)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Saved Items", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Text(
                    "Clear All",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = RedRemove,
                    modifier = Modifier.clickable(onClick = onClearAllClick)
                )
            }
            Text(
                "${items.size} products saved",
                fontSize = 12.sp,
                color = HintGray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items) { item ->
                    WishlistCard(
                        item = item,
                        onClick = { onItemClick(item) },
                        onRemoveClick = { onRemoveItemClick(item) },
                        onAddToCartClick = { onAddToCartClick(item) }
                    )
                }
            }
        }
    }
}

@Composable
private fun WishlistCard(
    item: WishlistItem,
    onClick: () -> Unit,
    onRemoveClick: () -> Unit,
    onAddToCartClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(CardWhite)
            .clickable(onClick = onClick)
            .padding(bottom = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp))
                .background(item.imageBg)
        ) {
            if (item.isNew) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(PrimaryBlue)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text("NEW", fontSize = 8.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(22.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable(onClick = onRemoveClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Delete, contentDescription = "Remove", tint = RedRemove, modifier = Modifier.size(12.dp))
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .size(26.dp)
                    .clip(CircleShape)
                    .background(PrimaryBlue)
                    .clickable(onClick = onAddToCartClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.ShoppingBag, contentDescription = "Add to cart", tint = Color.White, modifier = Modifier.size(13.dp))
            }
        }

        Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {
            Text(item.category, fontSize = 10.sp, color = HintGray)
            Text(item.name, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextDark, maxLines = 1)
            Spacer(modifier = Modifier.height(3.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Star, contentDescription = null, tint = StarGold, modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(3.dp))
                Text("${item.rating} (${item.reviewCount})", fontSize = 10.sp, color = HintGray)
            }
            Spacer(modifier = Modifier.height(3.dp))
            Text(item.price, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PrimaryBlue)
        }
    }
}