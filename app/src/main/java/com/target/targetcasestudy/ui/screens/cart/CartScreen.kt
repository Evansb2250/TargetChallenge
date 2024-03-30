package com.target.targetcasestudy.ui.screens.cart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CartScreen(
    userId: String,
    viewModel: CartViewModel = hiltViewModel(),
    navigateToLandingScreen: () -> Unit = {},
) {

    LaunchedEffect(key1 = Unit){
        viewModel.setCartId(userId)
        viewModel.loadCartData()
    }


    CartScreenContent(
        state = viewModel.state.collectAsState().value,
        deleteCartItem = viewModel::deletCartItem,
        navigateBack = navigateToLandingScreen
    )
}