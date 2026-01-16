package com.luckin.clone.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.luckin.clone.data.model.CartState
import com.luckin.clone.ui.theme.*
import com.luckin.clone.util.formatPrice

@Composable
fun FloatingCartBar(
    cartState: CartState,
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (cartState.isEmpty) return
    
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = FloatingCartShape,
        color = LuckinBlue,
        shadowElevation = 8.dp,
        onClick = onCartClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left side - Cart icon and item count
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Cart icon with badge
                Box {
                    Text(
                        text = "🛒",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                
                Text(
                    text = "${cartState.itemCount} item",
                    style = MaterialTheme.typography.titleMedium,
                    color = White,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            // Right side - Prices
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Original price (strikethrough)
                Text(
                    text = "\$${cartState.totalOriginalPrice.formatPrice()}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = White.copy(alpha = 0.6f),
                    textDecoration = TextDecoration.LineThrough
                )
                
                // Discounted price
                Text(
                    text = "\$ ${cartState.totalDiscountedPrice.formatPrice()}",
                    style = MaterialTheme.typography.titleLarge,
                    color = White,
                    fontWeight = FontWeight.Bold
                )
                
                // Discount badge
                if (cartState.totalDiscount > 0) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = LuckinOrange
                    ) {
                        Text(
                            text = "${cartState.totalDiscount}% OFF",
                            style = MaterialTheme.typography.labelSmall,
                            color = White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}
