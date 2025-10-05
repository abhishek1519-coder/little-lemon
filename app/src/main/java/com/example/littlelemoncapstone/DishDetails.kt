package com.example.littlelemoncapstone


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.AppDatabase
import com.example.littlelemon.MenuItemRoom
import com.example.littlelemoncapstone.ui.theme.LittleLemonColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun DishDetails(id: Int,navController: NavController) {
    val applicationContext = LocalContext.current.applicationContext
    val database = remember {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database").build()
    }
    val dishState = produceState<MenuItemRoom?>(initialValue = null, id) {
        // The producer block is a coroutine scope.
        // We switch to the IO dispatcher for database access.
        value = withContext(Dispatchers.IO) {
            database.menuItemDao().getMenuItemById(id)
        }
    }

    val dish = dishState.value
    DishContent(navController,dish);
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DishContent(navController: NavController, dish: MenuItemRoom?) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.background(LittleLemonColor.white )) {
        TopAppBar(navController)
        if (dish!=null) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(8.dp) // Add some padding to the column
            ) {
                // 1. Add the Dish Image
                GlideImage(
                    model = dish.imageUrl,
                    contentDescription = dish.title,
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    contentScale = ContentScale.Crop
                )

                // 2. Add the Dish Name
                Text(
                    text = dish.title,
                    style = MaterialTheme.typography.headlineLarge
                )

                // 3. Add the Dish Description
                Text(
                    text = dish.description,
                    style = MaterialTheme.typography.bodyLarge
                )

                // 4. Add the Counter
                Counter()

                // 5. Add the "Add to cart" button
                Button(
                    onClick = { /* TODO: Add to cart logic */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LittleLemonColor.yellow
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.add_for) + " $${dish.price}",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun Counter() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        var counter by remember {
            mutableStateOf(1)
        }
        TextButton(
            onClick = {
                counter--
            }
        ) {
            Text(
                text = "-",
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Text(
            text = counter.toString(),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(16.dp)
        )
        TextButton(
            onClick = {
                counter++
            }
        ) {
            Text(
                text = "+",
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@Preview
@Composable
fun DishDetailsPreview() {
    DishDetails(id = 3, rememberNavController())
}
