package com.example.littlelemon

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items // Use simpler 'items'
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemoncapstone.DishDetails
import com.example.littlelemoncapstone.ui.theme.LittleLemonColor
import kotlin.text.replaceFirstChar
import kotlin.text.uppercase

@Composable
fun LowerPanel(navController: NavController, dishes: List<MenuItemRoom> = listOf()) {
    Column {
        OrderForDeliveryCard(navController,dishes)

    }
}

@Composable
fun CategoryList(categories: List<String>, selectedCategory: String?, onCategoryClick: (String)->Unit) {
    Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
    ) {
        Text(
            text = "ORDER FOR DELIVERY",
            style = MaterialTheme.typography.headlineSmall
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            contentPadding = PaddingValues(end = 16.dp) // Allows last item to not be cut off
        ) {
            items(categories) { category ->
                Button(
                    onClick = { onCategoryClick(category) },
                    modifier = Modifier.padding(end = 10.dp), // Space between buttons
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        // Use Yellow for selected, Gray for unselected
                        containerColor = if (selectedCategory == category) {
                            LittleLemonColor.yellow
                        } else {
                            LittleLemonColor.lightGray
                        }
                    )
                ) {
                    Text(
                        text = category.replaceFirstChar { it.uppercase() },
                        color = if (selectedCategory == category) {
                            LittleLemonColor.darkGray
                        } else {
                            LittleLemonColor.charcoal
                        }
                    )
                }
            }

        }

}
}


@Composable
fun OrderForDeliveryCard(navController: NavController, allDishes: List<MenuItemRoom>) {
    val categories = allDishes.map { it.category }.distinct()
    // 1. STATE FOR THE SELECTED CATEGORY
    var selectedCategory: String? by remember { mutableStateOf<String?>(null) }

    val filteredDishes = if (selectedCategory != null) {
        allDishes.filter { it.category == selectedCategory }
    } else {
        allDishes
    }
    Column(Modifier.background(LittleLemonColor.white)) {
        // 2. PASS the data to a new CategoryList composable
        CategoryList(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategoryClick = { category ->
               selectedCategory = if (selectedCategory == category) null else category
            })
    }
    Divider(
        modifier = Modifier.padding(top = 8.dp, end = 8.dp),
        thickness = 2.dp,
        color = LittleLemonColor.lightGray
    )
        LazyColumn {
            items(items = filteredDishes) { dish ->
                MenuDish(navController, dish)
            }
        }
    }


// --- FIX THE PREVIEW ---


@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun MenuDish(navController: NavController? = null, dish: MenuItemRoom) {
    Card(
        onClick = {
            Log.d("AAA", "Click ${dish.id}")
            navController?.navigate(DishDetails.createRoute(dish.id))
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically // Aligns items in the center of the row
        ) {
            Column(
                modifier = Modifier
                    .weight(1f) // Makes the column expand to fill available space
                    .padding(end = 8.dp) // Add padding to separate from the image
            ) {
                Text(
                    text = dish.title,
                    style = MaterialTheme.typography.titleLarge // A more appropriate style
                )
                Text(
                    text = dish.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = "$${dish.price}",
                    style = MaterialTheme.typography.labelLarge // A more appropriate style
                )
            }
            // The 'alignment' parameter does not exist on Image.
            // Alignment is controlled by the parent Row.
            GlideImage(
                model = dish.imageUrl,
                contentDescription = "${dish.title} image",
                modifier = Modifier.size(90.dp),
                contentScale = ContentScale.Crop // Crop is usually best for fixed-size image views
            )
        }
    }
    Divider(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        thickness = 2.dp,
        // Use a theme-aware color for better light/dark mode support
        color = LittleLemonColor.yellow
    )
}
