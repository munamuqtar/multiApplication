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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PrimaryBlue = Color(0xFF3366FF)
private val BackgroundGray = Color(0xFFF7F7F9)
private val CardWhite = Color.White
private val HintGray = Color(0xFF9A9AA0)
private val TextDark = Color(0xFF1A1A1A)
private val GreenBadgeBg = Color(0xFFE1F6E7)
private val GreenBadgeText = Color(0xFF1E9E4A)
private val ImagePlaceholder = Color(0xFF3D5A5C)
private val DividerGray = Color(0xFFE7E7EA)

data class ProductDetails(
    val id: String,
    val name: String,
    val rating: Double,
    val reviewCount: Int,
    val badge: String? = null,
    val price: String,
    val originalPrice: String? = null,
    val imageBackground: Color = ImagePlaceholder,
    val sellerName: String,
    val sellerVerified: Boolean = true,
    val sellerTagline: String,
    val description: String,
    val specifications: List<Pair<String, String>>,
    val shippingAndReturns: String
)

// Default sample matching the screenshot. Swap for a real repository lookup by id.
val sampleAureliaHandbag = ProductDetails(
    id = "aurelia-signature-handbag",
    name = "Aurelia Signature Leather Handbag",
    rating = 4.9,
    reviewCount = 128,
    badge = "Limited Edition",
    price = "$2,450.00",
    originalPrice = "$3,100.00",
    sellerName = "Elysian Boutique",
    sellerVerified = true,
    sellerTagline = "Verified Luxury Partner",
    description = "Crafted from ethically sourced, full-grain Italian calfskin, this " +
            "handbag is built with a reinforced structural silhouette, hand-painted " +
            "edges, and custom-forged gold-tone hardware — designed for everyday " +
            "durability without giving up its refined, artisanal finish.",
    specifications = listOf(
        "Material" to "100% Full-Grain Italian Leather",
        "Hardware" to "24k Gold-Plated Brass",
        "Dimensions" to "32cm x 24cm x 14cm",
        "Lining" to "Premium Microsuede"
    ),
    shippingAndReturns = "Free express shipping. 30-day returns on unused items " +
            "with original packaging and tags."
)

@Composable
fun ProductDetailsScreen(
    product: ProductDetails = sampleAureliaHandbag,
    isFavorite: Boolean = false,
    onBackClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onFavoriteToggle: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onVisitSellerClick: () -> Unit = {},
    onGuaranteeClick: () -> Unit = {},
    onWishlistClick: () -> Unit = {},
    onEnquireClick: (quantity: Int) -> Unit = {},
    onAddToBagClick: (quantity: Int) -> Unit = {}
) {
    var quantity by remember { mutableStateOf(1) }

    Box(modifier = Modifier.fillMaxSize()) {
        androidx.compose.foundation.lazy.LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGray),
            contentPadding = PaddingValues(bottom = 140.dp)
        ) {
            item {
                ProductDetailsTopBar(onBackClick, onSearchClick, isFavorite, onFavoriteToggle)
            }
            item {
                ProductHeroImage(product, onFavoriteToggle, isFavorite, onShareClick)
            }
            item { ProductTitleAndPrice(product) }
            item { SellerCard(product, onVisitSellerClick) }
            item { DescriptionSection(product.description) }
            item { SpecificationsSection(product.specifications) }
            item { ShippingReturnsSection(product.shippingAndReturns) }
        }

        BottomActionBar(
            quantity = quantity,
            onQuantityChange = { quantity = it },
            onGuaranteeClick = onGuaranteeClick,
            onWishlistClick = onWishlistClick,
            onEnquireClick = { onEnquireClick(quantity) },
            onAddToBagClick = { onAddToBagClick(quantity) },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun ProductDetailsTopBar(
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit
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
                    Icons.Filled.Info,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text("Product Details", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onSearchClick) {
                Icon(Icons.Filled.Search, contentDescription = "Search", tint = TextDark)
            }
            IconButton(onClick = onFavoriteToggle) {
                Icon(
                    if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color(0xFFFF3B30) else TextDark
                )
            }
        }
    }
}

@Composable
private fun ProductHeroImage(
    product: ProductDetails,
    isFavoriteCallback: () -> Unit,
    isFavorite: Boolean,
    onShareClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(260.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(product.imageBackground)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CircleIconButton(
                icon = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                tint = if (isFavorite) Color(0xFFFF3B30) else TextDark,
                onClick = isFavoriteCallback
            )
            CircleIconButton(
                icon = Icons.Filled.Share,
                tint = TextDark,
                onClick = onShareClick
            )
        }
    }
}

