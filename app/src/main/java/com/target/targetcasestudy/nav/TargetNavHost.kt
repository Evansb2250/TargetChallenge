package com.target.targetcasestudy.nav

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.target.targetcasestudy.ui.screens.landing.LandingScreen
import com.target.targetcasestudy.ui.screens.login.LoginScreen
import com.target.targetcasestudy.ui.screens.signup.SignUpScreen

@Composable
fun TargetNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.Login.route
    ) {

        composable(
            route = Destinations.Login.route,
        ) {
            LoginScreen(
                navigateToLandingPage = { userId ->
                    navController.navigate(
                        route = Destinations.LandingPage.route.replace("{$userIdKey}", userId)
                    )
                },
                navigateToSignUpScreen = {
                    navController.navigate(
                        route = Destinations.SignUp.route,
                    )
                }
            )
        }

        composable(
            route = Destinations.SignUp.route
        ) {
            SignUpScreen(
                navigateToLoginScreen = {
                    navController.navigate(
                        route = Destinations.Login.route
                    )
                }
            )
        }

        composable(
            route = Destinations.LandingPage.route,
        ) {
            val userId = it.arguments?.getString(userIdKey) ?: return@composable
            LandingScreen(
                userId = userId,
                navigateToLoginScreen = {
                    navController.navigate(
                        route = Destinations.Login.route
                    )
                },
                navigateToCartScreen = {
                    navController.navigate(
                        route = Destinations.CartRoute.route
                    )
                },
                navigateToCatalogScreen = {
                    navController.navigate(
                        route = Destinations.Catalog.route
                    )
                },
            )
        }
        composable(
            route = Destinations.Catalog.route,
        ) {
            Text("Catalog")
        }
        composable(
            route = Destinations.ProductDetailRoute.route,
        ) {

        }
        composable(
            route = Destinations.CartRoute.route,
        ) {
            Text("Cart")
        }
    }
}
