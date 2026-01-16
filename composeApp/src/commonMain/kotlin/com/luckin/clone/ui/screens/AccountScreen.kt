package com.luckin.clone.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luckin.clone.ui.components.*
import com.luckin.clone.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun AccountScreen(
    onLoginClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(100)
        isVisible = true
    }
    
    ScreenScaffold(
        modifier = modifier,
        backgroundColor = Background,
        applyStatusBarPadding = false // We handle it in the header
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Profile header with blue background
            AnimatedSection(visible = isVisible, delayMillis = 0) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = LuckinBlue,
                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Avatar with animation
                        Surface(
                            modifier = Modifier.size(80.dp),
                            shape = CircleShape,
                            color = White
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                AccountIcon(
                                    color = LuckinBlue,
                                    filled = true,
                                    size = 48.dp
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "Guest User",
                            style = MaterialTheme.typography.headlineSmall,
                            color = White,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Button(
                            onClick = onLoginClick,
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = White
                            )
                        ) {
                            Text(
                                text = "Login / Sign Up",
                                style = MaterialTheme.typography.labelLarge,
                                color = LuckinBlue,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Menu items with staggered animation
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val menuItems = listOf(
                    "📍" to "Saved Addresses",
                    "💳" to "Payment Methods",
                    "🎁" to "Rewards & Coupons",
                    "📋" to "Order History",
                    "⚙️" to "Settings",
                    "❓" to "Help & Support"
                )
                
                menuItems.forEachIndexed { index, (icon, title) ->
                    AnimatedSection(
                        visible = isVisible, 
                        delayMillis = 100 + index * 50
                    ) {
                        AccountMenuItem(
                            icon = icon,
                            title = title,
                            onClick = { }
                        )
                    }
                }
            }
            
            // Bottom padding for nav bar
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
private fun AccountMenuItem(
    icon: String,
    title: String,
    onClick: () -> Unit = {}
) {
    ContentCard(
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = icon,
                fontSize = 24.sp
            )
            
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary,
                modifier = Modifier.weight(1f)
            )
            
            Text(
                text = "›",
                style = MaterialTheme.typography.titleLarge,
                color = TextTertiary
            )
        }
    }
}