@Composable
private fun CircleIconButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tint: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(Color.White)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(17.dp))
    }
}

@Composable
private fun ProductTitleAndPrice(product: ProductDetails) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        Text(
            product.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            lineHeight = 25.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Filled.Star,
                contentDescription = null,
                tint = Color(0xFFFFB800),
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("${product.rating}", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
            Text("  ·  ${product.reviewCount} Reviews", fontSize = 13.sp, color = HintGray)

            Spacer(modifier = Modifier.weight(1f))

            product.badge?.let { badge ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(GreenBadgeBg)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(badge, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = GreenBadgeText)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.Bottom) {
            Text(product.price, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = PrimaryBlue)
            product.originalPrice?.let { original ->
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    original,
                    fontSize = 14.sp,
                    color = HintGray,
                    textDecoration = TextDecoration.LineThrough,
                    modifier = Modifier.padding(bottom = 3.dp)
                )
            }
        }
    }
}

@Composable
private fun SellerCard(product: ProductDetails, onVisitClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 20.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardWhite)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFEFEFF1)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Person, contentDescription = null, tint = HintGray, modifier = Modifier.size(20.dp))
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(product.sellerName, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
                if (product.sellerVerified) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        Icons.Filled.Verified,
                        contentDescription = "Verified",
                        tint = PrimaryBlue,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
            Text(product.sellerTagline, fontSize = 12.sp, color = HintGray)
        }

        OutlinedButton(
            onClick = onVisitClick,
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text("Visit", fontSize = 13.sp, color = PrimaryBlue, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun DescriptionSection(description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 20.dp)
    ) {
        Text(
            "DESCRIPTION",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = HintGray,
            letterSpacing = 0.6.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(description, fontSize = 13.sp, color = TextDark.copy(alpha = 0.75f), lineHeight = 19.sp)
    }
}

@Composable
private fun SpecificationsSection(specifications: List<Pair<String, String>>) {
    var expanded by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 12.dp)
    ) {
        CollapsibleSectionHeader(
            title = "Specifications",
            expanded = expanded,
            onToggle = { expanded = !expanded }
        )
        if (expanded) {
            Spacer(modifier = Modifier.height(10.dp))
            specifications.forEachIndexed { index, (label, value) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(label, fontSize = 13.sp, color = HintGray)
                    Text(value, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = TextDark)
                }
                if (index != specifications.lastIndex) {
                    Divider(color = DividerGray)
                }
            }
        }
    }
}

@Composable
private fun ShippingReturnsSection(shippingAndReturns: String) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 12.dp)
    ) {
        CollapsibleSectionHeader(
            title = "Shipping & Returns",
            expanded = expanded,
            onToggle = { expanded = !expanded }
        )
        if (expanded) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                shippingAndReturns,
                fontSize = 13.sp,
                color = TextDark.copy(alpha = 0.75f),
                lineHeight = 19.sp
            )
        }
    }
}

@Composable
private fun CollapsibleSectionHeader(
    title: String,
    expanded: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle)
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
        Icon(
            if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = null,
            tint = HintGray
        )
    }
    Divider(color = DividerGray)
}

@Composable
private fun BottomActionBar(
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    onGuaranteeClick: () -> Unit,
    onWishlistClick: () -> Unit,
    onEnquireClick: () -> Unit,
    onAddToBagClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(BackgroundGray)
            .padding(horizontal = 20.dp)
            .padding(top = 10.dp, bottom = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedIconSquare(icon = Icons.Filled.Shield, onClick = onGuaranteeClick)
            OutlinedIconSquare(icon = Icons.Filled.StarBorder, onClick = onWishlistClick)

            Row(
                modifier = Modifier
                    .height(44.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(CardWhite)
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { if (quantity > 1) onQuantityChange(quantity - 1) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(Icons.Filled.Remove, contentDescription = "Decrease quantity", tint = TextDark)
                }
                Text(
                    "$quantity",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextDark,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )
                IconButton(
                    onClick = { onQuantityChange(quantity + 1) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Increase quantity", tint = TextDark)
                }
            }

            OutlinedButton(
                onClick = onEnquireClick,
                shape = RoundedCornerShape(22.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
            ) {
                Icon(
                    Icons.Filled.ChatBubbleOutline,
                    contentDescription = null,
                    tint = PrimaryBlue,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Enquire", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = PrimaryBlue)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onAddToBagClick,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Icon(Icons.Filled.ShoppingBag, contentDescription = null, tint = Color.White, modifier = Modifier.size(17.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add to Bag", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        }
    }
}

@Composable
private fun OutlinedIconSquare(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(CardWhite)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.size(18.dp))
    }
}