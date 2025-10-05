package com.example.littlelemoncapstone

interface Destinations {
    val route: String
}

object Home : Destinations {
    override val route = "Home"
}

object Onboarding : Destinations {
    override val route = "Onboarding"
}

object Profile : Destinations {
    override val route = "Profile"
}

object DishDetails : Destinations {
    private const val A_ROUTE = "Menu" // The base path for the route
    const val DISH_ID_KEY = "dishId"   // The key for the argument we will pass

    // The final route includes a placeholder for the dish ID
    override val route = "$A_ROUTE/{$DISH_ID_KEY}"

    // Helper function to build the navigation path with a specific ID
    fun createRoute(dishId: Int) = "$A_ROUTE/$dishId"
}
