package com.target.targetcasestudy.ui.screens.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.chooseu.ui.ui_components.dialog.ErrorDialog
import com.example.chooseu.ui.ui_components.dialog.LoadingDialog
import com.target.targetcasestudy.R
import com.target.targetcasestudy.theme.RebotoFontFamily
import com.target.targetcasestudy.theme.dpValue12
import com.target.targetcasestudy.theme.dpValue4
import com.target.targetcasestudy.theme.dpValue8
import com.target.targetcasestudy.ui.components.cards.CartItemsCard
import com.target.targetcasestudy.ui.components.generic.ErrorScreen
import com.target.targetcasestudy.ui.components.toolbar.TargetToolBar
import com.target.targetcasestudy.ui.screens.cart.domain.CartItem

@Composable
fun CartScreenContent(
    state: CartScreenStates,
    deleteCartItem: (CartItem) -> Unit = {},
    navigateBack: () -> Unit = {},
    onDismissDialog: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TargetToolBar(
                title = {
                    Text(
                        fontFamily = RebotoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = TextUnit(24f, TextUnitType.Sp),
                        text = "Cart Items"
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack,
                    ) {
                        Image(
                            painter = painterResource(R.drawable.back_arrow),
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        when (state) {
            is CartScreenStates.CartView -> {
                Box {
                    if (state.errorState.isError) {
                        ErrorDialog(
                            title = "Error Detected!",
                            error = state.errorState.errorMessage,
                            onDismiss = onDismissDialog
                        )
                    }
                    Column {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(paddingValues)
                                .weight(12f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(
                                items = state.products
                            ) {
                                CartItemsCard(
                                    cartItem = it,
                                    deleteCartItem = { deleteCartItem(it) }
                                )
                            }
                        }
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border( width =  1.dp, shape = RoundedCornerShape(dpValue12), color = Color.Black)
                                .padding(dpValue8)
                                .weight(2f),
                            fontSize = TextUnit(23f, TextUnitType.Sp),
                            fontFamily = RebotoFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6D6B6B),
                            text = "Items in Cart: ${state.itemQuantity}" +
                                    "\nTotal price: ${state.displayPrice}",
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            CartScreenStates.Loading -> {
                LoadingDialog()
            }

            CartScreenStates.Error -> {
                ErrorScreen()
            }
        }
    }
}