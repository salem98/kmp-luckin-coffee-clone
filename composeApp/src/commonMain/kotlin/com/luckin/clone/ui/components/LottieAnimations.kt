package com.luckin.clone.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.luckin.clone.ui.theme.*

/**
 * Pure Compose Canvas animations for premium micro-interactions
 * No external libraries required - works on all platforms
 */

/**
 * Coffee cup loading animation with steam
 */
@Composable
fun CoffeeLoadingAnimation(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    isPlaying: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition()
    
    // Steam wave animation
    val steamOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    
    // Cup pulse animation
    val cupScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    // Steam opacity animation
    val steamOpacity by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    Canvas(modifier = modifier.size(size)) {
        val cupWidth = size.toPx() * 0.5f * cupScale
        val cupHeight = size.toPx() * 0.4f * cupScale
        val cupLeft = (size.toPx() - cupWidth) / 2
        val cupTop = size.toPx() * 0.45f
        
        // Draw steam lines
        val steamColor = Color.Gray.copy(alpha = steamOpacity)
        val steamY = cupTop - (steamOffset * size.toPx() * 0.25f)
        
        for (i in 0..2) {
            val xOffset = cupLeft + cupWidth * 0.25f + (i * cupWidth * 0.25f)
            val waveOffset = kotlin.math.sin((steamOffset + i * 0.3f) * 6.28f) * 5f
            
            drawLine(
                color = steamColor,
                start = Offset(xOffset + waveOffset, steamY + 10f),
                end = Offset(xOffset - waveOffset, steamY - 15f),
                strokeWidth = 3f,
                cap = StrokeCap.Round
            )
        }
        
        // Draw cup body
        drawRoundRect(
            color = LuckinBlue,
            topLeft = Offset(cupLeft, cupTop),
            size = Size(cupWidth, cupHeight),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f)
        )
        
        // Draw handle
        drawArc(
            color = LuckinBlue,
            startAngle = -60f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(cupLeft + cupWidth - 5f, cupTop + cupHeight * 0.15f),
            size = Size(cupWidth * 0.35f, cupHeight * 0.6f),
            style = Stroke(width = 6f)
        )
        
        // Draw coffee liquid
        drawRoundRect(
            color = Color(0xFF5D4037),
            topLeft = Offset(cupLeft + 5f, cupTop + cupHeight * 0.3f),
            size = Size(cupWidth - 10f, cupHeight * 0.5f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f)
        )
    }
}

/**
 * Success checkmark animation for add-to-cart confirmation
 */
@Composable
fun SuccessAnimation(
    modifier: Modifier = Modifier,
    size: Dp = 60.dp,
    isPlaying: Boolean = true,
    onAnimationEnd: () -> Unit = {}
) {
    var animationProgress by remember { mutableStateOf(0f) }
    
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            animationProgress = 0f
            animate(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = tween(600, easing = FastOutSlowInEasing)
            ) { value, _ ->
                animationProgress = value
            }
            onAnimationEnd()
        }
    }
    
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(size.toPx() / 2, size.toPx() / 2)
        val radius = size.toPx() * 0.4f * animationProgress
        
        // Draw circle background
        drawCircle(
            color = LuckinGreen,
            radius = radius,
            center = center
        )
        
        // Draw checkmark
        if (animationProgress > 0.5f) {
            val checkProgress = ((animationProgress - 0.5f) * 2f).coerceIn(0f, 1f)
            val checkPath = Path().apply {
                val startX = size.toPx() * 0.3f
                val startY = size.toPx() * 0.5f
                val midX = size.toPx() * 0.45f
                val midY = size.toPx() * 0.65f
                val endX = size.toPx() * 0.7f
                val endY = size.toPx() * 0.35f
                
                moveTo(startX, startY)
                if (checkProgress > 0f) {
                    val progress1 = (checkProgress * 2f).coerceAtMost(1f)
                    lineTo(
                        startX + (midX - startX) * progress1,
                        startY + (midY - startY) * progress1
                    )
                }
                if (checkProgress > 0.5f) {
                    val progress2 = ((checkProgress - 0.5f) * 2f).coerceAtMost(1f)
                    lineTo(
                        midX + (endX - midX) * progress2,
                        midY + (endY - midY) * progress2
                    )
                }
            }
            
            drawPath(
                path = checkPath,
                color = White,
                style = Stroke(width = 4f, cap = StrokeCap.Round)
            )
        }
    }
}

/**
 * Cart bounce animation for when items are added
 */
@Composable
fun CartBounceAnimation(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    isPlaying: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition()
    
    val bounce by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 600
                0f at 0
                -10f at 150
                0f at 300
                -5f at 450
                0f at 600
            },
            repeatMode = RepeatMode.Restart
        )
    )
    
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 600
                0f at 0
                -8f at 150
                8f at 300
                -4f at 450
                0f at 600
            },
            repeatMode = RepeatMode.Restart
        )
    )
    
    Canvas(modifier = modifier.size(size)) {
        rotate(degrees = rotation, pivot = Offset(size.toPx() / 2, size.toPx() / 2)) {
            val cartWidth = size.toPx() * 0.6f
            val cartHeight = size.toPx() * 0.4f
            val cartLeft = (size.toPx() - cartWidth) / 2
            val cartTop = size.toPx() * 0.3f + bounce
            
            // Cart body
            val cartPath = Path().apply {
                moveTo(cartLeft, cartTop)
                lineTo(cartLeft + cartWidth, cartTop)
                lineTo(cartLeft + cartWidth * 0.85f, cartTop + cartHeight)
                lineTo(cartLeft + cartWidth * 0.15f, cartTop + cartHeight)
                close()
            }
            
            drawPath(
                path = cartPath,
                color = LuckinBlue
            )
            
            // Handle
            drawLine(
                color = LuckinBlue,
                start = Offset(cartLeft - 8f, cartTop),
                end = Offset(cartLeft, cartTop),
                strokeWidth = 4f,
                cap = StrokeCap.Round
            )
            
            // Wheels
            val wheelY = cartTop + cartHeight + 8f
            drawCircle(
                color = LuckinBlueDark,
                radius = 6f,
                center = Offset(cartLeft + cartWidth * 0.25f, wheelY)
            )
            drawCircle(
                color = LuckinBlueDark,
                radius = 6f,
                center = Offset(cartLeft + cartWidth * 0.75f, wheelY)
            )
        }
    }
}

/**
 * Pulsing dot loading indicator
 */
@Composable
fun PulsingDotsLoader(
    modifier: Modifier = Modifier,
    color: Color = LuckinBlue,
    dotCount: Int = 3,
    dotSize: Dp = 10.dp,
    spacing: Dp = 8.dp
) {
    val infiniteTransition = rememberInfiniteTransition()
    
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(dotCount) { index ->
            val delay = index * 150
            
            val scale by infiniteTransition.animateFloat(
                initialValue = 0.6f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(600, delayMillis = delay, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
            
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.4f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(600, delayMillis = delay, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
            
            Canvas(modifier = Modifier.size(dotSize)) {
                drawCircle(
                    color = color.copy(alpha = alpha),
                    radius = (dotSize.toPx() / 2) * scale
                )
            }
        }
    }
}
