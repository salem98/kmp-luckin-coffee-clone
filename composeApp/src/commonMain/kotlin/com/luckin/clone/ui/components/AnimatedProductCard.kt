package com.luckin.clone.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.luckin.clone.data.model.Product
import com.luckin.clone.ui.theme.*
import com.luckin.clone.util.formatPrice

/**
 * Enhanced Product Card with scroll-based animations and micro-interactions
 */
@Composable
fun AnimatedProductCard(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    animationDelay: Int = 0
) {
    var isPressed by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(false) }
    
    // Entry animation
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(animationDelay.toLong())
        isVisible = true
    }
    
    // Press animation
    val scale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.92f
            else -> 1f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        )
    )
    
    // Entry animation values
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(400, easing = FastOutSlowInEasing)
    )
    
    val offsetY by animateFloatAsState(
        targetValue = if (isVisible) 0f else 30f,
        animationSpec = tween(400, easing = FastOutSlowInEasing)
    )
    
    Card(
        modifier = modifier
            .width(160.dp)
            .graphicsLayer {
                this.alpha = alpha
                translationY = offsetY
            }
            .scale(scale)
            .shadow(
                elevation = if (isPressed) 2.dp else 6.dp,
                shape = ProductCardShape,
                spotColor = CardShadowColor
            ),
        shape = ProductCardShape,
        colors = CardDefaults.cardColors(containerColor = White),
        onClick = {
            isPressed = true
            onClick()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Image placeholder with badges
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color(0xFFE8F4F8),
                                Color(0xFFF5F9FA)
                            )
                        )
                    )
            ) {
                // Discount badge - top left with pulse animation
                if (product.discountPercent > 0) {
                    AnimatedDiscountBadge(
                        discountPercent = product.discountPercent,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                    )
                }
                
                // NEW badge
                if (product.isNew) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = NewBadgeOrange,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 8.dp, top = if (product.discountPercent > 0) 36.dp else 8.dp)
                    ) {
                        Text(
                            text = "NEW",
                            style = MaterialTheme.typography.labelSmall,
                            color = White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                
                // Product image placeholder
                Text(
                    text = "☕",
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            
            // Product info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                // Nutri-Grade indicator
                product.nutriGrade?.let { grade ->
                    NutriGradeBadge(
                        grade = grade,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }
                
                // Product name
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                
                // Prices row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "\$${product.discountedPrice.formatPrice()}",
                        style = MaterialTheme.typography.titleMedium,
                        color = DiscountRed,
                        fontWeight = FontWeight.Bold
                    )
                    
                    if (product.discountPercent > 0) {
                        Text(
                            text = "\$${product.originalPrice.formatPrice()}",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextTertiary,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AnimatedDiscountBadge(
    discountPercent: Int,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    
    // Subtle pulse animation
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    Surface(
        modifier = modifier.scale(pulseScale),
        shape = RoundedCornerShape(4.dp),
        color = Color(0xFFFFF3E0)
    ) {
        Text(
            text = "${discountPercent}% OFF",
            style = MaterialTheme.typography.labelMedium,
            color = LuckinOrange,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
