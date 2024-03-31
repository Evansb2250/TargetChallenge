package com.target.targetcasestudy.ui.screens.catalog

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CatalogScreen(
    userId: String,
    viewModel: CatalogViewModel = hiltViewModel(),
    navigateToDetailsPage: (userId: String, dealId: String) -> Unit = { _, _ -> },
    navigateBackToLanding: () -> Unit = {},
) {
    BackHandler {
        navigateBackToLanding()
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.loadData(userId)
    }

    val state = viewModel.state.collectAsState().value

    CatalogContent(
        state = state,
        navigateToDetailsPage = navigateToDetailsPage,
        dismissDialog = viewModel::dismissDialog
    )
}