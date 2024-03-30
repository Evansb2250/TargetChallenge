package com.target.targetcasestudy.ui.screens.details

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DealsDetailScreen(
    userId: String,
    dealId: String,
    viewModel: DealDetailViewModel = hiltViewModel(),
    navigateBack: () -> Unit = {},
) {
    //Override BackHandler
    BackHandler {

    }

    LaunchedEffect(key1 = Unit) {
        viewModel.loadProductDetails(
            userId = userId,
            productId = dealId,
        )
    }

    val state = viewModel.state.collectAsState().value

    DealsDetailContent(
        navigateBack = navigateBack,
        state = state,
        addToCart = viewModel::addToCart,
        onDismiss = viewModel::dismissDialog
    )
}