package com.example.littlelemon

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuNetwork(
    @SerialName("menu")
    val menu: List<MenuItemNetwork>,
) {
    operator fun get(s: String): List<MenuItemNetwork> {
        return menu
    }
}

@Serializable
data class MenuItemNetwork(
    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("price")
    val price: Double,

    @SerialName("description")
    val description: String,

    @SerialName("category")
    val category: String,

    @SerialName("image")
    val image: String

    ) {
    fun toMenuItemRoom() = MenuItemRoom(
        // add code here
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        imageUrl = image
    )
}
