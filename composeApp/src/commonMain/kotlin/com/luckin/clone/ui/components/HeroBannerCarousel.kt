package com.luckin.clone.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.luckin.clone.data.model.Banner
import com.luckin.clone.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

/**
 * Swipeable Hero Banner Carousel using HorizontalPager
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeroBannerCarousel(
    banners: List<Banner>,
    onBannerClick: (Banner) -> Unit,
    modifier: Modifier = Modifier,
    autoScrollInterval: Long = 4000L
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { banners.size }
    )
    
    // Auto-scroll effect
    LaunchedEffect(pagerState) {
        while (true) {
            delay(autoScrollInterval)
            val nextPage = (pagerState.currentPage + 1) % banners.size
            pagerState.animateScrollToPage(
                page = nextPage,
                animationSpec = tween(500, easing = FastOutSlowInEasing)
            )
        }
    }
    
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Pager with swipe gestures
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageSpacing = 12.dp,
            beyondViewportPageCount = 1
        ) { page ->
            val banner = banners[page]
            
            // Calculate page offset for animations
            val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
            
            CarouselBannerCard(
                banner = banner,
                pageOffset = pageOffset,
                onClick = { onBannerClick(banner) }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Animated page indicators
        PagerIndicator(
            pagerState = pagerState,
            pageCount = banners.size
        )
    }
}

@Composable
private fun CarouselBannerCard(
    banner: Banner,
    pageOffset: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Smooth scale animation based on page position
    val scale = lerp(
        start = 0.9f,
        stop = 1f,
        fraction = 1f - pageOffset.coerceIn(0f, 1f)
    )
    
    // Fade effect for non-centered pages
    val alpha = lerp(
        start = 0.5f,
        stop = 1f,
        fraction = 1f - pageOffset.coerceIn(0f, 1f)
    )
    
    Card(
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (pageOffset < 0.5f) 12.dp else 4.dp
        ),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = when (banner.id) {
                        "promo_1" -> LuckinBlueGradient
                        "promo_2" -> CoconutGradient
                        else -> SunriseGradient
                    }
                )
        ) {
            // Animated floating elements overlay
            FloatingAnimations(
                modifier = Modifier.fillMaxSize(),
                isActive = pageOffset < 0.5f
            )
            
            // Background pattern overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.1f),
                                Color.Transparent
                            )
                        )
                    )
            )
            
            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    // Subtitle chip
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = White.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = banner.subtitle,
                            style = MaterialTheme.typography.labelMedium,
                            color = White,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Main title
                    Text(
                        text = banner.title,
                        style = MaterialTheme.typography.displayMedium,
                        color = White,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 36.sp
                    )
                }
                
                // CTA Button
                Button(
                    onClick = onClick,
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White
                    ),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = banner.buttonText,
                        style = MaterialTheme.typography.labelLarge,
                        color = LuckinBlue,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            
            // Animated coffee cup with steam
            AnimatedCoffeeCup(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .size(90.dp)
            )
        }
    }
}

/**
 * Floating animated elements - sparkles and coffee beans
 */
@Composable
private fun FloatingAnimations(
    modifier: Modifier = Modifier,
    isActive: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition()
    
    // Pre-calculate all animations outside Canvas (composable context)
    val anim1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val anim2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, delayMillis = 300, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val anim3 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, delayMillis = 600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val anim4 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, delayMillis = 150, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val anim5 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, delayMillis = 450, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val anim6 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, delayMillis = 750, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    val animValues = listOf(anim1, anim2, anim3, anim4, anim5, anim6)
    val particles = listOf(
        0.1f to 0.2f,
        0.85f to 0.15f,
        0.7f to 0.7f,
        0.15f to 0.65f,
        0.9f to 0.5f,
        0.3f to 0.35f
    )
    
    Canvas(modifier = modifier) {
        if (!isActive) return@Canvas
        
        particles.forEachIndexed { index, (xFraction, yFraction) ->
            val animValue = animValues[index]
            val x = size.width * xFraction
            val y = size.height * yFraction + (animValue * 20f - 10f)
            val sparkleAlpha = 0.3f + (animValue * 0.4f)
            
            // Draw sparkle/star
            if (index % 2 == 0) {
                // Star sparkle
                drawCircle(
                    color = Color.White.copy(alpha = sparkleAlpha),
                    radius = 4f + (animValue * 3f),
                    center = Offset(x, y)
                )
                // Cross sparkle effect
                drawLine(
                    color = Color.White.copy(alpha = sparkleAlpha * 0.7f),
                    start = Offset(x - 8f, y),
                    end = Offset(x + 8f, y),
                    strokeWidth = 1.5f
                )
                drawLine(
                    color = Color.White.copy(alpha = sparkleAlpha * 0.7f),
                    start = Offset(x, y - 8f),
                    end = Offset(x, y + 8f),
                    strokeWidth = 1.5f
                )
            } else {
                // Coffee bean shape
                val beanPath = Path().apply {
                    moveTo(x, y - 6f)
                    quadraticTo(x + 8f, y, x, y + 6f)
                    quadraticTo(x - 8f, y, x, y - 6f)
                    close()
                }
                drawPath(
                    path = beanPath,
                    color = Color(0xFF5D4037).copy(alpha = sparkleAlpha * 0.6f)
                )
                // Center line of bean
                drawLine(
                    color = Color(0xFF3E2723).copy(alpha = sparkleAlpha * 0.4f),
                    start = Offset(x, y - 4f),
                    end = Offset(x, y + 4f),
                    strokeWidth = 1f
                )
            }
        }
    }
}

