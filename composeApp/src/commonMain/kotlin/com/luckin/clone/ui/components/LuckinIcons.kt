package com.luckin.clone.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luckin.clone.ui.theme.*

/**
 * Custom SVG-style icon components for a more polished look
 */

@Composable
fun CoffeeIcon(
    modifier: Modifier = Modifier,
    color: Color = LuckinBlue,
    size: Dp = 24.dp
) {
    Canvas(
        modifier = modifier.size(size)
    ) {
        val strokeWidth = size.toPx() * 0.08f
        
        // Cup body
        drawRoundRect(
            color = color,
            topLeft = Offset(size.toPx() * 0.15f, size.toPx() * 0.25f),
            size = androidx.compose.ui.geometry.Size(size.toPx() * 0.55f, size.toPx() * 0.6f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(size.toPx() * 0.08f),
            style = Stroke(width = strokeWidth)
        )
        
        // Handle
        drawArc(
            color = color,
            startAngle = -60f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(size.toPx() * 0.55f, size.toPx() * 0.35f),
            size = androidx.compose.ui.geometry.Size(size.toPx() * 0.3f, size.toPx() * 0.35f),
            style = Stroke(width = strokeWidth)
        )
        
        // Steam lines
        val steamPath = Path().apply {
            moveTo(size.toPx() * 0.3f, size.toPx() * 0.18f)
            quadraticTo(size.toPx() * 0.35f, size.toPx() * 0.1f, size.toPx() * 0.3f, size.toPx() * 0.05f)
        }
        drawPath(steamPath, color, style = Stroke(width = strokeWidth * 0.6f))
        
        val steamPath2 = Path().apply {
            moveTo(size.toPx() * 0.45f, size.toPx() * 0.2f)
            quadraticTo(size.toPx() * 0.5f, size.toPx() * 0.12f, size.toPx() * 0.45f, size.toPx() * 0.05f)
        }
        drawPath(steamPath2, color, style = Stroke(width = strokeWidth * 0.6f))
    }
}

@Composable
fun CartIcon(
    modifier: Modifier = Modifier,
    color: Color = LuckinBlue,
    size: Dp = 24.dp,
    itemCount: Int = 0
) {
    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier.size(size)
        ) {
            val strokeWidth = size.toPx() * 0.1f
            
            // Cart body
            val cartPath = Path().apply {
                moveTo(size.toPx() * 0.2f, size.toPx() * 0.25f)
                lineTo(size.toPx() * 0.9f, size.toPx() * 0.25f)
                lineTo(size.toPx() * 0.75f, size.toPx() * 0.6f)
                lineTo(size.toPx() * 0.3f, size.toPx() * 0.6f)
                close()
            }
            drawPath(cartPath, color, style = Stroke(width = strokeWidth))
            
            // Handle
            drawLine(
                color = color,
                start = Offset(size.toPx() * 0.1f, size.toPx() * 0.25f),
                end = Offset(size.toPx() * 0.2f, size.toPx() * 0.25f),
                strokeWidth = strokeWidth
            )
            
            // Wheels
            drawCircle(
                color = color,
                radius = size.toPx() * 0.08f,
                center = Offset(size.toPx() * 0.38f, size.toPx() * 0.75f)
            )
            drawCircle(
                color = color,
                radius = size.toPx() * 0.08f,
                center = Offset(size.toPx() * 0.68f, size.toPx() * 0.75f)
            )
        }
        
        // Item count badge
        if (itemCount > 0) {
            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 4.dp, y = (-4).dp)
                    .size(16.dp),
                shape = CircleShape,
                color = DiscountRed
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = if (itemCount > 9) "9+" else itemCount.toString(),
                        style = MaterialTheme.typography.labelSmall,
                        color = White,
                        fontSize = 8.sp
                    )
                }
            }
        }
    }
}

@Composable
fun LocationIcon(
    modifier: Modifier = Modifier,
    color: Color = LuckinBlue,
    size: Dp = 24.dp
) {
    Canvas(
        modifier = modifier.size(size)
    ) {
        val strokeWidth = size.toPx() * 0.1f
        
        // Pin body
        val pinPath = Path().apply {
            moveTo(size.toPx() * 0.5f, size.toPx() * 0.9f)
            lineTo(size.toPx() * 0.2f, size.toPx() * 0.45f)
            quadraticTo(size.toPx() * 0.15f, size.toPx() * 0.15f, size.toPx() * 0.5f, size.toPx() * 0.1f)
            quadraticTo(size.toPx() * 0.85f, size.toPx() * 0.15f, size.toPx() * 0.8f, size.toPx() * 0.45f)
            close()
        }
        drawPath(pinPath, color, style = Stroke(width = strokeWidth))
        
        // Center dot
        drawCircle(
            color = color,
            radius = size.toPx() * 0.12f,
            center = Offset(size.toPx() * 0.5f, size.toPx() * 0.35f)
        )
    }
}

