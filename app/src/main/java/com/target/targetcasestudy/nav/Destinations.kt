package com.target.targetcasestudy.nav

sealed class Destinations(
    val route: String
) {
    object Login : Destinations(
        route = "Login",
    )

    object LandingPage : Destinations(
        route = "LandingPage/{userId}"
    )

    object Catalog : Destinations(
        route = "Catalog/{userId}"
    )

    object ProductDetailRoute : Destinations(
        route = "ProductDetailRoute/{userId}/{productId}"
    )

    object CartRoute: Destinations(
        route = "CartRoute/{userId}"
    )
}