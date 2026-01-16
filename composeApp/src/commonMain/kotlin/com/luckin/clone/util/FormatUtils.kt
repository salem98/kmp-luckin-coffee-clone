package com.luckin.clone.util

import kotlin.math.roundToInt

/**
 * Multiplatform utility functions
 */

/**
 * Format a Double to a price string with 2 decimal places
 * Works on all platforms (JVM, JS, Native, WASM)
 */
fun Double.formatPrice(): String {
    val rounded = (this * 100).roundToInt() / 100.0
    val intPart = rounded.toInt()
    val decPart = ((rounded - intPart) * 100).roundToInt()
    return "$intPart.${decPart.toString().padStart(2, '0')}"
}

/**
 * Format a Double to currency display
 */
fun Double.toCurrency(): String = "\$${formatPrice()}"
