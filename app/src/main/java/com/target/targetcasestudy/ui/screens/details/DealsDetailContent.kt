package com.target.targetcasestudy.ui.screens.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.chooseu.ui.ui_components.dialog.LoadingDialog

@Composable
fun DealsDetailContent(
    state: DealScreenStates,
    addToCart: (dealId: String) -> Unit = {},
) {
    when (state) {
        DealScreenStates.Loading -> {
            LoadingDialog()
        }

        is DealScreenStates.ProductDetails -> {
            Text(text = state.deal!!.description)
        }
    }
}