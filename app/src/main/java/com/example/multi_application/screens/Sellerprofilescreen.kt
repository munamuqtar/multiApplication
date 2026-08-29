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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ---------------------------------------------------------------------------
// Shared design tokens (kept identical to ProductDetailsScreen for consistency)
// ---------------------------------------------------------------------------
private val PrimaryBlue = Color(0xFF3366FF)
private val BackgroundGray = Color(0xFFF7F7F9)
private val CardWhite = Color.White
private val HintGray = Color(0xFF9A9AA0)
private val TextDark = Color(0xFF1A1A1A)
private val GreenBadgeBg = Color(0xFFE1F6E7)
private val GreenBadgeText = Color(0xFF1E9E4A)
private val DividerGray = Color(0xFFE7E7EA)
private val OnlineGreen = Color(0xFF2ECC71)
private val ImagePlaceholder1 = Color(0xFF2B2B2E)
private val ImagePlaceholder2 = Color(0xFFEFE3D3)
private val ImagePlaceholder3 = Color(0xFF6B2E2E)
private val ImagePlaceholder4 = Color(0xFFD8CFC4)
private val ImagePlaceholder5 = Color(0xFF7C5A3C)

// ---------------------------------------------------------------------------
// Models
// ---------------------------------------------------------------------------
data class SellerProduct(
    val id: String,
    val name: String,
    val price: String,
    val imageBackground: Color,
    val isFavorite: Boolean = false
)

data class SellerProfile(
    val id: String,
    val name: String,
    val verified: Boolean = true,
    val isOnline: Boolean = true,
    val location: String,
    val isTopSeller: Boolean = true,
    val rating: Double,
    val reviewCount: Int,
    val bio: String,
    val memberSince: String,
    val followers: String,
    val productCount: Int,
    val responseRate: String,
    val isFollowing: Boolean = false,
    val products: List<SellerProduct>
)

// Sample data matching the screenshot
val sampleElenaSeller = SellerProfile(
    id = "elena-v",
    name = "Elena V.",
    verified = true,
    isOnline = true,
    location = "London, United Kingdom",
    isTopSeller = true,
    rating = 4.9,
    reviewCount = 218,
    bio = "Curating exclusive luxury pieces for discerning collectors. " +
            "Specializing in timeless accessories and rare vintage finds from across Europe.",
    memberSince = "Member since October 2021",
    followers = "1.2k",
    productCount = 84,
    responseRate = "98%",
    isFollowing = false,
    products = listOf(
        SellerProduct("p1", "Classic Minimalist Watch", "$1,250.00", ImagePlaceholder1),
        SellerProduct("p2", "Premium Leather Wallet", "$450.00", ImagePlaceholder2, isFavorite = true),
        SellerProduct("p3", "Polished Buckle Belt", "$320.00", ImagePlaceholder3),
        SellerProduct("p4", "Elegant Silk Scarf", "$580.00", ImagePlaceholder4, isFavorite = true),
        SellerProduct("p5", "Saffiano Leather Tote", "$2,100.00", ImagePlaceholder5)
    )
)

enum class SellerTab { ALL_ITEMS, REVIEWS }

// ---------------------------------------------------------------------------
// Screen
// ---------------------------------------------------------------------------
@Composable
fun SellerProfileScreen(
    seller: SellerProfile = sampleElenaSeller,
    onBackClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onMoreClick: () -> Unit = {},
    onFollowClick: () -> Unit = {},
    onMessageClick: () -> Unit = {},
    onProductClick: (String) -> Unit = {},
    onProductFavoriteToggle: (String) -> Unit = {},
    onViewAllClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onSearchNavClick: () -> Unit = {},
    onBagClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(SellerTab.ALL_ITEMS) }
    var isFollowing by remember { mutableStateOf(seller.isFollowing) }

    Scaffold(
        containerColor = BackgroundGray,
        bottomBar = {
            SellerBottomNavBar(
                onHomeClick = onHomeClick,
                onSearchClick = onSearchNavClick,
                onBagClick = onBagClick,
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->
        androidx.compose.foundation.lazy.LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGray)
                .padding(innerPadding)
        ) {
            item {
                SellerTopBar(onBackClick, onSearchClick, onFavoriteClick)
            }
            item {
                SellerHeaderCard(seller, onShareClick, onMoreClick)
            }
            item {
                FollowMessageRow(
                    isFollowing = isFollowing,
                    onFollowClick = {
                        isFollowing = !isFollowing
                        onFollowClick()
                    },
                    onMessageClick = onMessageClick
                )
            }
            item {
                StatsRow(seller)
            }
            item {
                SellerTabsRow(selectedTab, onTabSelected = { selectedTab = it })
            }
            when (selectedTab) {
                SellerTab.ALL_ITEMS -> {
                    item {
                        ProductGrid(
                            products = seller.products,
                            totalCount = seller.productCount,
                            onProductClick = onProductClick,
                            onProductFavoriteToggle = onProductFavoriteToggle,
                            onViewAllClick = onViewAllClick
                        )
                    }
                }
                SellerTab.REVIEWS -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No reviews to show yet.", fontSize = 13.sp, color = HintGray)
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(12.dp)) }
        }
    }
}

