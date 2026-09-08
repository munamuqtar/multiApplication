package com.example.multi_application.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// -----------------------------------------------------------------------------------
// COLORS
// -----------------------------------------------------------------------------------
private val GreenPrimary = Color(0xFF3CB44E)
private val GreenDark = Color(0xFF1F9B37)
private val CardWhite = Color.White
private val TextDark = Color(0xFF1A1A1A)
private val HintGray = Color(0xFFAFAFAF)
private val FlameOrange = Color(0xFFE9573F)
private val StarYellow = Color(0xFFFFB800)
private val PlateColor = Color(0xFFF4EFE4)

// -----------------------------------------------------------------------------------
// MODEL
// -----------------------------------------------------------------------------------
data class FoodDetails(
    val id: String,
    val name: String,
    val price: String,
    val rating: Double,
    val calories: String,
    val prepTime: String,
    val description: String,
    val imageBackground: Color = Color(0xFF6B8E4E)
)

// Default sample matching the screenshot.
val sampleAvocadaSalad = FoodDetails(
    id = "avocada-salad",
    name = "Avocada Salad",
    price = "$15.00",
    rating = 4.5,
    calories = "100 Kcal",
    prepTime = "20min",
    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Et cursus tortor metus suspendisse sed..."
)

@Composable
fun FoodDetailsScreen(
    food: FoodDetails = sampleAvocadaSalad,
    isFavorite: Boolean = false,
    onBackClick: () -> Unit = {},
    onFavoriteToggle: () -> Unit = {},
    onReadMoreClick: () -> Unit = {},
    onAddToCartClick: (quantity: Int) -> Unit = {}
) {
    var quantity by remember { mutableStateOf(1) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CardWhite)
    ) {
        // ---------------- Green header block ----------------
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .background(GreenPrimary)
        ) {
            FoodDetailsTopBar(
                onBackClick = onBackClick,
                isFavorite = isFavorite,
                onFavoriteToggle = onFavoriteToggle
            )
        }

        // ---------------- White content card ----------------
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 170.dp)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(CardWhite)
        ) {
            Spacer(modifier = Modifier.height(120.dp)) // reserved space for the overlapping plate image

            FoodTitleAndStepper(
                food = food,
                quantity = quantity,
                onIncrease = { quantity++ },
                onDecrease = { if (quantity > 1) quantity-- }
            )

            FoodStatsRow(food)

            AboutFoodSection(
                description = food.description,
                onReadMoreClick = onReadMoreClick
            )

            Spacer(modifier = Modifier.weight(1f))

            AddToCartButton(onClick = { onAddToCartClick(quantity) })

            Spacer(modifier = Modifier.height(20.dp))
        }

        // ---------------- Overlapping circular plate image ----------------
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 90.dp)
                .size(190.dp)
                .clip(CircleShape)
                .background(CardWhite)
                .padding(6.dp)
                .clip(CircleShape)
                .background(PlateColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Filled.Restaurant,
                contentDescription = food.name,
                tint = food.imageBackground,
                modifier = Modifier.size(72.dp)
            )
        }
    }
}

// -----------------------------------------------------------------------------------
// TOP BAR — matches the screenshot exactly: circular translucent back button,
// centered bold white title, circular translucent favorite button
// -----------------------------------------------------------------------------------
@Composable
private fun FoodDetailsTopBar(
    onBackClick: () -> Unit,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPaddingCompat()
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(38.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.22f))
                .clickable(onClick = onBackClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Filled.ChevronLeft,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(22.dp)
            )
        }

        Text(
            "Food Details",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(38.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.22f))
                .clickable(onClick = onFavoriteToggle),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

// Small helper so this file compiles standalone without requiring the accompanist/
// systemuicontroller status-bar-padding dependency. Replace with
// Modifier.statusBarsPadding() (androidx.compose.foundation layout + accompanist,
// or WindowInsets.statusBars) if your project already wires that up.
@Composable
private fun Modifier.statusBarsPaddingCompat(): Modifier = this.padding(top = 8.dp)

// -----------------------------------------------------------------------------------
// TITLE + PRICE + QUANTITY STEPPER
// -----------------------------------------------------------------------------------
@Composable
private fun FoodTitleAndStepper(
    food: FoodDetails,
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
            .padding(bottom = 14.dp)
    ) {
        Text(food.name, fontSize = 21.sp, fontWeight = FontWeight.Bold, color = TextDark)

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(food.price, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = GreenPrimary)

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(22.dp))
                    .background(GreenPrimary)
                    .padding(horizontal = 6.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Filled.Remove,
                    contentDescription = "Decrease quantity",
                    tint = Color.White,
                    modifier = Modifier
                        .size(22.dp)
                        .clickable(onClick = onDecrease)
                )
                Text(
                    "$quantity",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Increase quantity",
                    tint = Color.White,
                    modifier = Modifier
                        .size(22.dp)
                        .clickable(onClick = onIncrease)
                )
            }
        }
    }
}

// -----------------------------------------------------------------------------------
// RATING / CALORIES / PREP TIME ROW
// -----------------------------------------------------------------------------------
@Composable
private fun FoodStatsRow(food: FoodDetails) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
            .padding(bottom = 18.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatItem(icon = Icons.Filled.Star, tint = StarYellow, label = "${food.rating}")
        StatItem(icon = Icons.Filled.LocalFireDepartment, tint = FlameOrange, label = food.calories)
        StatItem(icon = Icons.Filled.AccessTime, tint = HintGray, label = food.prepTime)
    }
}

@Composable
private fun StatItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tint: Color,
    label: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(5.dp))
        Text(label, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
    }
}

// -----------------------------------------------------------------------------------
// ABOUT FOOD SECTION with inline "Read More" link
// -----------------------------------------------------------------------------------
@Composable
private fun AboutFoodSection(
    description: String,
    onReadMoreClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
    ) {
        Text("About food", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
        Spacer(modifier = Modifier.height(8.dp))

        val annotatedText = buildAnnotatedString {
            withStyle(style = SpanStyle(color = HintGray, fontSize = 13.sp)) {
                append(description)
            }
            append(" ")
            withStyle(
                style = SpanStyle(
                    color = GreenPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            ) {
                append("Read More")
            }
        }

        ClickableText(
            text = annotatedText,
            style = androidx.compose.ui.text.TextStyle(lineHeight = 19.sp),
            onClick = { onReadMoreClick() }
        )
    }
}

// -----------------------------------------------------------------------------------
// ADD TO CART BUTTON
// -----------------------------------------------------------------------------------
@Composable
private fun AddToCartButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
            .height(54.dp)
    ) {
        Text("Add to cart", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
    }
}