package com.target.targetcasestudy.ui.screens.details

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.chooseu.ui.ui_components.dialog.LoadingDialog
import com.target.targetcasestudy.ui.screens.details.domain.DealDetails

@Composable
fun DealsDetailContent(
    state: DealScreenStates,
    addToCart: (deal: DealDetails) -> Unit = {},
) {
    when (state) {
        DealScreenStates.Loading -> {
            LoadingDialog()
        }

        is DealScreenStates.ProductDetails -> {
            Text(text = state.deal!!.description)

            Button(onClick = { addToCart(state.deal) }) {
                Text(text = "Add To Cart")
            }
        }
    }
}