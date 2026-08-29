package com.example.multi_application.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PrimaryBlue = Color(0xFF3366FF)
private val BackgroundGray = Color(0xFFF7F7F9)
private val ChipGray = Color(0xFFEFEFF1)
private val CardWhite = Color.White
private val HintGray = Color(0xFF9A9AA0)
private val TextDark = Color(0xFF1A1A1A)
private val BorderBlue = Color(0xFFCBD8FF)

data class PopularSearchItem(
    val title: String,
    val category: String
)

private val defaultRecentSearches = listOf(
    "Wireless Headphones",
    "MacBook Pro M3",
    "Leather Crossbody Bag",
    "Smart Home Hub"
)

private val popularNowItems = listOf(
    PopularSearchItem("Smartwatch", "ELECTRONICS"),
    PopularSearchItem("Linen Shirt", "FASHION"),
    PopularSearchItem("Mechanical Keyboard", "TECH"),
    PopularSearchItem("Ceramic Vase", "HOME")
)

@Composable
fun SearchScreen(
    recentSearches: List<String> = defaultRecentSearches,
    popularSearches: List<PopularSearchItem> = popularNowItems,
    onBackClick: () -> Unit = {},
    onFilterClick: () -> Unit = {},
    onMicClick: () -> Unit = {},
    onClearAllClick: () -> Unit = {},
    onRecentSearchClick: (String) -> Unit = {},
    onPopularItemClick: (PopularSearchItem) -> Unit = {},
    onSearchSubmit: (String) -> Unit = {}
) {
    var query by remember { mutableStateOf("") }

    androidx.compose.foundation.lazy.LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            SearchTopBar(
                query = query,
                onQueryChange = { query = it },
                onBackClick = onBackClick,
                onFilterClick = onFilterClick,
                onMicClick = onMicClick,
                onSubmit = { onSearchSubmit(query) }
            )
        }
        if (recentSearches.isNotEmpty()) {
            item { RecentSearchesSection(recentSearches, onClearAllClick, onRecentSearchClick) }
        }
        item { PopularNowSection(popularSearches, onPopularItemClick) }
    }
}

@Composable
private fun SearchTopBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onFilterClick: () -> Unit,
    onMicClick: () -> Unit,
    onSubmit: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = TextDark)
        }

        Spacer(modifier = Modifier.width(4.dp))

        Row(
            modifier = Modifier
                .weight(1f)
                .height(46.dp)
                .clip(RoundedCornerShape(23.dp))
                .background(CardWhite)
                .border(1.dp, BorderBlue, RoundedCornerShape(23.dp))
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.Search, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.weight(1f)) {
                if (query.isEmpty()) {
                    Text("Search products, brands...", color = HintGray, fontSize = 14.sp)
                }
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    singleLine = true,
                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp, color = TextDark),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            IconButton(onClick = onMicClick, modifier = Modifier.size(22.dp)) {
                Icon(Icons.Filled.Mic, contentDescription = "Voice search", tint = HintGray, modifier = Modifier.size(18.dp))
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(CardWhite)
                .clickable(onClick = onFilterClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Tune, contentDescription = "Filter", tint = TextDark)
        }
    }
}

@Composable
private fun RecentSearchesSection(
    recentSearches: List<String>,
    onClearAllClick: () -> Unit,
    onRecentSearchClick: (String) -> Unit
) {
    Column(modifier = Modifier.padding(bottom = 24.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "RECENT SEARCHES",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = HintGray,
                letterSpacing = 0.6.sp
            )
            Text(
                "Clear All",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryBlue,
                modifier = Modifier.clickable(onClick = onClearAllClick)
            )
        }

        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            recentSearches.forEach { search ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(ChipGray)
                        .clickable { onRecentSearchClick(search) }
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.Schedule,
                        contentDescription = null,
                        tint = HintGray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(search, fontSize = 14.sp, color = TextDark)
                }
            }
        }
    }
}

@Composable
private fun PopularNowSection(
    items: List<PopularSearchItem>,
    onItemClick: (PopularSearchItem) -> Unit
) {
    Column {
        Text(
            "POPULAR NOW",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = HintGray,
            letterSpacing = 0.6.sp,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 8.dp)
        )
        items.forEachIndexed { index, item ->
            PopularSearchRow(item, onClick = { onItemClick(item) })
            if (index != items.lastIndex) {
                Divider(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = Color(0xFFE7E7EA)
                )
            }
        }
    }
}

@Composable
private fun PopularSearchRow(
    item: PopularSearchItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Filled.TrendingUp,
            contentDescription = null,
            tint = PrimaryBlue,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(item.title, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                item.category,
                fontSize = 10.sp,
                color = HintGray,
                letterSpacing = 0.5.sp
            )
        }
        Icon(
            Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = HintGray,
            modifier = Modifier.size(18.dp)
        )
    }
}