// ---------------------------------------------------------------------------
// Top bar
// ---------------------------------------------------------------------------
@Composable
private fun SellerTopBar(
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = TextDark)
            }
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(PrimaryBlue),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(13.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text("Seller Profile", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onSearchClick) {
                Icon(Icons.Filled.Search, contentDescription = "Search", tint = TextDark)
            }
            IconButton(onClick = onFavoriteClick) {
                Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Favorite", tint = TextDark)
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Seller header (avatar, name, location, badge, rating, bio, member since)
// ---------------------------------------------------------------------------
@Composable
private fun SellerHeaderCard(
    seller: SellerProfile,
    onShareClick: () -> Unit,
    onMoreClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Box {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF1A1A1A)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.Person,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.85f),
                        modifier = Modifier.size(30.dp)
                    )
                }
                if (seller.isOnline) {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .background(Color.White)
                            .padding(2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(OnlineGreen)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        seller.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    if (seller.verified) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            Icons.Filled.Verified,
                            contentDescription = "Verified",
                            tint = PrimaryBlue,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.LocationOn,
                        contentDescription = null,
                        tint = HintGray,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(seller.location, fontSize = 12.sp, color = HintGray)
                }
            }

            Row {
                CircleIconButtonSmall(icon = Icons.Filled.Share, onClick = onShareClick)
                Spacer(modifier = Modifier.width(6.dp))
                CircleIconButtonSmall(icon = Icons.Filled.MoreVert, onClick = onMoreClick)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            if (seller.isTopSeller) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(GreenBadgeBg)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text("Top Seller", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = GreenBadgeText)
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            Icon(
                Icons.Filled.Star,
                contentDescription = null,
                tint = Color(0xFFFFB800),
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text("${seller.rating}", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
            Text(" (${seller.reviewCount})", fontSize = 13.sp, color = HintGray)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            seller.bio,
            fontSize = 13.sp,
            color = TextDark.copy(alpha = 0.75f),
            lineHeight = 19.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Filled.CalendarToday,
                contentDescription = null,
                tint = HintGray,
                modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(seller.memberSince, fontSize = 12.sp, color = HintGray)
        }
    }
}

@Composable
private fun CircleIconButtonSmall(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(CardWhite)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = TextDark, modifier = Modifier.size(15.dp))
    }
}

// ---------------------------------------------------------------------------
// Follow / Message row
// ---------------------------------------------------------------------------
@Composable
private fun FollowMessageRow(
    isFollowing: Boolean,
    onFollowClick: () -> Unit,
    onMessageClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Button(
            onClick = onFollowClick,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isFollowing) CardWhite else PrimaryBlue,
                contentColor = if (isFollowing) PrimaryBlue else Color.White
            ),
            modifier = Modifier
                .weight(1f)
                .height(46.dp)
        ) {
            Text(
                if (isFollowing) "Following" else "Follow Seller",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        OutlinedButton(
            onClick = onMessageClick,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .weight(1f)
                .height(46.dp)
        ) {
            Icon(
                Icons.Filled.ChatBubbleOutline,
                contentDescription = null,
                tint = TextDark,
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text("Message", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
        }
    }
}

// ---------------------------------------------------------------------------
// Stats row (Followers / Products / Response)
// ---------------------------------------------------------------------------
@Composable
private fun StatsRow(seller: SellerProfile) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 18.dp, bottom = 4.dp)
    ) {
        StatItem(value = seller.followers, label = "Followers", modifier = Modifier.weight(1f))
        StatItem(value = "${seller.productCount}", label = "Products", modifier = Modifier.weight(1f))
        StatItem(value = seller.responseRate, label = "Response", modifier = Modifier.weight(1f))
    }
}

@Composable
private fun StatItem(value: String, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
        Spacer(modifier = Modifier.height(2.dp))
        Text(label, fontSize = 12.sp, color = HintGray)
    }
}

