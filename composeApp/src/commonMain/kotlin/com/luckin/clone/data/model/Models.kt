package com.luckin.clone.data.model

data class Product(
    val id: String,
    val name: String,
    val description: String = "",
    val originalPrice: Double,
    val discountedPrice: Double,
    val discountPercent: Int = 0,
    val imageUrl: String = "",
    val isNew: Boolean = false,
    val nutriGrade: String? = null,
    val category: String = ""
)

data class Category(
    val id: String,
    val name: String,
    val icon: String,
    val badge: String? = null,
    val products: List<Product> = emptyList()
)

data class Banner(
    val id: String,
    val title: String,
    val subtitle: String = "",
    val buttonText: String = "Order Now >",
    val imageUrl: String = "",
    val backgroundColor: Long = 0xFF87CEEB
)

data class Store(
    val id: String,
    val name: String,
    val address: String,
    val distance: String,
    val isOpen: Boolean = true
)

data class CartItem(
    val product: Product,
    val quantity: Int = 1
)

data class CartState(
    val items: List<CartItem> = emptyList(),
    val totalOriginalPrice: Double = 0.0,
    val totalDiscountedPrice: Double = 0.0,
    val totalDiscount: Int = 0
) {
    val itemCount: Int get() = items.sumOf { it.quantity }
    val isEmpty: Boolean get() = items.isEmpty()
}
