package com.luckin.clone.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luckin.clone.data.model.Banner
import com.luckin.clone.ui.theme.*

@Composable
fun PromoBanner(
    banner: Banner,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = BannerShape,
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            Color(banner.backgroundColor),
                            Color(banner.backgroundColor).copy(alpha = 0.7f),
                            Color(0xFFE0F4FF)
                        )
                    )
                )
        ) {
            // Content
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(20.dp)
            ) {
                Text(
                    text = banner.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = LuckinBlueDark,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = banner.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Order Now button
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = LuckinBlue,
                    onClick = onClick
                ) {
                    Text(
                        text = banner.buttonText,
                        style = MaterialTheme.typography.labelLarge,
                        color = White,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
            
            // Decorative coffee cups emoji on right
            Text(
                text = "☕🧋",
                fontSize = 48.sp,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 20.dp)
            )
        }
    }
}

@Composable
fun HeroBanner(
    banner: Banner,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp),
        shape = RoundedCornerShape(0.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0xFF87CEEB),
                            Color(0xFFB0E0E6),
                            Color(0xFFE0F4FF)
                        )
                    )
                )
        ) {
            // Main content
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(24.dp)
            ) {
                Text(
                    text = banner.title,
                    style = MaterialTheme.typography.displayMedium,
                    color = LuckinBlueDark,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 36.sp
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Product tags
                    NutriProductTag(name = "Blue-ming\nCoconut Latte", grade = "A")
                    NutriProductTag(name = "Blue-ming\nCoconut Frappe", grade = "B")
                }
            }
            
            // Order Now button
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp),
                shape = RoundedCornerShape(20.dp),
                color = LuckinBlue,
                onClick = onClick
            ) {
                Text(
                    text = "Order Now >",
                    style = MaterialTheme.typography.labelLarge,
                    color = White,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            
            // Decorative cup
            Text(
                text = "🥤",
                fontSize = 80.sp,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
            )
        }
    }
}

@Composable
private fun NutriProductTag(
    name: String,
    grade: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall,
            color = TextSecondary
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            listOf("A", "B", "C", "D").forEach { g ->
                val isActive = g == grade
                Surface(
                    shape = RoundedCornerShape(2.dp),
                    color = when {
                        isActive && g == "A" -> Color(0xFF4CAF50)
                        isActive && g == "B" -> Color(0xFF8BC34A)
                        isActive && g == "C" -> Color(0xFFFFEB3B)
                        isActive && g == "D" -> Color(0xFFFF9800)
                        else -> Color(0xFFE0E0E0)
                    },
                    modifier = Modifier.size(16.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = g,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isActive && g in listOf("A", "B")) White else TextPrimary,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
