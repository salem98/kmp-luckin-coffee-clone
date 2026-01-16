package com.luckin.clone.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val LuckinShapes = Shapes(
    // Extra small - for badges and chips
    extraSmall = RoundedCornerShape(4.dp),
    
    // Small - for buttons and small cards
    small = RoundedCornerShape(8.dp),
    
    // Medium - for cards and containers
    medium = RoundedCornerShape(12.dp),
    
    // Large - for banners and modals
    large = RoundedCornerShape(16.dp),
    
    // Extra large - for bottom sheets and dialogs
    extraLarge = RoundedCornerShape(24.dp)
)

// Custom shapes for specific components
val CardShape = RoundedCornerShape(12.dp)
val BannerShape = RoundedCornerShape(16.dp)
val ButtonShape = RoundedCornerShape(24.dp)
val BadgeShape = RoundedCornerShape(4.dp)
val BottomBarShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
val CategoryIndicatorShape = RoundedCornerShape(2.dp)
val ProductCardShape = RoundedCornerShape(12.dp)
val FloatingCartShape = RoundedCornerShape(28.dp)
val SearchBarShape = RoundedCornerShape(20.dp)
