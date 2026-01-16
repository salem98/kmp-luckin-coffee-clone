package com.luckin.clone.ui.components

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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.luckin.clone.ui.theme.*
import kotlinx.coroutines.delay

/**
 * Consistent screen wrapper with proper system bar handling
 * Use this for all screens to ensure consistent layout and system UI handling
 */
@Composable
fun ScreenScaffold(
    modifier: Modifier = Modifier,
    backgroundColor: androidx.compose.ui.graphics.Color = Background,
    applyStatusBarPadding: Boolean = true,
    header: @Composable (() -> Unit)? = null,
    floatingContent: @Composable (BoxScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (applyStatusBarPadding) Modifier.statusBarsPadding() 
                    else Modifier
                )
        ) {
            header?.invoke()
            content()
        }
        
        floatingContent?.invoke(this)
    }
}

/**
 * Consistent screen header component
 */
@Composable
fun ScreenHeader(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = White,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Text(text = "←", style = MaterialTheme.typography.titleLarge)
                }
            }
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                subtitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                content = actions
            )
        }
    }
}

/**
 * Animated entrance wrapper for content sections
 */
@Composable
fun AnimatedSection(
    visible: Boolean,
    delayMillis: Int = 0,
    modifier: Modifier = Modifier,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(400, delayMillis = delayMillis)) + 
                slideInVertically(tween(400, delayMillis = delayMillis)) { 20 },
        exit = fadeOut(tween(200)),
        modifier = modifier,
        content = content
    )
}

/**
 * Standard content card wrapper
 */
@Composable
fun ContentCard(
    modifier: Modifier = Modifier,
    elevation: Dp = 2.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    if (onClick != null) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = elevation),
            onClick = onClick,
            content = content
        )
    } else {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = elevation),
            content = content
        )
    }
}

/**
 * Primary action button with Luckin styling
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: String? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = LuckinBlue,
            disabledContainerColor = DividerGray
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            icon?.let {
                Text(text = it)
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

/**
 * Secondary/outlined button
 */
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = LuckinBlue
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

/**
 * Toast state management hook
 */
@Composable
fun rememberToastState(): ToastState {
    return remember { ToastState() }
}

class ToastState {
    var isVisible by mutableStateOf(false)
        private set
    var message by mutableStateOf("")
        private set
    var type by mutableStateOf(ToastType.SUCCESS)
        private set
    var icon by mutableStateOf("✓")
        private set
    
    fun show(
        message: String,
        type: ToastType = ToastType.SUCCESS,
        icon: String = "✓"
    ) {
        this.message = message
        this.type = type
        this.icon = icon
        this.isVisible = true
    }
    
    fun dismiss() {
        isVisible = false
    }
}

/**
 * Toast container to be placed at the screen level
 */
@Composable
fun BoxScope.ToastContainer(
    toastState: ToastState,
    autoDismissMs: Long = 3000L
) {
    LaunchedEffect(toastState.isVisible) {
        if (toastState.isVisible) {
            delay(autoDismissMs)
            toastState.dismiss()
        }
    }
    
    AnimatedVisibility(
        visible = toastState.isVisible,
        enter = fadeIn() + slideInVertically { -it },
        exit = fadeOut() + slideOutVertically { -it },
        modifier = Modifier
            .align(Alignment.TopCenter)
            .statusBarsPadding()
            .padding(top = 8.dp)
    ) {
        LuckinToast(
            message = toastState.message,
            icon = toastState.icon,
            type = toastState.type,
            onDismiss = { toastState.dismiss() }
        )
    }
}
