package com.example.littlelemoncapstone

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.littlelemoncapstone.data.UserDataManager

@Composable
fun NavigationComposable(navController: NavHostController){

    val context = LocalContext.current
    val userDataManager = remember { UserDataManager(context) }

    // 2. Collect the isRegisteredFlow as state.
    // The initial value is important to avoid a null state before the flow emits.
    val isRegistered by userDataManager.isRegisteredFlow.collectAsState(initial = false)

    // 3. Define the start destination based on the collected state.
    // This will recompose when `isRegistered` changes.
    val startDestination = if (isRegistered) {
        Home.route
    } else {
        Onboarding.route
    }
    NavHost(navController = navController, startDestination = startDestination) {
        // Add Onboarding to the NavGraph
        composable(Onboarding.route) {
            Onboarding(navController)
        }
        composable(Home.route) {
            Home(navController)
        }
        composable(
            Profile.route
        ) {
            Profile(navController)
        }
        composable(
            route = DishDetails.route, // e.g., "Menu/{dishId}"
            arguments = listOf(navArgument(DishDetails.DISH_ID_KEY) {
                type = NavType.IntType // We expect the ID to be an Integer
            })
        ) { backStackEntry ->
            // Retrieve the dishId from the navigation arguments
            val dishId = backStackEntry.arguments?.getInt(DishDetails.DISH_ID_KEY)

            // Check if the ID is valid before navigating
            if (dishId != null) {
                DishDetails(id = dishId, navController)
            } else {
                // Optional: Handle the case where the ID is missing, e.g., navigate back
                Text("Error: Dish ID not found.")
            }
        }
    }
}