/**
 * Animated coffee cup with floating steam
 */
@Composable
private fun AnimatedCoffeeCup(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    
    // Cup subtle rotation
    val cupRotation by infiniteTransition.animateFloat(
        initialValue = -15f,
        targetValue = -10f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    // Steam animations
    val steam1Y by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -20f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    
    val steam2Y by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -25f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, delayMillis = 300, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    
    val steam3Y by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -18f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, delayMillis = 600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    
    val steamOpacity1 by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    
    val steamOpacity2 by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, delayMillis = 300, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    
    val steamOpacity3 by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, delayMillis = 600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    
    Canvas(
        modifier = modifier.graphicsLayer { rotationZ = cupRotation }
    ) {
        val centerX = size.width / 2
        val cupTop = size.height * 0.35f
        val cupWidth = size.width * 0.6f
        val cupHeight = size.height * 0.5f
        
        // Draw steam lines
        val steamX = centerX
        drawLine(
            color = Color.White.copy(alpha = steamOpacity1),
            start = Offset(steamX - 10f, cupTop + steam1Y),
            end = Offset(steamX - 10f, cupTop - 15f + steam1Y),
            strokeWidth = 3f,
            cap = StrokeCap.Round
        )
        drawLine(
            color = Color.White.copy(alpha = steamOpacity2),
            start = Offset(steamX, cupTop + steam2Y - 5f),
            end = Offset(steamX, cupTop - 20f + steam2Y),
            strokeWidth = 3f,
            cap = StrokeCap.Round
        )
        drawLine(
            color = Color.White.copy(alpha = steamOpacity3),
            start = Offset(steamX + 10f, cupTop + steam3Y),
            end = Offset(steamX + 10f, cupTop - 12f + steam3Y),
            strokeWidth = 3f,
            cap = StrokeCap.Round
        )
        
        // Draw cup body
        drawRoundRect(
            color = Color.White.copy(alpha = 0.3f),
            topLeft = Offset(centerX - cupWidth / 2, cupTop),
            size = Size(cupWidth, cupHeight),
            cornerRadius = CornerRadius(12f, 12f)
        )
        
        // Draw handle
        drawArc(
            color = Color.White.copy(alpha = 0.3f),
            startAngle = -60f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(centerX + cupWidth / 2 - 5f, cupTop + cupHeight * 0.2f),
            size = Size(cupWidth * 0.4f, cupHeight * 0.5f),
            style = Stroke(width = 5f)
        )
        
        // Coffee inside
        drawRoundRect(
            color = Color(0xFF5D4037).copy(alpha = 0.4f),
            topLeft = Offset(centerX - cupWidth / 2 + 6f, cupTop + cupHeight * 0.25f),
            size = Size(cupWidth - 12f, cupHeight * 0.6f),
            cornerRadius = CornerRadius(8f, 8f)
        )
    }
}

private data class ParticleData(
    val xFraction: Float,
    val yFraction: Float,
    val duration: Int,
    val delay: Int
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PagerIndicator(
    pagerState: PagerState,
    pageCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            val isSelected = pagerState.currentPage == index
            
            // Animated width for selected indicator
            val width by animateDpAsState(
                targetValue = if (isSelected) 24.dp else 8.dp,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
            
            // Animated color
            val color by animateColorAsState(
                targetValue = if (isSelected) LuckinBlue else DividerGray,
                animationSpec = tween(300)
            )
            
            Box(
                modifier = Modifier
                    .width(width)
                    .height(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}
