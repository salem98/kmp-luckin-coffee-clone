package com.luckin.clone.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = LuckinBlue,
    onPrimary = White,
    primaryContainer = LuckinBlueVeryLight,
    onPrimaryContainer = LuckinBlueDark,
    
    secondary = LuckinOrange,
    onSecondary = White,
    secondaryContainer = Color(0xFFFFF3E0),
    onSecondaryContainer = Color(0xFFE65100),
    
    tertiary = LuckinGreen,
    onTertiary = White,
    
    background = Background,
    onBackground = TextPrimary,
    
    surface = White,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceGray,
    onSurfaceVariant = TextSecondary,
    
    outline = DividerGray,
    outlineVariant = Color(0xFFE0E0E0),
    
    error = DiscountRed,
    onError = White
)

private val DarkColorScheme = darkColorScheme(
    primary = LuckinBlueLight,
    onPrimary = Color(0xFF002984),
    primaryContainer = LuckinBlue,
    onPrimaryContainer = LuckinBlueVeryLight,
    
    secondary = LuckinOrange,
    onSecondary = Color(0xFF3E2723),
    
    background = Color(0xFF121212),
    onBackground = White,
    
    surface = Color(0xFF1E1E1E),
    onSurface = White,
    surfaceVariant = Color(0xFF2C2C2C),
    onSurfaceVariant = Color(0xFFCCCCCC),
    
    outline = Color(0xFF444444),
    
    error = Color(0xFFCF6679),
    onError = Color(0xFF000000)
)

@Composable
fun LuckinTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val typography = getLuckinTypography()

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        shapes = LuckinShapes,
        content = content
    )
}
