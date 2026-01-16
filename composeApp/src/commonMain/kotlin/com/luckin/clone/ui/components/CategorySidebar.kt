package com.luckin.clone.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luckin.clone.data.model.Category
import com.luckin.clone.ui.theme.*

@Composable
fun CategorySidebar(
    categories: List<Category>,
    selectedCategoryId: String,
    onCategorySelected: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(80.dp)
            .fillMaxHeight()
            .background(SurfaceGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        categories.forEach { category ->
            val isSelected = category.id == selectedCategoryId
            
            CategoryItem(
                category = category,
                isSelected = isSelected,
                onClick = { onCategorySelected(category) }
            )
        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) White else Color.Transparent,
        animationSpec = spring(stiffness = Spring.StiffnessMedium)
    )
    
    val indicatorColor by animateColorAsState(
        targetValue = if (isSelected) LuckinBlue else Color.Transparent,
        animationSpec = spring(stiffness = Spring.StiffnessMedium)
    )
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .background(backgroundColor)
            .padding(vertical = 8.dp)
    ) {
        // Left indicator line
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .width(3.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(topEnd = 2.dp, bottomEnd = 2.dp))
                .background(indicatorColor)
        )
        
        // Category content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Badge (if any)
            category.badge?.let { badge ->
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = when {
                        badge.contains("$") -> LuckinBlue
                        badge == "NEW" -> NewBadgeOrange
                        badge == "SALE" -> SaleBadgeRed
                        else -> LuckinBlue
                    }
                ) {
                    Text(
                        text = badge,
                        style = MaterialTheme.typography.labelSmall,
                        color = White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
                        fontSize = 8.sp
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
            
            // Icon
            Text(
                text = category.icon,
                fontSize = 24.sp
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // Name
            Text(
                text = category.name,
                style = MaterialTheme.typography.labelSmall,
                color = if (isSelected) TextPrimary else TextSecondary,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
