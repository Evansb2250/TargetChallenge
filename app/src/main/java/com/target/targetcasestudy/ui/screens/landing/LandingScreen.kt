package com.target.targetcasestudy.ui.screens.landing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.target.targetcasestudy.nav.Destinations

@Composable
fun LandingScreen(
    userId: String,
    vm: LandingPageViewModel = hiltViewModel(),
    navigateToCartScreen: (String) -> Unit = {},
    navigateToCatalogScreen: (String) -> Unit = {},
    navigateToLoginScreen: () -> Unit = {},
) {

    LaunchedEffect(key1 = Unit) {
        vm.loadDetails(userId = userId.toInt())
    }

    LandingContent(
        state = vm.state.collectAsState().value,
        navigateToCart = navigateToCartScreen,
        navigateToCatalog = navigateToCatalogScreen,
        navigateToLoginScreen = navigateToLoginScreen,
    )
}


