package com.luckin.clone.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luckin.clone.data.MockData
import com.luckin.clone.data.model.Product
import com.luckin.clone.ui.components.*
import com.luckin.clone.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    onProductClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var isLoading by remember { mutableStateOf(true) }
    var showProductPopup by remember { mutableStateOf<Product?>(null) }
    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }
    
    // Simulate loading
    LaunchedEffect(Unit) {
        delay(600)
        isLoading = false
    }
    
    // Calculate scroll-based effects
    val scrollProgress = scrollState.value.toFloat() / 400f
    val headerAlpha = (1f - scrollProgress).coerceIn(0.3f, 1f)
    
    Box(modifier = modifier.fillMaxSize()) {
        if (isLoading) {
            LoadingScreen()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Background)
                    .statusBarsPadding()
                    .verticalScroll(scrollState)
            ) {
                // HorizontalPager Swipeable Banner Carousel
                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            translationY = scrollState.value * 0.3f
                            alpha = headerAlpha
                        }
                ) {
                    HeroBannerCarousel(
                        banners = MockData.banners,
                        onBannerClick = { },
                        autoScrollInterval = 5000L
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Main content card with rounded top
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    color = White
                ) {
                    Column(
                        modifier = Modifier.padding(top = 20.dp)
                    ) {
                        // Greeting section with entrance animation
                        var greetingVisible by remember { mutableStateOf(false) }
                        LaunchedEffect(Unit) {
                            delay(100)
                            greetingVisible = true
                        }
                        
                        AnimatedVisibility(
                            visible = greetingVisible,
                            enter = fadeIn(tween(400)) + slideInVertically { 20 }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Good Morning ☀️",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = TextSecondary
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Happy New Year 2026!",
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = TextPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    // Notification badge
                                    Surface(
                                        shape = CircleShape,
                                        color = SurfaceGray
                                    ) {
                                        Box(
                                            modifier = Modifier.padding(10.dp)
                                        ) {
                                            Text(text = "🔔", fontSize = 18.sp)
                                        }
                                    }
                                    
                                    // Profile
                                    Surface(
                                        shape = CircleShape,
                                        color = LuckinBlue
                                    ) {
                                        AccountIcon(
                                            color = White,
                                            filled = true,
                                            size = 24.dp,
                                            modifier = Modifier.padding(10.dp)
                                        )
                                    }
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        // Quick action cards with staggered animation
                        var actionsVisible by remember { mutableStateOf(false) }
                        LaunchedEffect(Unit) {
                            delay(250)
                            actionsVisible = true
                        }
                        
                        AnimatedVisibility(
                            visible = actionsVisible,
                            enter = fadeIn(tween(400)) + slideInVertically { 30 }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                OrderNowCard(
                                    onClick = { },
                                    modifier = Modifier.weight(1f)
                                )
                                
                                LuckyDrawCard(
                                    onClick = { },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(28.dp))
                        
                        // Recommend for You section
                        SectionHeader(
                            title = "Recommend for You",
                            onMoreClick = { }
                        )
                        
                        // Products horizontal list with staggered entry
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            itemsIndexed(MockData.featuredProducts) { index, product ->
                                AnimatedProductCard(
                                    product = product,
                                    onClick = { showProductPopup = product },
                                    animationDelay = index * 80
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(28.dp))
                        
                        // Best Sellers section
                        SectionHeader(
                            title = "Best Sellers 🔥",
                            onMoreClick = { }
                        )
                        
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            itemsIndexed(MockData.featuredProducts.shuffled()) { index, product ->
                                AnimatedProductCard(
                                    product = product,
                                    onClick = { showProductPopup = product },
                                    animationDelay = index * 80 + 150
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(28.dp))
                        
                        // New Arrivals section
                        SectionHeader(
                            title = "New Arrivals ✨",
                            onMoreClick = { }
                        )
                        
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            itemsIndexed(MockData.featuredProducts.filter { it.isNew }) { index, product ->
                                AnimatedProductCard(
                                    product = product,
                                    onClick = { showProductPopup = product },
                                    animationDelay = index * 80 + 300
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }
        }
        
        // Product detail popup
        showProductPopup?.let { product ->
            ProductDetailPopup(
                product = product,
                onDismiss = { showProductPopup = null },
                onAddToCart = { p, qty ->
                    toastMessage = "${qty}x ${p.name} added to cart!"
                    showToast = true
                    showProductPopup = null
                }
            )
        }
        
        // Toast notification
        AnimatedVisibility(
            visible = showToast,
            enter = fadeIn() + slideInVertically { -it },
            exit = fadeOut() + slideOutVertically { -it },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 48.dp)
        ) {
            LaunchedEffect(showToast) {
                if (showToast) {
                    delay(3000)
                    showToast = false
                }
            }
            
            LuckinToast(
                message = toastMessage,
                icon = "🛒",
                type = ToastType.SUCCESS,
                onDismiss = { showToast = false }
            )
        }
    }
}
