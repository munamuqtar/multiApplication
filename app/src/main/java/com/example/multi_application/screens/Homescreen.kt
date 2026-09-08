package com.example.multi_application.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
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

// -----------------------------------------------------------------------------------
// COLORS
// -----------------------------------------------------------------------------------
private val BackgroundGray = Color(0xFFF5F6F8)
private val CardWhite = Color.White
private val TextDark = Color(0xFF1C1C1E)
private val HintGray = Color(0xFF9A9AA0)
private val GreenAccent = Color(0xFF2FB159)
private val GreenDark = Color(0xFF1E9E4C)
private val BlueBadge = Color(0xFF3366FF)
private val OrangeIcon = Color(0xFFE8A33D)
private val RedFlame = Color(0xFFE9573F)
private val PinkFruit = Color(0xFFE85D75)
private val LeafGreen = Color(0xFF57B85C)
private val DrinkBlue = Color(0xFF4AA3D9)

// -----------------------------------------------------------------------------------
// MODELS
// -----------------------------------------------------------------------------------
data class GroceryCategory(
    val name: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val iconColor: Color,
    val backgroundColor: Color
)

data class GroceryStore(
    val name: String,
    val shortLabel: String,
    val logoBackground: Color,
    val logoTextColor: Color = Color.White
)

data class FruitProduct(
    val id: String,
    val name: String,
    val price: String,
    val imageColor: Color,
    val hasFreeDelivery: Boolean = true
)

val sampleGroceryCategories = listOf(
    GroceryCategory("Halal Shop", Icons.Filled.Storefront, OrangeIcon, Color(0xFFFCEFDA)),
    GroceryCategory("Best Deals", Icons.Filled.LocalFireDepartment, RedFlame, Color(0xFFFCE7E2)),
    GroceryCategory("Fruits", Icons.Filled.Nature, PinkFruit, Color(0xFFFCE6EC)),
    GroceryCategory("Vegetable", Icons.Filled.Grass, LeafGreen, Color(0xFFE6F6E4)),
    GroceryCategory("Drinks", Icons.Filled.LocalDrink, DrinkBlue, Color(0xFFE3F2FA))
)

private val sampleStores = listOf(
    GroceryStore("Walmart", "W", Color(0xFF0071CE)),
    GroceryStore("Carrefour", "C", Color(0xFFE30613)),
    GroceryStore("Lulu Hyp...", "L", Color(0xFF00A651)),
    GroceryStore("Nesto", "N", Color(0xFFEE2E24)),
    GroceryStore("VIVA Sup...", "V", Color(0xFFF7941D)),
    GroceryStore("Al Maya", "A", Color(0xFF6E3B8B))
)

private val sampleFruits = listOf(
    FruitProduct("1", "Papaya", "$2.99 / kg", Color(0xFFF3A93A)),
    FruitProduct("2", "Strawberry", "$4.49 / box", Color(0xFFE94B5C))
)

// -----------------------------------------------------------------------------------
// MAIN SCREEN
// -----------------------------------------------------------------------------------
@Composable
fun HomeScreen(
    userName: String = "Muna",
    userLocation: String = "somalia, mugdisho",
    onSearchClick: () -> Unit = {},
    onFilterClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onLocationClick: () -> Unit = {},
    onSeeAllCategoriesClick: () -> Unit = {},
    onCategoryClick: (GroceryCategory) -> Unit = {},
    onSeeAllStoresClick: () -> Unit = {},
    onStoreClick: (GroceryStore) -> Unit = {},
    onSeeAllFruitsClick: () -> Unit = {},
    onProductClick: (FruitProduct) -> Unit = {},
    onGetNowClick: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize().background(BackgroundGray)) {
        androidx.compose.foundation.lazy.LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 20.dp)
        ) {
            item { TopBar(userName, userLocation, onCartClick, onNotificationsClick, onLocationClick) }
            item { SearchBar(onSearchClick, onFilterClick) }
            item { PromoBanner(onGetNowClick) }
            item { CategoriesSection(onCategoryClick) }
            item { ViewMoreCategoriesButton(onSeeAllCategoriesClick) }
            item { SectionHeader("Top Grocery Stores", onSeeAllStoresClick) }
            item { StoresRow(onStoreClick) }
            item { SectionHeader("Top Fruits 2025", onSeeAllFruitsClick) }
            item { FruitsRow(onProductClick) }
        }
    }
}

