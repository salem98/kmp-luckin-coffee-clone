package com.luckin.clone.data

import com.luckin.clone.data.model.*

object MockData {
    
    val currentStore = Store(
        id = "1",
        name = "Pasir Ris Mall",
        address = "1 Pasir Ris Central St 3",
        distance = "1.1km away",
        isOpen = false
    )
    
    val categories = listOf(
        Category(
            id = "luckin_day",
            name = "Luckin Day",
            icon = "📅",
            badge = "2@\$7.99"
        ),
        Category(
            id = "coconut",
            name = "Coconut Series",
            icon = "🥥",
            badge = "NEW"
        ),
        Category(
            id = "best_sellers",
            name = "Best Sellers",
            icon = "🔥"
        ),
        Category(
            id = "today_deals",
            name = "Today's Deals",
            icon = "🏷️",
            badge = "SALE"
        ),
        Category(
            id = "new_arrivals",
            name = "New Arrivals",
            icon = "✨",
            badge = "NEW"
        ),
        Category(
            id = "signature",
            name = "Signature",
            icon = "☕"
        ),
        Category(
            id = "latte",
            name = "Latte",
            icon = "🧋"
        ),
        Category(
            id = "americano",
            name = "Americano",
            icon = "☕"
        ),
        Category(
            id = "tea",
            name = "Tea Series",
            icon = "🍵"
        ),
        Category(
            id = "snacks",
            name = "Snacks",
            icon = "🍪"
        )
    )
    
    val featuredProducts = listOf(
        Product(
            id = "1",
            name = "Blue-ming Coconut Latte",
            originalPrice = 8.50,
            discountedPrice = 6.80,
            discountPercent = 20,
            isNew = true,
            nutriGrade = "A",
            category = "coconut"
        ),
        Product(
            id = "2",
            name = "Blue-ming Coconut Frappe",
            originalPrice = 8.50,
            discountedPrice = 6.80,
            discountPercent = 20,
            isNew = true,
            nutriGrade = "B",
            category = "coconut"
        ),
        Product(
            id = "3",
            name = "Coconut Cloud Latte",
            originalPrice = 7.90,
            discountedPrice = 6.32,
            discountPercent = 20,
            isNew = false,
            nutriGrade = "A",
            category = "coconut"
        ),
        Product(
            id = "4",
            name = "Raw Coconut Latte",
            originalPrice = 7.50,
            discountedPrice = 6.00,
            discountPercent = 20,
            isNew = false,
            nutriGrade = "B",
            category = "coconut"
        ),
        Product(
            id = "5",
            name = "Classic Latte",
            originalPrice = 6.50,
            discountedPrice = 5.20,
            discountPercent = 20,
            category = "latte"
        ),
        Product(
            id = "6",
            name = "Vanilla Latte",
            originalPrice = 7.00,
            discountedPrice = 5.60,
            discountPercent = 20,
            category = "latte"
        )
    )
    
    val banners = listOf(
        Banner(
            id = "1",
            title = "BLUE-MING NEW YEAR",
            subtitle = "Blue-ming Coconut Latte & Frappe",
            buttonText = "Order Now >",
            backgroundColor = 0xFF87CEEB
        ),
        Banner(
            id = "2",
            title = "LUCKIN DAY",
            subtitle = "\$7.99 FOR 2 CUPS",
            buttonText = "Order Now >",
            backgroundColor = 0xFF4A90D9
        ),
        Banner(
            id = "3",
            title = "New Picks Combo",
            subtitle = "35% OFF",
            buttonText = "Order Now >",
            backgroundColor = 0xFF80CBC4
        )
    )
    
    val sampleCart = CartState(
        items = listOf(
            CartItem(featuredProducts[0], 1)
        ),
        totalOriginalPrice = 17.00,
        totalDiscountedPrice = 11.05,
        totalDiscount = 35
    )
}
