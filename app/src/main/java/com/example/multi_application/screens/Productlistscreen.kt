package com.example.multi_application.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PrimaryBlue = Color(0xFF3366FF)
private val BackgroundGray = Color(0xFFF7F7F9)
private val CardWhite = Color.White
private val HintGray = Color(0xFF9A9AA0)
private val TextDark = Color(0xFF1A1A1A)
private val RedBadge = Color(0xFFFF3B30)
private val OrangeBadge = Color(0xFFE0A030)

data class ProductListItem(
    val id: String,
    val badge: String? = null,
    val badgeColor: Color = PrimaryBlue,
    val brand: String,
    val name: String,
    val rating: Double,
    val reviewCount: Int,
    val price: String,
    val imageBackground: Color,
    val isFavorite: Boolean = false
)

// Sample data keyed loosely off the "Technology" example in the design.
// Swap for a real repository/API call per category later.
private fun sampleProductsFor(category: String): List<ProductListItem> = listOf(
    ProductListItem(
        id = "1",
        badge = "NEW",
        badgeColor = PrimaryBlue,
        brand = "SONY",
        name = "WH-1000XM5\nHeadphones",
        rating = 4.9,
        reviewCount = 890,
        price = "$349.99",
        imageBackground = Color(0xFFE4E4E7)
    ),
    ProductListItem(
        id = "2",
        brand = "GARMIN",
        name = "S6 Smartwatch Pro",
        rating = 4.6,
        reviewCount = 610,
        price = "$199.00",
        imageBackground = Color(0xFFEFEFF1),
        isFavorite = true
    ),
    ProductListItem(
        id = "3",
        badge = "PLASMA",
        badgeColor = Color(0xFF1A1A1A),
        brand = "SONY",
        name = "Mirrorless X-T4",
        rating = 4.9,
        reviewCount = 1101,
        price = "$1,699.99",
        imageBackground = Color(0xFFF3EFE6)
    ),
    ProductListItem(
        id = "4",
        badge = "NEW",
        badgeColor = PrimaryBlue,
        brand = "HP",
        name = "Spectre x360 Laptop",
        rating = 4.7,
        reviewCount = 405,
        price = "$1,249.50",
        imageBackground = Color(0xFF2B2B2E)
    ),
    ProductListItem(
        id = "5",
        brand = "SONY",
        name = "WH-1000XM5\nHeadphones",
        rating = 4.0,
        reviewCount = 1023,
        price = "$349.99",
        imageBackground = Color(0xFFE4E4E7)
    ),
    ProductListItem(
        id = "6",
        badge = "SALE",
        badgeColor = OrangeBadge,
        brand = "WINSLY",
        name = "S6 Smartwatch Pro",
        rating = 4.6,
        reviewCount = 610,
        price = "$199.00",
        imageBackground = Color(0xFFEFE6D8),
        isFavorite = true
    )
)

private fun filterChipsFor(category: String): List<String> = when (category) {
    "Technology" -> listOf("All Tech", "Audio", "Wearables", "Cameras")
    "Fashion" -> listOf("All Fashion", "Men", "Women", "Kids")
    "Home Decor" -> listOf("All Decor", "Furniture", "Lighting", "Storage")
    "Beauty" -> listOf("All Beauty", "Skincare", "Makeup", "Haircare")
    "Accessories" -> listOf("All Accessories", "Bags", "Jewelry", "Watches")
    "Gifts & Toys" -> listOf("All Gifts", "Toys", "Games", "Cards")
    else -> listOf("All", "Popular", "New", "Sale")
}

