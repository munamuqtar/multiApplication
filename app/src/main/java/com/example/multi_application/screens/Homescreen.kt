package com.example.multi_application.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PrimaryBlue = Color(0xFF3366FF)
private val BackgroundGray = Color(0xFFF7F7F9)
private val CardWhite = Color.White
private val HintGray = Color(0xFF9A9AA0)
private val TextDark = Color(0xFF1A1A1A)
private val GreenAccent = Color(0xFF34C759)

data class Category(
    val name: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val iconColor: Color,
    val backgroundColor: Color
)

data class Product(
    val id: String,
    val badge: String? = null,
    val badgeColor: Color = PrimaryBlue,
    val brand: String,
    val name: String,
    val rating: Double,
    val reviewCount: Int,
    val price: String,
    val imageBackground: Color
)

data class PremiumPick(
    val title: String,
    val subtitle: String
)

// Public so CategoriesScreen.kt can reuse the same category set / colors.
val sampleCategories = listOf(
    Category("Electronics", Icons.Filled.PhoneIphone, PrimaryBlue, Color(0xFFEAF0FF)),
    Category("Fashion", Icons.Filled.Checkroom, GreenAccent, Color(0xFFE7F9EC)),
    Category("Lifestyle", Icons.Filled.Weekend, Color(0xFF8A63D2), Color(0xFFF1EBFB)),
    Category("Gadgets", Icons.Filled.Watch, Color(0xFFE0A030), Color(0xFFFBF2E2)),
    Category("More", Icons.Filled.MoreHoriz, HintGray, Color(0xFFEFEFF1))
)

private val sampleProducts = listOf(
    Product(
        id = "1",
        badge = "BESTSELLER",
        badgeColor = Color(0xFF1A1A1A),
        brand = "SONY",
        name = "WH-1000XM5",
        rating = 4.9,
        reviewCount = 1240,
        price = "$349.99",
        imageBackground = Color(0xFFE4E4E7)
    ),
    Product(
        id = "2",
        badge = "NEW",
        badgeColor = PrimaryBlue,
        brand = "APPLE",
        name = "MacBook Air M2",
        rating = 4.8,
        reviewCount = 856,
        price = "$1,099",
        imageBackground = Color(0xFFEFEFF1)
    ),
    Product(
        id = "3",
        brand = "TECHVIBE",
        name = "Series 8 Pro",
        rating = 4.7,
        reviewCount = 432,
        price = "$199.50",
        imageBackground = Color(0xFFCFEFE3)
    ),
    Product(
        id = "4",
        badge = "PROFESSIONAL",
        badgeColor = Color(0xFF1A1A1A),
        brand = "DIGITALONE",
        name = "Alpha A7 IV",
        rating = 5.0,
        reviewCount = 328,
        price = "$2,499",
        imageBackground = Color(0xFF2B2B2E)
    )
)

private val premiumPicks = listOf(
    PremiumPick("Tech Essentials", "Curated for your workstation"),
    PremiumPick("Tech Essentials", "Curated for your setup")
)

@Composable
fun HomeScreen(
    userName: String = "Alex Miller",
    onSearchClick: () -> Unit = {},
    onFilterClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    // Fired by the "See All" link in Quick Categories -> navigate to CategoriesScreen.
    onSeeAllCategoriesClick: () -> Unit = {},
    // Fired by tapping any category chip. HomeScreen itself decides that tapping
    // the "More" chip should behave like "See All" and forwards to the same callback.
    onCategoryClick: (Category) -> Unit = {},
    onProductClick: (Product) -> Unit = {},
    onAddToCartClick: (Product) -> Unit = {},
    onShopCollectionClick: () -> Unit = {}
) {
    androidx.compose.foundation.lazy.LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item { TopBar(userName, onNotificationsClick, onMenuClick) }
        item { SearchBar(onSearchClick, onFilterClick) }
        item { PromoBanner(onShopCollectionClick) }
        item {
            QuickCategoriesSection(
                onSeeAllClick = onSeeAllCategoriesClick,
                onCategoryClick = { category ->
                    // "More" chip behaves the same as "See All": go to the full Categories screen.
                    if (category.name == "More") {
                        onSeeAllCategoriesClick()
                    } else {
                        onCategoryClick(category)
                    }
                }
            )
        }
        item { TrendingHeader() }
        item { ProductGrid(onProductClick, onAddToCartClick) }
        item { PremiumPicksSection() }
    }
}

