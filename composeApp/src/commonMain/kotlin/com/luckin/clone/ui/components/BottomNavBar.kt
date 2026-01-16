package com.luckin.clone.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.luckin.clone.navigation.BottomNavItem
import com.luckin.clone.navigation.Screen
import com.luckin.clone.ui.theme.*

@Composable
fun LuckinBottomNavBar(
    currentRoute: String,
    onNavigate: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                spotColor = CardShadowColor
            ),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        color = White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()  // Respect system navigation bar
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem.entries.forEach { item ->
                val isSelected = currentRoute == item.screen.route
                
                NavBarItem(
                    item = item,
                    isSelected = isSelected,
                    onClick = { onNavigate(item) }
                )
            }
        }
    }
}

@Composable
private fun NavBarItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    
    // Scale animation
    val scale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.85f
            isSelected -> 1.1f
            else -> 1f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        )
    )
    
    // Color animations
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) LuckinBlue else TextTertiary,
        animationSpec = tween(200)
    )
    
    val labelColor by animateColorAsState(
        targetValue = if (isSelected) LuckinBlue else TextTertiary,
        animationSpec = tween(200)
    )
    
    // Background for home when selected
    val showBackground = item == BottomNavItem.HOME && isSelected
    
    Column(
        modifier = Modifier
            .scale(scale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    isPressed = true
                    onClick()
                }
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            // Animated background for home
            if (showBackground) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(LuckinBlueVeryLight)
                )
            }
            
            // Custom SVG-style icon
            when (item) {
                BottomNavItem.HOME -> HomeIcon(
                    color = iconColor,
                    filled = isSelected,
                    size = 26.dp
                )
                BottomNavItem.MENU -> MenuIcon(
                    color = iconColor,
                    filled = isSelected,
                    size = 26.dp
                )
                BottomNavItem.ORDER -> OrderIcon(
                    color = iconColor,
                    filled = isSelected,
                    size = 26.dp
                )
                BottomNavItem.ACCOUNT -> AccountIcon(
                    color = iconColor,
                    filled = isSelected,
                    size = 26.dp
                )
            }
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = item.label,
            style = MaterialTheme.typography.labelSmall,
            color = labelColor,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
    
    // Reset press state
    LaunchedEffect(isPressed) {
        if (isPressed) {
            kotlinx.coroutines.delay(100)
            isPressed = false
        }
    }
}