@Composable
fun ProductListScreen(
    category: String = "Technology",
    resultCount: Int = 124,
    onBackClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onFiltersClick: () -> Unit = {},
    onSortClick: () -> Unit = {},
    onLayoutToggleClick: () -> Unit = {},
    onProductClick: (ProductListItem) -> Unit = {},
    onAddToCartClick: (ProductListItem) -> Unit = {},
    onFavoriteToggle: (ProductListItem) -> Unit = {}
) {
    val filterChips = remember(category) { filterChipsFor(category) }
    var selectedChip by remember(category) { mutableStateOf(filterChips.first()) }
    val products = remember(category) { sampleProductsFor(category) }

    androidx.compose.foundation.lazy.LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item { ProductListTopBar(category, onBackClick, onSearchClick) }
        item {
            FilterChipsRow(
                chips = filterChips,
                selected = selectedChip,
                onSelect = { selectedChip = it }
            )
        }
        item {
            ResultsBar(
                resultCount = resultCount,
                onFiltersClick = onFiltersClick,
                onSortClick = onSortClick,
                onLayoutToggleClick = onLayoutToggleClick
            )
        }
        item {
            ProductGridSection(
                products = products,
                onProductClick = onProductClick,
                onAddToCartClick = onAddToCartClick,
                onFavoriteToggle = onFavoriteToggle
            )
        }
        item { ShowingAllItemsFooter() }
    }
}

@Composable
private fun ProductListTopBar(
    category: String,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = TextDark)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "CATEGORY",
                    fontSize = 10.sp,
                    color = HintGray,
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(category, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
            }
            IconButton(onClick = onSearchClick) {
                Icon(Icons.Filled.Search, contentDescription = "Search", tint = TextDark)
            }
        }
    }
}

@Composable
private fun FilterChipsRow(
    chips: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    androidx.compose.foundation.lazy.LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(bottom = 14.dp)
    ) {
        items(chips) { chip ->
            val isSelected = chip == selected
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSelected) PrimaryBlue else CardWhite)
                    .clickable { onSelect(chip) }
                    .padding(horizontal = 16.dp, vertical = 9.dp)
            ) {
                Text(
                    chip,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isSelected) Color.White else TextDark
                )
            }
        }
    }
}

@Composable
private fun ResultsBar(
    resultCount: Int,
    onFiltersClick: () -> Unit,
    onSortClick: () -> Unit,
    onLayoutToggleClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("$resultCount Results", fontSize = 13.sp, color = HintGray)

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextIconAction(
                icon = Icons.Filled.Tune,
                label = "Filters",
                onClick = onFiltersClick
            )
            Spacer(modifier = Modifier.width(14.dp))
            TextIconAction(
                icon = Icons.Filled.ImportExport,
                label = "Sort",
                onClick = onSortClick
            )
            Spacer(modifier = Modifier.width(14.dp))
            IconButton(onClick = onLayoutToggleClick, modifier = Modifier.size(28.dp)) {
                Icon(Icons.Filled.GridView, contentDescription = "Toggle layout", tint = TextDark)
            }
        }
    }
}

@Composable
private fun TextIconAction(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Icon(icon, contentDescription = label, tint = TextDark, modifier = Modifier.size(15.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(label, fontSize = 13.sp, color = TextDark)
    }
}

@Composable
private fun ProductGridSection(
    products: List<ProductListItem>,
    onProductClick: (ProductListItem) -> Unit,
    onAddToCartClick: (ProductListItem) -> Unit,
    onFavoriteToggle: (ProductListItem) -> Unit
) {
    // Manual 2-column layout inside a LazyColumn item (avoids nested-scroll LazyVerticalGrid).
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(bottom = 8.dp)
    ) {
        products.chunked(2).forEach { rowProducts ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                rowProducts.forEach { product ->
                    ProductGridCard(
                        product = product,
                        modifier = Modifier.weight(1f),
                        onClick = { onProductClick(product) },
                        onAddToCart = { onAddToCartClick(product) },
                        onFavoriteToggle = { onFavoriteToggle(product) }
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
private fun ProductGridCard(
    product: ProductListItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onAddToCart: () -> Unit,
    onFavoriteToggle: () -> Unit
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
                .height(110.dp)
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
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp)
                    .size(22.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.9f))
                    .clickable(onClick = onFavoriteToggle),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    if (product.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (product.isFavorite) RedBadge else Color(0xFF6A6A6E),
                    modifier = Modifier.size(13.dp)
                )
            }
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
            maxLines = 2,
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
                    Icons.Filled.ShoppingCart,
                    contentDescription = "Add to cart",
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@Composable
private fun ShowingAllItemsFooter() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("SHOWING ALL ITEMS", fontSize = 10.sp, color = HintGray, letterSpacing = 0.6.sp)
    }
}