@Composable
private fun TopBar(
    userName: String,
    onNotificationsClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(PrimaryBlue, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.ShoppingBag,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text("WELCOME BACK,", fontSize = 10.sp, color = HintGray, letterSpacing = 0.5.sp)
                Text(userName, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onNotificationsClick) {
                Icon(Icons.Filled.Notifications, contentDescription = "Notifications", tint = TextDark)
            }
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = TextDark)
            }
        }
    }
}

@Composable
private fun SearchBar(
    onSearchClick: () -> Unit,
    onFilterClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(CardWhite)
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.Search, contentDescription = null, tint = HintGray, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Search premium products...",
                color = HintGray,
                fontSize = 14.sp,
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = onSearchClick)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(CardWhite)
                .clickable(onClick = onFilterClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Tune, contentDescription = "Filter", tint = PrimaryBlue)
        }
    }
}

@Composable
private fun PromoBanner(onShopCollectionClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 24.dp)
            .height(160.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF2B2B2E), Color(0xFF3D5A80))
                )
            )
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(GreenAccent)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text("LIMITED OFFER", fontSize = 9.sp, color = Color.White, fontWeight = FontWeight.Bold)
        }

        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp, top = 8.dp)
        ) {
            Text(
                "Upgrade Your\nLifestyle",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 26.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Get up to 40% off on our latest\ntech & fashion arrivals.",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 11.sp,
                lineHeight = 15.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .clickable(onClick = onShopCollectionClick)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text("Shop Collection", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
            }
        }
    }
}

@Composable
private fun QuickCategoriesSection(
    onSeeAllClick: () -> Unit,
    onCategoryClick: (Category) -> Unit
) {
    Column(modifier = Modifier.padding(bottom = 24.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Quick Categories", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(onClick = onSeeAllClick)
            ) {
                Text("See All", fontSize = 13.sp, color = PrimaryBlue)
                Icon(
                    Icons.Filled.ChevronRight,
                    contentDescription = null,
                    tint = PrimaryBlue,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(sampleCategories) { category ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(64.dp)
                        .clickable { onCategoryClick(category) }
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(category.backgroundColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(category.icon, contentDescription = category.name, tint = category.iconColor)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        category.name,
                        fontSize = 11.sp,
                        color = TextDark,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun TrendingHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Trending Now", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextDark)
        Row {
            Text("Popular", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = PrimaryBlue)
            Spacer(modifier = Modifier.width(12.dp))
            Text("New", fontSize = 13.sp, color = HintGray)
        }
    }
}

@Composable
private fun ProductGrid(
    onProductClick: (Product) -> Unit,
    onAddToCartClick: (Product) -> Unit
) {
    // Manual 2-column layout inside a LazyColumn item (avoids nested-scroll LazyVerticalGrid)
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(bottom = 24.dp)
    ) {
        sampleProducts.chunked(2).forEach { rowProducts ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                rowProducts.forEach { product ->
                    ProductCard(
                        product = product,
                        modifier = Modifier.weight(1f),
                        onClick = { onProductClick(product) },
                        onAddToCart = { onAddToCartClick(product) }
                    )
                }
                if (rowProducts.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onAddToCart: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(CardWhite)
            .clickable(onClick = onClick)
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(product.imageBackground)
        ) {
            product.badge?.let { badge ->
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(6.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(product.badgeColor)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(badge, fontSize = 8.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
            Icon(
                Icons.Outlined.FavoriteBorder,
                contentDescription = "Save",
                tint = Color(0xFF6A6A6E),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp)
                    .size(18.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            product.brand,
            fontSize = 10.sp,
            color = PrimaryBlue,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.5.sp
        )
        Text(
            product.name,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextDark,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Filled.Star,
                contentDescription = null,
                tint = Color(0xFFFFB800),
                modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text("${product.rating}", fontSize = 11.sp, color = TextDark)
            Text(" (${product.reviewCount})", fontSize = 11.sp, color = HintGray)
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(product.price, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(PrimaryBlue)
                    .clickable(onClick = onAddToCart),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Add to cart",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun PremiumPicksSection() {
    Column {
        Text(
            "Premium Picks",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(premiumPicks) { pick ->
                Box(
                    modifier = Modifier
                        .width(180.dp)
                        .height(100.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF5B4B8A), Color(0xFF2B2B2E))
                            )
                        )
                        .padding(14.dp)
                ) {
                    Column(modifier = Modifier.align(Alignment.BottomStart)) {
                        Text(pick.title, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Text(pick.subtitle, color = Color.White.copy(alpha = 0.75f), fontSize = 10.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("Explore", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}