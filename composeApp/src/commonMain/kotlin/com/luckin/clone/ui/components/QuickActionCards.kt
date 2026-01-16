package com.luckin.clone.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.luckin.clone.ui.theme.*

@Composable
fun QuickActionCard(
    title: String,
    emoji: String,
    gradient: Brush,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(100.dp),
        shape = BannerShape,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(16.dp)
        ) {
            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            
            // Emoji decoration
            Text(
                text = emoji,
                fontSize = 40.sp,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}

@Composable
fun OrderNowCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    QuickActionCard(
        title = "Order\nNow",
        emoji = "📱☕",
        gradient = LuckinBlueGradient,
        onClick = onClick,
        modifier = modifier
    )
}

@Composable
fun LuckyDrawCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    QuickActionCard(
        title = "Lucky\nDraw",
        emoji = "🥥🌴",
        gradient = Brush.linearGradient(
            listOf(
                Color(0xFFFFF8E1),
                Color(0xFFFFECB3),
                Color(0xFFFFE082)
            )
        ),
        onClick = onClick,
        modifier = modifier
    )
}

@Composable
fun LocationPicker(
    storeName: String,
    distance: String,
    isOpen: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Pickup",
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                
                // Search icon placeholder
                Surface(
                    shape = SearchBarShape,
                    color = SurfaceGray,
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🔍",
                            fontSize = 16.sp
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Status badge
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = if (isOpen) LuckinGreen else StatusClosed
                ) {
                    Text(
                        text = if (isOpen) "OPEN" else "CLOSED",
                        style = MaterialTheme.typography.labelSmall,
                        color = White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                
                Text(
                    text = storeName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.Medium
                )
                
                Text(
                    text = "▼",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiary
                )
            }
            
            Text(
                text = distance,
                style = MaterialTheme.typography.bodySmall,
                color = TextTertiary
            )
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    onMoreClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = TextPrimary,
            fontWeight = FontWeight.Bold
        )
        
        onMoreClick?.let {
            Text(
                text = "More",
                style = MaterialTheme.typography.bodyMedium,
                color = TextTertiary,
                modifier = Modifier.clickable(onClick = it)
            )
        }
    }
}
