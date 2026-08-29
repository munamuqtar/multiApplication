package com.example.multi_application.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PrimaryBlue = Color(0xFF3366FF)
private val BackgroundGray = Color(0xFFF7F7F9)
private val CardWhite = Color.White
private val HintGray = Color(0xFF9A9AA0)
private val TextDark = Color(0xFF1A1A1A)
private val RedBadge = Color(0xFFFF3B30)

data class CategoryListItem(
    val name: String,
    val productCount: Int,
    val icon: ImageVector,
    val iconTint: Color,
    val thumbnailBackground: Color
)

private val categoryListItems = listOf(
    CategoryListItem("Technology", 1240, Icons.Filled.PhoneIphone, PrimaryBlue, Color(0xFF2B2B2E)),
    CategoryListItem("Fashion", 3850, Icons.Filled.Checkroom, PrimaryBlue, Color(0xFFD9CFC2)),
    CategoryListItem("Home Decor", 890, Icons.Filled.Chair, PrimaryBlue, Color(0xFFB9BEC4)),
    CategoryListItem("Beauty", 2100, Icons.Filled.AutoAwesome, PrimaryBlue, Color(0xFF7A4B3A)),
    CategoryListItem("Accessories", 1560, Icons.Filled.Watch, PrimaryBlue, Color(0xFFE8A93A)),
    CategoryListItem("Gifts & Toys", 420, Icons.Filled.CardGiftcard, PrimaryBlue, Color(0xFFE7C9C9))
)

@Composable
fun CategoriesScreen(
    cartBadgeCount: Int = 1,
    onBackClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    onSearchQueryClick: () -> Unit = {},
    onCategoryClick: (CategoryListItem) -> Unit = {},
    onExploreSpecialtiesClick: () -> Unit = {}
) {
    androidx.compose.foundation.lazy.LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item { CategoriesTopBar(cartBadgeCount, onBackClick, onCartClick) }
        item { CategoriesSearchBar(onSearchQueryClick) }
        item { CuratedCollectionsHeader() }
        items(categoryListItems) { category ->
            CategoryRow(category, onClick = { onCategoryClick(category) })
        }
        item { CantFindItCard(onExploreSpecialtiesClick) }
    }
}

@Composable
private fun CategoriesTopBar(
    cartBadgeCount: Int,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = TextDark)
            }
            Text("Categories", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark)
        }

        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(PrimaryBlue)
                .clickable(onClick = onCartClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Filled.ShoppingCart,
                contentDescription = "Cart",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
            if (cartBadgeCount > 0) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 6.dp, y = (-6).dp)
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(RedBadge),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "$cartBadgeCount",
                        fontSize = 9.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoriesSearchBar(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 20.dp)
            .height(46.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(CardWhite)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.Search, contentDescription = null, tint = HintGray, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text("Search categories...", color = HintGray, fontSize = 14.sp)
    }
}

@Composable
private fun CuratedCollectionsHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 20.dp)
    ) {
        Text(
            "CURATED COLLECTIONS",
            fontSize = 11.sp,
            color = PrimaryBlue,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.8.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            "Find Exactly What You're\nLooking For",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            lineHeight = 29.sp
        )
    }
}

@Composable
private fun CategoryRow(
    category: CategoryListItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 12.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardWhite)
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(category.thumbnailBackground)
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    category.icon,
                    contentDescription = null,
                    tint = category.iconTint,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(category.name, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text("${category.productCount} Products", fontSize = 12.sp, color = HintGray)
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
private fun CantFindItCard(onExploreSpecialtiesClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 8.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFFE7EEFF), Color(0xFFEFE9FB))
                )
            )
            .padding(18.dp),
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("Can't find it?", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PrimaryBlue)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                "Browse our seasonal clearance or featured brand boutiques.",
                fontSize = 12.sp,
                color = TextDark.copy(alpha = 0.75f),
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(onClick = onExploreSpecialtiesClick)
            ) {
                Text("View All Specialties", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = PrimaryBlue)
                Icon(
                    Icons.Filled.ChevronRight,
                    contentDescription = null,
                    tint = PrimaryBlue,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Simple 2x2 grid glyph, echoing the illustration in the design.
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                GridGlyphSquare()
                GridGlyphSquare()
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                GridGlyphSquare()
                GridGlyphSquare()
            }
        }
    }
}

@Composable
private fun GridGlyphSquare() {
    Box(
        modifier = Modifier
            .size(22.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(Color.White.copy(alpha = 0.7f))
    )
}