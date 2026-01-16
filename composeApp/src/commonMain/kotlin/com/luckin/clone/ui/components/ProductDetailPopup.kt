package com.luckin.clone.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.luckin.clone.data.model.Product
import com.luckin.clone.ui.theme.*
import com.luckin.clone.util.formatPrice

/**
 * Product detail bottom sheet / popup dialog
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailPopup(
    product: Product,
    onDismiss: () -> Unit,
    onAddToCart: (Product, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var quantity by remember { mutableStateOf(1) }
    var isAdding by remember { mutableStateOf(false) }
    
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth(0.95f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(24.dp),
            color = White,
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                // Close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Surface(
                        shape = CircleShape,
                        color = SurfaceGray,
                        onClick = onDismiss
                    ) {
                        Text(
                            text = "✕",
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Product image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(
                                    Color(0xFFE8F4F8),
                                    Color(0xFFF5F9FA)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "☕",
                        fontSize = 80.sp
                    )
                    
                    // Discount badge
                    if (product.discountPercent > 0) {
                        Surface(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(12.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = DiscountRed
                        ) {
                            Text(
                                text = "${product.discountPercent}% OFF",
                                style = MaterialTheme.typography.labelMedium,
                                color = White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                    
                    // NEW badge
                    if (product.isNew) {
                        Surface(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = NewBadgeOrange
                        ) {
                            Text(
                                text = "NEW",
                                style = MaterialTheme.typography.labelMedium,
                                color = White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(20.dp))
                
                // Product name
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Nutri-grade
                product.nutriGrade?.let { grade ->
                    NutriGradeBadge(grade = grade)
                    Spacer(modifier = Modifier.height(12.dp))
                }
                
                // Price row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "\$${product.discountedPrice.formatPrice()}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = DiscountRed,
                        fontWeight = FontWeight.Bold
                    )
                    
                    if (product.discountPercent > 0) {
                        Text(
                            text = "\$${product.originalPrice.formatPrice()}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextTertiary,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Quantity selector
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Quantity",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary
                    )
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Minus button
                        AnimatedQuantityButton(
                            text = "−",
                            enabled = quantity > 1,
                            onClick = { if (quantity > 1) quantity-- }
                        )
                        
                        Text(
                            text = quantity.toString(),
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        
                        // Plus button
                        AnimatedQuantityButton(
                            text = "+",
                            enabled = quantity < 10,
                            onClick = { if (quantity < 10) quantity++ }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Add to cart button with animation
                AnimatedAddToCartButton(
                    totalPrice = product.discountedPrice * quantity,
                    isAdding = isAdding,
                    onClick = {
                        isAdding = true
                        onAddToCart(product, quantity)
                    }
                )
            }
        }
    }
}

@Composable
private fun AnimatedQuantityButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.85f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        )
    )
    
    Surface(
        modifier = Modifier
            .size(40.dp)
            .scale(scale)
            .pointerInput(enabled) {
                detectTapGestures(
                    onPress = {
                        if (enabled) {
                            isPressed = true
                            tryAwaitRelease()
                            isPressed = false
                            onClick()
                        }
                    }
                )
            },
        shape = CircleShape,
        color = if (enabled) LuckinBlue else DividerGray
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge,
                color = if (enabled) White else TextTertiary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun AnimatedAddToCartButton(
    totalPrice: Double,
    isAdding: Boolean,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )
    
    val backgroundColor by animateColorAsState(
        targetValue = if (isAdding) LuckinGreen else LuckinBlue,
        animationSpec = tween(300)
    )
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                        onClick()
                    }
                )
            },
        shape = RoundedCornerShape(28.dp),
        color = backgroundColor,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedContent(
                targetState = isAdding,
                transitionSpec = {
                    fadeIn(tween(200)) togetherWith fadeOut(tween(200))
                }
            ) { adding ->
                if (adding) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "✓",
                            style = MaterialTheme.typography.titleLarge,
                            color = White
                        )
                        Text(
                            text = "Added to Cart!",
                            style = MaterialTheme.typography.titleMedium,
                            color = White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🛒",
                            fontSize = 20.sp
                        )
                        Text(
                            text = "Add to Cart • \$${totalPrice.formatPrice()}",
                            style = MaterialTheme.typography.titleMedium,
                            color = White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
