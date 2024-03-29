package com.target.targetcasestudy.nav

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.target.targetcasestudy.ui.screens.catalog.CatalogScreen
import com.target.targetcasestudy.ui.screens.details.DealsDetailScreen
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
                        route = addUserIdToDestination(
                            Destinations.LandingPage,
                            userId
                        )
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
                navigateToCartScreen = { userId ->
                    navController.navigate(
                        route = addUserIdToDestination(
                            destinations = Destinations.CartRoute,
                            userId = userId,
                        )
                    )
                },
                navigateToCatalogScreen = { userId ->
                    navController.navigate(
                        route = addUserIdToDestination(
                            destinations = Destinations.Catalog,
                            userId = userId
                        ),
                    )
                },
            )
        }

        composable(
            route = Destinations.Catalog.route,
        ) {
            val userId = it.arguments?.getString(userIdKey) ?: return@composable
            CatalogScreen(
                userId = userId,
                navigateToDetailsPage = { userId, dealId ->
                    navController.navigate(
                        route = Destinations.ProductDetailRoute.route.replace(
                            "{$userIdKey}",
                            userId
                        ).replace(
                            "{$productIdKey}",
                            dealId,
                        )
                    )
                }
            )
        }

        composable(
            route = Destinations.ProductDetailRoute.route,
        ) {

            val userId = it.arguments?.getString(userIdKey) ?: return@composable
            val productId = it.arguments?.getString(productIdKey) ?: return@composable

            DealsDetailScreen(userId = userId, dealId = productId)

        }

        composable(
            route = Destinations.CartRoute.route,
        ) {
            Text("Cart")
        }
    }
}

private fun addUserIdToDestination(
    destinations: Destinations,
    userId: String,
): String {
    return destinations.route.replace("{$userIdKey}", userId)
}