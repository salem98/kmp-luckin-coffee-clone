package com.luckin.clone.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luckin.clone.ui.components.*
import com.luckin.clone.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun OrderScreen(
    onBrowseMenuClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(100)
        isVisible = true
    }
    
    ScreenScaffold(
        modifier = modifier,
        backgroundColor = Background,
        header = {
            ScreenHeader(title = "My Orders")
        }
    ) {
        // Empty state with animations
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedSection(visible = isVisible, delayMillis = 0) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Animated coffee loading icon for empty state
                    CoffeeLoadingAnimation(size = 120.dp)
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "No Orders Yet",
                        style = MaterialTheme.typography.headlineSmall,
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Your order history will appear here\nonce you place your first order.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    PrimaryButton(
                        text = "Browse Menu",
                        onClick = onBrowseMenuClick,
                        icon = "☕"
                    )
                }
            }
        }
        
        // Bottom padding for nav bar
        Spacer(modifier = Modifier.height(80.dp))
    }
}
