package com.target.targetcasestudy.nav

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun TargetNavHost(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.Login.route
    ) {

        composable(
            route = Destinations.Login.route,
        ){
            Text("Hi")
        }

        composable(
            route = Destinations.LandingPage.route,
        ){

        }
        composable(
            route = Destinations.Catalog.route,
        ){

        }
        composable(
            route = Destinations.ProductDetailRoute.route,){

        }
        composable(
            route = Destinations.CartRoute.route,){

        }
    }
}