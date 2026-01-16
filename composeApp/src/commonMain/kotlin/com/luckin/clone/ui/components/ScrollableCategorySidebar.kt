package com.luckin.clone.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luckin.clone.data.model.Category
import com.luckin.clone.ui.theme.*

/**
 * Scrollable Category Sidebar with smooth scroll, fade indicators, and animations
 */
@Composable
fun ScrollableCategorySidebar(
    categories: List<Category>,
    selectedCategoryId: String,
    onCategorySelected: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    
    // Calculate scroll position for fade effects
    val showTopFade by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 0 }
    }
    val showBottomFade by remember {
        derivedStateOf { 
            val lastVisibleIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleIndex < categories.size - 1
        }
    }
    
    Box(
        modifier = modifier
            .width(88.dp)
            .fillMaxHeight()
            .background(SurfaceGray)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(
                items = categories,
                key = { it.id }
            ) { category ->
                val isSelected = category.id == selectedCategoryId
                
                ScrollableCategoryItem(
                    category = category,
                    isSelected = isSelected,
                    onClick = { onCategorySelected(category) }
                )
            }
        }
        
        // Top fade indicator
        AnimatedVisibility(
            visible = showTopFade,
            enter = fadeIn(tween(200)),
            exit = fadeOut(tween(200)),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                SurfaceGray,
                                SurfaceGray.copy(alpha = 0f)
                            )
                        )
                    )
            )
        }
        
        // Bottom fade indicator
        AnimatedVisibility(
            visible = showBottomFade,
            enter = fadeIn(tween(200)),
            exit = fadeOut(tween(200)),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                SurfaceGray.copy(alpha = 0f),
                                SurfaceGray
                            )
                        )
                    )
            )
        }
    }
}

@Composable
private fun ScrollableCategoryItem(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    
    // Scale animation on press
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        )
    )
    
    // Background color animation
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) White else Color.Transparent,
        animationSpec = tween(200)
    )
    
    // Text color animation
    val textColor by animateColorAsState(
        targetValue = if (isSelected) LuckinBlue else TextSecondary,
        animationSpec = tween(200)
    )
    
    // Selection indicator animation
    val indicatorWidth by animateDpAsState(
        targetValue = if (isSelected) 4.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )
    
    Box(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .scale(scale)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    isPressed = true
                    onClick()
                }
            )
    ) {
        // Left selection indicator
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .width(indicatorWidth)
                .height(32.dp)
                .clip(RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp))
                .background(LuckinBlue)
        )
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Category icon
            Text(
                text = category.icon,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            
            // Category name
            Text(
                text = category.name,
                style = MaterialTheme.typography.labelSmall,
                color = textColor,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 14.sp
            )
            
            // Badge (NEW, SALE, etc.)
            category.badge?.let { badge ->
                Spacer(modifier = Modifier.height(4.dp))
                
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = when {
                        badge.contains("NEW", ignoreCase = true) -> NewBadgeOrange
                        badge.contains("SALE", ignoreCase = true) -> DiscountRed
                        else -> LuckinOrange
                    }
                ) {
                    Text(
                        text = badge,
                        style = MaterialTheme.typography.labelSmall,
                        color = White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 8.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
    
    // Reset press state
    LaunchedEffect(isPressed) {
        if (isPressed) {
            kotlinx.coroutines.delay(100)
            isPressed = false
        }
    }
}
