package com.example.littlelemon

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // Use simpler 'items'
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.ui.theme.LittleLemonColor
import com.example.littlelemon.ui.theme.LittleLemonTheme // Import the theme for the preview

@Composable
fun LowerPanel(navController: NavHostController, dishes: List<Dish> = listOf()) {
    Column {
        WeeklySpecialCard()
        LazyColumn {
            items(items = dishes) { dish ->
                MenuDish(navController, dish)
            }
        }
    }
}

@Composable
fun WeeklySpecialCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Let the theme's style handle all text properties for consistency.
        Text(
            text = stringResource(R.string.weekly_special),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(16.dp) // Increased padding for better visuals
        )
    }
}

// --- FIX THE PREVIEW ---
@Composable
@Preview(showBackground = true) // Set a background to see component bounds
fun LowerPanelPreview() { // Renamed for clarity
    // Previews for Material components MUST be wrapped in your app's theme.
    LittleLemonTheme {
        LowerPanel(rememberNavController(), DishRepository.dishes)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuDish(navController: NavHostController? = null, dish: Dish) {
    Card(
        onClick = {
            Log.d("AAA", "Click ${dish.id}")
            navController?.navigate(DishDetails.route + "/${dish.id}")
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
                    text = dish.name,
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
            Image(
                painter = painterResource(id = dish.imageResource),
                contentDescription = "${dish.name} image",
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
