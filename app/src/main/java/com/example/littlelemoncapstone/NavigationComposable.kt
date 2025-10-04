package com.example.littlelemoncapstone

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
    }
}