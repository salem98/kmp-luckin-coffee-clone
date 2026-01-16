package com.luckin.clone.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.luckin.clone.data.MockData
import com.luckin.clone.data.model.CartState
import com.luckin.clone.data.model.Category
import com.luckin.clone.data.model.Product
import com.luckin.clone.ui.components.*
import com.luckin.clone.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun MenuScreen(
    cartState: CartState = MockData.sampleCart,
    onProductClick: (String) -> Unit = {},
    onCartClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf(MockData.categories.first()) }
    var showProductPopup by remember { mutableStateOf<Product?>(null) }
    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }
    var localCartState by remember { mutableStateOf(cartState) }
    
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .statusBarsPadding()
        ) {
            // Location picker header with entrance animation
            var headerVisible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                delay(50)
                headerVisible = true
            }
            
            AnimatedVisibility(
                visible = headerVisible,
                enter = fadeIn(tween(300)) + slideInVertically { -20 }
            ) {
                LocationPicker(
                    storeName = MockData.currentStore.name,
                    distance = MockData.currentStore.distance,
                    isOpen = MockData.currentStore.isOpen,
                    onClick = { }
                )
            }
            
            HorizontalDivider(color = DividerGray, thickness = 1.dp)
            
            // Main content: scrollable sidebar + products
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                // Scrollable Category sidebar with LazyColumn
                ScrollableCategorySidebar(
                    categories = MockData.categories,
                    selectedCategoryId = selectedCategory.id,
                    onCategorySelected = { selectedCategory = it }
                )
                
                // Category content with transition
                AnimatedContent(
                    targetState = selectedCategory,
                    transitionSpec = {
                        fadeIn(tween(300)) + slideInVertically { 20 } togetherWith
                        fadeOut(tween(200))
                    },
                    modifier = Modifier.weight(1f)
                ) { category ->
                    CategoryContent(
                        category = category,
                        onProductClick = { productId ->
                            val product = MockData.featuredProducts.find { it.id == productId }
                            product?.let { showProductPopup = it }
                        }
                    )
                }
            }
        }
        
        // Animated floating cart bar
        AnimatedVisibility(
            visible = !localCartState.isEmpty,
            enter = fadeIn() + slideInVertically { it },
            exit = fadeOut() + slideOutVertically { it },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp)
        ) {
            AnimatedFloatingCartBar(
                cartState = localCartState,
                onCartClick = onCartClick
            )
        }
        
        // Product detail popup
        showProductPopup?.let { product ->
            ProductDetailPopup(
                product = product,
                onDismiss = { showProductPopup = null },
                onAddToCart = { p, qty ->
                    // Update local cart state
                    val newTotal = localCartState.totalDiscountedPrice + (p.discountedPrice * qty)
                    val newOriginal = localCartState.totalOriginalPrice + (p.originalPrice * qty)
                    localCartState = localCartState.copy(
                        items = localCartState.items + com.luckin.clone.data.model.CartItem(p, qty),
                        totalDiscountedPrice = newTotal,
                        totalOriginalPrice = newOriginal,
                        totalDiscount = ((1 - newTotal / newOriginal) * 100).toInt()
                    )
                    
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

@Composable
private fun AnimatedFloatingCartBar(
    cartState: CartState,
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    
    // Subtle bounce animation
    val translateY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -4f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    FloatingCartBar(
        cartState = cartState,
        onCartClick = onCartClick,
        modifier = modifier.graphicsLayer { translationY = translateY }
    )
}

@Composable
private fun CategoryContent(
    category: Category,
    onProductClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val products = MockData.featuredProducts.filter { 
        it.category == category.id || category.id in listOf("best_sellers", "today_deals", "new_arrivals", "luckin_day")
    }.ifEmpty { MockData.featuredProducts }
    
    var contentLoaded by remember { mutableStateOf(false) }
    
    LaunchedEffect(category.id) {
        contentLoaded = false
        delay(150)
        contentLoaded = true
    }
    
    LazyColumn(
        modifier = modifier
            .fillMaxHeight()
            .background(Background)
            .padding(horizontal = 12.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section title with animation
        item {
            AnimatedVisibility(
                visible = contentLoaded,
                enter = fadeIn(tween(300)) + slideInVertically { -10 }
            ) {
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = TextPrimary
                )
            }
        }
        
        // Promo banner
        item {
            AnimatedVisibility(
                visible = contentLoaded,
                enter = fadeIn(tween(400, delayMillis = 100)) + slideInVertically { 20 }
            ) {
                val banner = when (category.id) {
                    "luckin_day" -> MockData.banners[1]
                    "coconut" -> MockData.banners[0]
                    else -> MockData.banners[2]
                }
                
                PromoBanner(
                    banner = banner,
                    onClick = { }
                )
            }
        }
        
        // Products grid with staggered animation
        itemsIndexed(products.chunked(2)) { rowIndex, rowProducts ->
            AnimatedVisibility(
                visible = contentLoaded,
                enter = fadeIn(tween(300, delayMillis = 200 + rowIndex * 80)) + 
                        slideInVertically { 30 }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowProducts.forEachIndexed { colIndex, product ->
                        AnimatedProductCard(
                            product = product,
                            onClick = { onProductClick(product.id) },
                            modifier = Modifier.weight(1f),
                            animationDelay = rowIndex * 80 + colIndex * 40
                        )
                    }
                    
                    if (rowProducts.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
        
        // Bottom spacing
        item {
            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}
