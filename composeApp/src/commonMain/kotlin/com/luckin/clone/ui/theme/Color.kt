package com.luckin.clone.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush

// ============================================
// LUCKIN COFFEE BRAND COLORS
// ============================================

// Primary Blue Palette
val LuckinBlue = Color(0xFF1E4DB4)
val LuckinBlueDark = Color(0xFF1539A6)
val LuckinBlueLight = Color(0xFF4A90D9)
val LuckinBlueVeryLight = Color(0xFFE8F1FF)

// Secondary / Accent Colors
val LuckinOrange = Color(0xFFF5A623)
val LuckinGold = Color(0xFFFFD700)
val LuckinGreen = Color(0xFF4CAF50)

// Neutral Colors
val White = Color(0xFFFFFFFF)
val Background = Color(0xFFF5F7FA)
val SurfaceGray = Color(0xFFF8F9FA)
val DividerGray = Color(0xFFE5E5E5)
val TextPrimary = Color(0xFF1A1A1A)
val TextSecondary = Color(0xFF666666)
val TextTertiary = Color(0xFF999999)

// Status Colors
val StatusClosed = Color(0xFF9E9E9E)
val DiscountRed = Color(0xFFE53935)
val DiscountGreen = Color(0xFF4CAF50)
val NewBadgeOrange = Color(0xFFFF6B35)
val SaleBadgeRed = Color(0xFFE53935)

// Category Highlight
val CategoryActive = LuckinBlue
val CategoryInactive = Color(0xFF757575)

// ============================================
// GRADIENT BRUSHES
// ============================================

val LuckinBlueGradient = Brush.horizontalGradient(
    listOf(LuckinBlue, LuckinBlueLight)
)

val LuckinBlueVerticalGradient = Brush.verticalGradient(
    listOf(LuckinBlue, LuckinBlueDark)
)

val PromoWaterGradient = Brush.linearGradient(
    listOf(
        Color(0xFF87CEEB),
        Color(0xFFB0E0E6),
        Color(0xFFE0F4FF)
    )
)

val CoconutGradient = Brush.linearGradient(
    listOf(
        Color(0xFFE8F5E9),
        Color(0xFFB2DFDB),
        Color(0xFF80CBC4)
    )
)

val SunriseGradient = Brush.linearGradient(
    listOf(
        Color(0xFFFFE4B5),
        Color(0xFFFFD700),
        Color(0xFFFFA500)
    )
)

val CartBarGradient = Brush.horizontalGradient(
    listOf(LuckinBlue, LuckinBlueDark)
)

// ============================================
// SHADOW & ELEVATION
// ============================================
val CardShadowColor = Color(0x1A000000)
