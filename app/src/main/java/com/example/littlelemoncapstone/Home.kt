package com.example.littlelemoncapstone

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.isEmpty
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import androidx.room.Room
import com.example.littlelemon.AppDatabase
import com.example.littlelemon.LowerPanel
import com.example.littlelemon.MenuItemNetwork
import com.example.littlelemon.MenuItemRoom
import com.example.littlelemon.MenuNetwork
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val httpClient = HttpClient(Android) {
    install(ContentNegotiation) {
        json(contentType = ContentType("text", "plain"))
    }
}


@Composable
fun Home(navController: NavController) {

    // FIX 1: Correctly and safely get a single database instance.
    val applicationContext = LocalContext.current.applicationContext
    val database = remember {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database").build()
    }
    val databaseMenuItems by database.menuItemDao().getAll().observeAsState(emptyList())
    var searchQuery by remember { mutableStateOf("") }
    var orderMenuItems by remember {
        mutableStateOf(false)
    }

    // add menuItems variable here
    var menuItems = if (orderMenuItems) {
        databaseMenuItems.sortedBy { it.title }
    } else {
        databaseMenuItems
    }


    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) { // Switch to a background thread
            // Check if the database is empty.
            if (database.menuItemDao().isEmpty()) {
                try {
                    val menuItemsNetwork = fetchMenu()
                    saveMenuToDatabase(menuItemsNetwork, database)
                } catch (e: Exception) {
                    // Log the actual error to see what's wrong with Ktor/JSON parsing
                    e.printStackTrace()
                }
            }
        }
    }


    Column {

        TopAppBar(navController)
        UpperPanel( searchQuery,
            { newQuery ->
                searchQuery = newQuery
            })
        val menuItems by remember(searchQuery, databaseMenuItems) {
            derivedStateOf {
                if (searchQuery.isNotBlank()) {
                    databaseMenuItems.filter { menuItem ->
                        menuItem.title.contains(searchQuery, ignoreCase = true)
                    }
                } else {
                    databaseMenuItems
                }
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            LowerPanel(navController, menuItems)
        }

    }
}


suspend fun fetchMenu(): List<MenuItemNetwork> {

    val response: MenuNetwork = httpClient
        .get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
        .body<MenuNetwork>()
    return response.menu
}

fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>, database: AppDatabase) {
    val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
    database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
}


@Composable
fun MenuItemsList(items: List<MenuItemRoom>) {

}

@Preview(showBackground = false)
@Composable
fun PreviewHome() {
    Home(rememberNavController())
}