// -----------------------------------------------------------------------------------
// TOP BAR
// -----------------------------------------------------------------------------------
@Composable
private fun TopBar(
    userName: String,
    userLocation: String,
    onCartClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onLocationClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFD8DCE3)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = null,
                    tint = Color(0xFF8A8F98),
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(userName, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .clip(CircleShape)
                            .background(GreenAccent),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "Verified",
                            tint = Color.White,
                            modifier = Modifier.size(9.dp)
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(onClick = onLocationClick)
                ) {
                    Text(userLocation, fontSize = 11.sp, color = HintGray)
                    Icon(
                        Icons.Filled.ExpandMore,
                        contentDescription = null,
                        tint = HintGray,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(BackgroundGray)
                    .clickable(onClick = onCartClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.ShoppingBag,
                    contentDescription = "Cart",
                    tint = TextDark,
                    modifier = Modifier.size(19.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(BackgroundGray)
                        .clickable(onClick = onNotificationsClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = TextDark,
                        modifier = Modifier.size(19.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 2.dp, y = (-2).dp)
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(RedFlame),
                    contentAlignment = Alignment.Center
                ) {
                    Text("12", fontSize = 8.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------------
// SEARCH BAR
// -----------------------------------------------------------------------------------
@Composable
private fun SearchBar(
    onSearchClick: () -> Unit,
    onFilterClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 16.dp)
            .height(48.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(CardWhite)
            .clickable(onClick = onSearchClick)
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.Search, contentDescription = null, tint = HintGray, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            "Would you like to eat somethings?",
            color = HintGray,
            fontSize = 13.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(GreenAccent)
                .clickable(onClick = onFilterClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Filled.Tune,
                contentDescription = "Filter",
                tint = Color.White,
                modifier = Modifier.size(15.dp)
            )
        }
    }
}

// -----------------------------------------------------------------------------------
// PROMO BANNER
// -----------------------------------------------------------------------------------
@Composable
private fun PromoBanner(onGetNowClick: () -> Unit) {
    Column(
        modifier = Modifier.padding(bottom = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(150.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color(0xFFDCEFE0), Color(0xFFEFF3D9))
                    )
                )
        ) {
            // Decorative "produce" block standing in for the promo photo
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight()
                    .width(150.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFFB6D98A), Color(0xFF7FAE55))
                        ),
                        shape = RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Eco,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.85f),
                    modifier = Modifier.size(64.dp)
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 18.dp)
                    .fillMaxWidth(0.58f)
            ) {
                Text("Vegetable Offers", fontSize = 13.sp, color = TextDark.copy(alpha = 0.7f))
                Text(
                    "20% OFF",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark
                )
                Text("10 October, 2025", fontSize = 11.sp, color = TextDark.copy(alpha = 0.6f))
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(GreenAccent)
                        .clickable(onClick = onGetNowClick)
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    Text("Get Now", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Carousel dot indicators
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(4) { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .size(if (index == 0) 7.dp else 6.dp)
                        .clip(CircleShape)
                        .background(if (index == 0) GreenAccent else Color(0xFFD6D9DE))
                )
            }
        }
    }
}

// -----------------------------------------------------------------------------------
// CATEGORIES SECTION
// -----------------------------------------------------------------------------------
@Composable
private fun CategoriesSection(onCategoryClick: (GroceryCategory) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        sampleGroceryCategories.forEach { category ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(60.dp)
                    .clickable { onCategoryClick(category) }
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(category.backgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(category.icon, contentDescription = category.name, tint = category.iconColor, modifier = Modifier.size(24.dp))
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    category.name,
                    fontSize = 10.sp,
                    color = TextDark,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun ViewMoreCategoriesButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 22.dp)
            .height(46.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFFEAF7EC))
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Filled.Apps,
            contentDescription = null,
            tint = GreenAccent,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("View More Categories", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = GreenDark)
    }
}

// -----------------------------------------------------------------------------------
// SECTION HEADER (reused for Stores + Fruits)
// -----------------------------------------------------------------------------------
@Composable
private fun SectionHeader(title: String, onSeeAllClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
        Text(
            "View all",
            fontSize = 12.sp,
            color = GreenAccent,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.clickable(onClick = onSeeAllClick)
        )
    }
}

// -----------------------------------------------------------------------------------
// STORES ROW
// -----------------------------------------------------------------------------------
@Composable
private fun StoresRow(onStoreClick: (GroceryStore) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(18.dp),
        modifier = Modifier.padding(bottom = 22.dp)
    ) {
        items(sampleStores) { store ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(64.dp)
                    .clickable { onStoreClick(store) }
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(CardWhite),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(store.logoBackground),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            store.shortLabel,
                            color = store.logoTextColor,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    store.name,
                    fontSize = 10.sp,
                    color = TextDark,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// -----------------------------------------------------------------------------------
// FRUITS ROW
// -----------------------------------------------------------------------------------
@Composable
private fun FruitsRow(onProductClick: (FruitProduct) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(sampleFruits) { fruit ->
            Column(
                modifier = Modifier
                    .width(165.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(CardWhite)
                    .clickable { onProductClick(fruit) }
                    .padding(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(fruit.imageColor.copy(alpha = 0.25f), fruit.imageColor.copy(alpha = 0.55f))
                            )
                        )
                ) {
                    if (fruit.hasFreeDelivery) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(6.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(BlueBadge)
                                .padding(horizontal = 6.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Filled.LocalShipping,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(9.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text("Free Delivery", fontSize = 8.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                    Icon(
                        Icons.Filled.Eco,
                        contentDescription = fruit.name,
                        tint = fruit.imageColor,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(44.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(fruit.name, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
                Spacer(modifier = Modifier.height(2.dp))
                Text(fruit.price, fontSize = 12.sp, color = HintGray)
            }
        }
    }
}