package com.luckin.clone.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.luckin.clone.data.model.Product
import com.luckin.clone.ui.theme.*
import com.luckin.clone.util.formatPrice

@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(160.dp)
            .shadow(
                elevation = 4.dp,
                shape = ProductCardShape,
                spotColor = CardShadowColor
            ),
        shape = ProductCardShape,
        colors = CardDefaults.cardColors(containerColor = White),
        onClick = onClick
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
                // Discount badge - top left
                if (product.discountPercent > 0) {
                    DiscountBadge(
                        discountPercent = product.discountPercent,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                    )
                }
                
                // NEW badge - positioned below discount
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
                
                // Product image placeholder (coffee emoji)
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
                    // Discounted price
                    Text(
                        text = "\$${product.discountedPrice.formatPrice()}",
                        style = MaterialTheme.typography.titleMedium,
                        color = DiscountRed,
                        fontWeight = FontWeight.Bold
                    )
                    
                    // Original price (strikethrough)
                    if (product.discountPercent > 0) {
                        Text(
                            text = "\$${product.originalPrice.formatPrice()}",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextTertiary,
                            textDecoration = TextDecoration.LineThrough
                        )
                        
                        // Discount percentage
                        Text(
                            text = "${product.discountPercent}% OFF",
                            style = MaterialTheme.typography.labelSmall,
                            color = DiscountRed,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DiscountBadge(
    discountPercent: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
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

@Composable
fun NutriGradeBadge(
    grade: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Simplified nutri-grade display
        Surface(
            shape = RoundedCornerShape(2.dp),
            color = when (grade) {
                "A" -> Color(0xFF4CAF50)
                "B" -> Color(0xFF8BC34A)
                "C" -> Color(0xFFFFEB3B)
                "D" -> Color(0xFFFF9800)
                else -> Color(0xFF9E9E9E)
            }
        ) {
            Text(
                text = grade,
                style = MaterialTheme.typography.labelSmall,
                color = if (grade in listOf("A", "B")) White else TextPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
            )
        }
        
        Text(
            text = "NUTRI-GRADE",
            style = MaterialTheme.typography.labelSmall,
            color = TextTertiary
        )
    }
}