@Composable
fun HomeIcon(
    modifier: Modifier = Modifier,
    color: Color = LuckinBlue,
    filled: Boolean = false,
    size: Dp = 24.dp
) {
    Canvas(
        modifier = modifier.size(size)
    ) {
        val strokeWidth = size.toPx() * 0.1f
        
        // Roof
        val roofPath = Path().apply {
            moveTo(size.toPx() * 0.5f, size.toPx() * 0.1f)
            lineTo(size.toPx() * 0.1f, size.toPx() * 0.45f)
            lineTo(size.toPx() * 0.9f, size.toPx() * 0.45f)
            close()
        }
        
        // House body
        val housePath = Path().apply {
            moveTo(size.toPx() * 0.2f, size.toPx() * 0.45f)
            lineTo(size.toPx() * 0.2f, size.toPx() * 0.85f)
            lineTo(size.toPx() * 0.8f, size.toPx() * 0.85f)
            lineTo(size.toPx() * 0.8f, size.toPx() * 0.45f)
        }
        
        if (filled) {
            drawPath(roofPath, color)
            drawPath(housePath, color, style = Stroke(width = strokeWidth))
            // Door
            drawRoundRect(
                color = if (filled) White else color,
                topLeft = Offset(size.toPx() * 0.4f, size.toPx() * 0.55f),
                size = androidx.compose.ui.geometry.Size(size.toPx() * 0.2f, size.toPx() * 0.3f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(size.toPx() * 0.03f)
            )
        } else {
            drawPath(roofPath, color, style = Stroke(width = strokeWidth))
            drawPath(housePath, color, style = Stroke(width = strokeWidth))
        }
    }
}

@Composable
fun MenuIcon(
    modifier: Modifier = Modifier,
    color: Color = LuckinBlue,
    filled: Boolean = false,
    size: Dp = 24.dp
) {
    Canvas(
        modifier = modifier.size(size)
    ) {
        val strokeWidth = size.toPx() * 0.1f
        
        // Grid squares
        val squareSize = size.toPx() * 0.35f
        val gap = size.toPx() * 0.1f
        
        listOf(
            Offset(size.toPx() * 0.1f, size.toPx() * 0.1f),
            Offset(size.toPx() * 0.55f, size.toPx() * 0.1f),
            Offset(size.toPx() * 0.1f, size.toPx() * 0.55f),
            Offset(size.toPx() * 0.55f, size.toPx() * 0.55f)
        ).forEach { offset ->
            drawRoundRect(
                color = color,
                topLeft = offset,
                size = androidx.compose.ui.geometry.Size(squareSize, squareSize),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(size.toPx() * 0.05f),
                style = if (filled) Fill else Stroke(width = strokeWidth)
            )
        }
    }
}

@Composable
fun OrderIcon(
    modifier: Modifier = Modifier,
    color: Color = LuckinBlue,
    filled: Boolean = false,
    size: Dp = 24.dp
) {
    Canvas(
        modifier = modifier.size(size)
    ) {
        val strokeWidth = size.toPx() * 0.1f
        
        // Document
        drawRoundRect(
            color = color,
            topLeft = Offset(size.toPx() * 0.15f, size.toPx() * 0.1f),
            size = androidx.compose.ui.geometry.Size(size.toPx() * 0.7f, size.toPx() * 0.8f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(size.toPx() * 0.08f),
            style = if (filled) Fill else Stroke(width = strokeWidth)
        )
        
        // Lines
        val lineColor = if (filled) White else color
        listOf(0.3f, 0.45f, 0.6f).forEach { y ->
            drawLine(
                color = lineColor,
                start = Offset(size.toPx() * 0.28f, size.toPx() * y),
                end = Offset(size.toPx() * 0.72f, size.toPx() * y),
                strokeWidth = strokeWidth * 0.6f
            )
        }
    }
}

@Composable
fun AccountIcon(
    modifier: Modifier = Modifier,
    color: Color = LuckinBlue,
    filled: Boolean = false,
    size: Dp = 24.dp
) {
    Canvas(
        modifier = modifier.size(size)
    ) {
        val strokeWidth = size.toPx() * 0.1f
        
        // Head
        drawCircle(
            color = color,
            radius = size.toPx() * 0.2f,
            center = Offset(size.toPx() * 0.5f, size.toPx() * 0.3f),
            style = if (filled) Fill else Stroke(width = strokeWidth)
        )
        
        // Body
        val bodyPath = Path().apply {
            moveTo(size.toPx() * 0.2f, size.toPx() * 0.9f)
            quadraticTo(size.toPx() * 0.2f, size.toPx() * 0.55f, size.toPx() * 0.5f, size.toPx() * 0.55f)
            quadraticTo(size.toPx() * 0.8f, size.toPx() * 0.55f, size.toPx() * 0.8f, size.toPx() * 0.9f)
        }
        drawPath(bodyPath, color, style = if (filled) Fill else Stroke(width = strokeWidth))
    }
}

/**
 * Animated loading spinner
 */
@Composable
fun LoadingSpinner(
    modifier: Modifier = Modifier,
    color: Color = LuckinBlue,
    size: Dp = 32.dp
) {
    val infiniteTransition = rememberInfiniteTransition()
    
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    
    Canvas(
        modifier = modifier
            .size(size)
            .rotate(rotation)
    ) {
        val strokeWidth = size.toPx() * 0.12f
        
        // Background circle
        drawCircle(
            color = color.copy(alpha = 0.2f),
            radius = (size.toPx() - strokeWidth) / 2,
            style = Stroke(width = strokeWidth)
        )
        
        // Animated arc
        drawArc(
            color = color,
            startAngle = 0f,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
    }
}
