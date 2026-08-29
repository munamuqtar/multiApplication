package com.example.multi_application.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
private val DeliveredBg = Color(0xFFE1F6E7)
private val DeliveredText = Color(0xFF1E9E4A)
private val PendingBg = Color(0xFFFBEAE9)
private val PendingText = Color(0xFFE0453C)
private val ProcessingBg = Color(0xFFFBF0DD)
private val ProcessingText = Color(0xFFB8860B)
private val HelpBannerBg = Color(0xFFEAF0FF)

// ---------------------------------------------------------------------------
// Models
// ---------------------------------------------------------------------------
enum class OrderStatus { PENDING, PROCESSING, DELIVERED }

data class OrderItem(
    val orderId: String,
    val placedOn: String,
    val title: String,
    val vendor: String,
    val price: String,
    val status: OrderStatus
)

val sampleOrders = listOf(
    OrderItem("MM-99281-X", "Oct 24, 2023", "Modern Wireless Noise-Canceling", "AudioTech Pro", "$249.00", OrderStatus.DELIVERED),
    OrderItem("MM-99304-A", "Oct 26, 2023", "Sleek Leather Minimalist Wallet", "Luxe Goods", "$55.00", OrderStatus.PENDING),
    OrderItem("MM-99295-B", "Oct 25, 2023", "Ceramic Craft Coffee Mug", "Artisan Clay", "$24.00", OrderStatus.PROCESSING)
)

// ---------------------------------------------------------------------------
// Screen
// ---------------------------------------------------------------------------
@Composable
fun MyOrdersScreen(
    orders: List<OrderItem> = sampleOrders,
    onBellClick: () -> Unit = {},
    onOrderDetailsClick: (OrderItem) -> Unit = {},
    onOrderPrimaryActionClick: (OrderItem) -> Unit = {},
    onHelpClick: () -> Unit = {},
    onProfileNavClick: () -> Unit = {},
    onWishlistNavClick: () -> Unit = {},
    onSellNavClick: () -> Unit = {}
) {
    var query by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "Pending", "Processing", "Delivered")

    val filteredOrders = orders.filter { order ->
        (selectedFilter == "All" || order.status.name.equals(selectedFilter, ignoreCase = true)) &&
                (query.isBlank() ||
                        order.title.contains(query, ignoreCase = true) ||
                        order.orderId.contains(query, ignoreCase = true))
    }

    Scaffold(
        containerColor = BackgroundGray,
        bottomBar = {
            ShopFlowBottomBar(
                selectedTab = ShopFlowTab.ORDERS,
                onProfileClick = onProfileNavClick,
                onOrdersClick = {},
                onWishlistClick = onWishlistNavClick,
                onSellClick = onSellNavClick
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
            item { ScreenTopBar(title = "My Orders", onBellClick = onBellClick) }

            item {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 12.dp),
                    placeholder = { Text("Find an order...", color = HintGray, fontSize = 13.sp) },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null, tint = HintGray) },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = CardWhite,
                        focusedContainerColor = CardWhite,
                        unfocusedBorderColor = DividerGray,
                        focusedBorderColor = PrimaryBlue
                    )
                )
            }

            item {
                LazyRow(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(filters) { filter ->
                        FilterPill(
                            text = filter,
                            selected = filter == selectedFilter,
                            onClick = { selectedFilter = filter }
                        )
                    }
                }
            }

            items(filteredOrders) { order ->
                OrderCard(
                    order = order,
                    onDetailsClick = { onOrderDetailsClick(order) },
                    onPrimaryActionClick = { onOrderPrimaryActionClick(order) }
                )
            }

            item { HelpBanner(onClick = onHelpClick) }

            item {
                Text(
                    "Showing recent orders from the last 6 months. For order records, visit our website.",
                    fontSize = 11.sp,
                    color = HintGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun FilterPill(text: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (selected) PrimaryBlue else CardWhite)
            .border(
                width = if (selected) 0.dp else 1.dp,
                color = DividerGray,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = if (selected) Color.White else TextDark
        )
    }
}

@Composable
private fun OrderCard(
    order: OrderItem,
    onDetailsClick: () -> Unit,
    onPrimaryActionClick: () -> Unit
) {
    val (statusBg, statusText, statusLabel) = when (order.status) {
        OrderStatus.DELIVERED -> Triple(DeliveredBg, DeliveredText, "DELIVERED")
        OrderStatus.PENDING -> Triple(PendingBg, PendingText, "PENDING")
        OrderStatus.PROCESSING -> Triple(ProcessingBg, ProcessingText, "PROCESSING")
    }
    val primaryActionLabel = if (order.status == OrderStatus.DELIVERED) "Buy Again" else "Track Order"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 14.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardWhite)
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("ORDER ID  ${order.orderId}", fontSize = 10.sp, color = HintGray, fontWeight = FontWeight.SemiBold)
            Column(horizontalAlignment = Alignment.End) {
                Text("PLACED ON", fontSize = 9.sp, color = HintGray)
                Text(order.placedOn, fontSize = 10.sp, color = HintGray)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(BackgroundGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.ShoppingBag, contentDescription = null, tint = HintGray, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(order.title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
                Text("Vendor: ${order.vendor}", fontSize = 11.sp, color = HintGray)
            }
            Icon(Icons.Filled.MoreVert, contentDescription = null, tint = HintGray, modifier = Modifier.size(18.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(order.price, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = PrimaryBlue)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(statusBg)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(statusLabel, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = statusText)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedButton(
                onClick = onDetailsClick,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TextDark)
            ) {
                Text("Details", fontSize = 12.sp, fontWeight = FontWeight.Medium)
            }
            Button(
                onClick = onPrimaryActionClick,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text(primaryActionLabel, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
private fun HelpBanner(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 4.dp, bottom = 16.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(HelpBannerBg)
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("Need help with an order?", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
            Text("Our support team is available 24/7.", fontSize = 11.sp, color = HintGray)
        }
        Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = PrimaryBlue)
    }
}