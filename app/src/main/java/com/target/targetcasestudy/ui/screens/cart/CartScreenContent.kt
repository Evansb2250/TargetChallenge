package com.target.targetcasestudy.ui.screens.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.chooseu.ui.ui_components.dialog.ErrorDialog
import com.example.chooseu.ui.ui_components.dialog.LoadingDialog
import com.target.targetcasestudy.core.domain.ErrorState
import com.target.targetcasestudy.ui.screens.cart.domain.CartItem

@Composable
fun CartScreenContent(
    state: CartScreenStates,
    deleteCartItem: (CartItem) -> Unit = {},
) {
    when (state) {
        is CartScreenStates.CartView -> {
            Box {
                if (state.errorState.isError) {
                    ErrorDialog(title = "", error = state.errorState.errorMessage)
                }
                Column {
                    LazyColumn {
                        items(
                            items = state.products
                        ) {
                            Card() {
                                AsyncImage(
                                    model = it.imageUrl,
                                    contentDescription = "",
                                )
                                Text(text = "${it.title}")
                                Button(onClick = { deleteCartItem(it) }) {
                                    Text(text = "Delete")
                                }
                            }
                            Spacer(modifier = Modifier.size(10.dp))
                        }
                    }

                    Text(text = "Total price: ${state.cartTotalInPennies}")
                }
            }
        }

        CartScreenStates.Loading -> {
            LoadingDialog()
        }
    }
}


sealed class CartScreenStates() {
    object Loading : CartScreenStates()
    data class CartView(
        val products: List<CartItem>,
        val cartTotalInPennies: Int,
        val errorState: ErrorState = ErrorState()
    ) : CartScreenStates()
}