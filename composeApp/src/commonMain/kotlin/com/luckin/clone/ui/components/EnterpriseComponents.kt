package com.luckin.clone.ui.components

import androidx.compose.animation.core.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luckin.clone.ui.theme.*

/**
 * Pull-to-refresh indicator with Luckin branding
 */
@Composable
fun LuckinRefreshIndicator(
    isRefreshing: Boolean,
    modifier: Modifier = Modifier
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
    
    if (isRefreshing) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = CircleShape,
                color = LuckinBlue,
                shadowElevation = 4.dp
            ) {
                Text(
                    text = "☕",
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(12.dp)
                        .rotate(rotation)
                )
            }
        }
    }
}

/**
 * Toast/Snackbar notification component
 */
@Composable
fun LuckinToast(
    message: String,
    icon: String = "✓",
    type: ToastType = ToastType.SUCCESS,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (type) {
        ToastType.SUCCESS -> LuckinGreen
        ToastType.ERROR -> DiscountRed
        ToastType.INFO -> LuckinBlue
        ToastType.WARNING -> LuckinOrange
    }
    
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = icon,
                fontSize = 20.sp
            )
            
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = White,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            
            Surface(
                shape = CircleShape,
                color = White.copy(alpha = 0.2f),
                onClick = onDismiss
            ) {
                Text(
                    text = "✕",
                    style = MaterialTheme.typography.labelMedium,
                    color = White,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

enum class ToastType {
    SUCCESS, ERROR, INFO, WARNING
}

/**
 * Animated counter for cart badge
 */
@Composable
fun AnimatedCartBadge(
    count: Int,
    modifier: Modifier = Modifier
) {
    var previousCount by remember { mutableStateOf(count) }
    
    val animatedCount by animateIntAsState(
        targetValue = count,
        animationSpec = tween(300)
    )
    
    val scale by animateFloatAsState(
        targetValue = if (count != previousCount) 1.3f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        finishedListener = { previousCount = count }
    )
    
    if (count > 0) {
        Surface(
            modifier = modifier
                .size(20.dp)
                .offset(x = 8.dp, y = (-4).dp),
            shape = CircleShape,
            color = DiscountRed
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = if (animatedCount > 99) "99+" else animatedCount.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
            }
        }
    }
}

/**
 * Skeleton loading state for the entire screen
 */
@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Banner skeleton
        ShimmerBanner(height = 200.dp)
        
        // Quick actions skeleton
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(2) {
                ShimmerEffect(
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }
        }
        
        // Section header skeleton
        ShimmerEffect(
            modifier = Modifier
                .width(150.dp)
                .height(24.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        
        // Products skeleton
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(3) {
                ShimmerProductCard(modifier = Modifier.weight(1f))
            }
        }
    }
}

/**
 * Empty state component
 */
@Composable
fun EmptyState(
    icon: String,
    title: String,
    subtitle: String,
    actionText: String? = null,
    onAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = icon,
            fontSize = 64.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = TextPrimary,
            fontWeight = FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        
        if (actionText != null && onAction != null) {
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = onAction,
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LuckinBlue
                )
            ) {
                Text(
                    text = actionText,
                    style = MaterialTheme.typography.labelLarge,
                    color = White,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
        }
    }
}
