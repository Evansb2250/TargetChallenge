package com.target.targetcasestudy.nav


val userIdKey = "userId"
val productIdKey = "productId"

sealed class Destinations(
    val route: String
) {
    object Login : Destinations(
        route = "Login",
    )

    object SignUp: Destinations(
        route = "SignUp"
    )
    object LandingPage : Destinations(
        route = "LandingPage/{$userIdKey}"
    )

    object Catalog : Destinations(
        route = "Catalog/{$userIdKey}"
    )

    object ProductDetailRoute : Destinations(
        route = "ProductDetailRoute/{$userIdKey}/{$productIdKey}"
    )

    object CartRoute: Destinations(
        route = "CartRoute/{$userIdKey}"
    )
}