// ---------------------------------------------------------------------------
// Tabs (All Items / Reviews) with filter + view toggle icons
// ---------------------------------------------------------------------------
@Composable
private fun SellerTabsRow(
    selectedTab: SellerTab,
    onTabSelected: (SellerTab) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.weight(1f)) {
                SellerTabItem(
                    text = "All Items",
                    selected = selectedTab == SellerTab.ALL_ITEMS,
                    onClick = { onTabSelected(SellerTab.ALL_ITEMS) }
                )
                Spacer(modifier = Modifier.width(20.dp))
                SellerTabItem(
                    text = "Reviews",
                    selected = selectedTab == SellerTab.REVIEWS,
                    onClick = { onTabSelected(SellerTab.REVIEWS) }
                )
            }

            IconButton(onClick = {}, modifier = Modifier.size(30.dp)) {
                Icon(Icons.Filled.FilterList, contentDescription = "Filter", tint = HintGray, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(onClick = {}, modifier = Modifier.size(30.dp)) {
                Icon(Icons.Filled.GridView, contentDescription = "Grid view", tint = PrimaryBlue, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(onClick = {}, modifier = Modifier.size(30.dp)) {
                Icon(Icons.Filled.ViewList, contentDescription = "List view", tint = HintGray, modifier = Modifier.size(18.dp))
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider(color = DividerGray)
    }
}

@Composable
private fun SellerTabItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text,
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
            color = if (selected) TextDark else HintGray
        )
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .height(2.dp)
                .width(if (selected) 28.dp else 0.dp)
                .background(PrimaryBlue)
        )
    }
}

// ---------------------------------------------------------------------------
// Product grid + "View all" tile
// ---------------------------------------------------------------------------
@Composable
private fun ProductGrid(
    products: List<SellerProduct>,
    totalCount: Int,
    onProductClick: (String) -> Unit,
    onProductFavoriteToggle: (String) -> Unit,
    onViewAllClick: () -> Unit
) {
    // Non-scrolling grid embedded inside the outer LazyColumn.
    val rows = products.chunked(2)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        rows.forEachIndexed { rowIndex, rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                rowItems.forEach { product ->
                    ProductCell(
                        product = product,
                        modifier = Modifier.weight(1f),
                        onClick = { onProductClick(product.id) },
                        onFavoriteToggle = { onProductFavoriteToggle(product.id) }
                    )
                }
                val isLastRow = rowIndex == rows.lastIndex
                if (isLastRow && rowItems.size == 1) {
                    ViewAllCell(
                        totalCount = totalCount,
                        modifier = Modifier.weight(1f),
                        onClick = onViewAllClick
                    )
                } else if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
        if (rows.isEmpty() || rows.last().size == 2) {
            ViewAllCell(totalCount = totalCount, modifier = Modifier.fillMaxWidth(0.5f), onClick = onViewAllClick)
        }
    }
}

@Composable
private fun ProductCell(
    product: SellerProduct,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onFavoriteToggle: () -> Unit
) {
    Column(
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(product.imageBackground)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable(onClick = onFavoriteToggle),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    if (product.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (product.isFavorite) Color(0xFFFF3B30) else TextDark,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            product.name,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = TextDark,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            product.price,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = PrimaryBlue
        )
    }
}

@Composable
private fun ViewAllCell(
    totalCount: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(CardWhite)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Filled.GridView,
                contentDescription = null,
                tint = HintGray,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                "View all $totalCount items\nfrom this seller",
                fontSize = 11.sp,
                color = HintGray,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                lineHeight = 14.sp
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Bottom navigation bar
// ---------------------------------------------------------------------------
@Composable
private fun SellerBottomNavBar(
    onHomeClick: () -> Unit,
    onSearchClick: () -> Unit,
    onBagClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Surface(
        color = CardWhite,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavIcon(icon = Icons.Filled.Home, selected = true, onClick = onHomeClick)
            BottomNavIcon(icon = Icons.Filled.Search, selected = false, onClick = onSearchClick)
            BottomNavIcon(
                icon = Icons.Filled.ShoppingBag,
                selected = false,
                onClick = onBagClick,
                badgeCount = 1
            )
            BottomNavIcon(icon = Icons.Filled.Person, selected = false, onClick = onProfileClick)
        }
    }
}

@Composable
private fun BottomNavIcon(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    badgeCount: Int = 0
) {
    Box {
        IconButton(onClick = onClick) {
            Icon(
                icon,
                contentDescription = null,
                tint = if (selected) PrimaryBlue else HintGray,
                modifier = Modifier.size(24.dp)
            )
        }
        if (badgeCount > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-6).dp, y = 6.dp)
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFF3B30)),
                contentAlignment = Alignment.Center
            ) {
                Text("$badgeCount", fontSize = 9.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}