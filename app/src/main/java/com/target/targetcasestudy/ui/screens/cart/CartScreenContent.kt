package com.target.targetcasestudy.ui.screens.cart

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.chooseu.ui.ui_components.dialog.ErrorDialog
import com.example.chooseu.ui.ui_components.dialog.LoadingDialog
import com.target.targetcasestudy.R
import com.target.targetcasestudy.core.domain.ErrorState
import com.target.targetcasestudy.theme.RebotoFontFamily
import com.target.targetcasestudy.theme.dpValue12
import com.target.targetcasestudy.theme.dpValue16
import com.target.targetcasestudy.theme.dpValue4
import com.target.targetcasestudy.theme.dpValue8
import com.target.targetcasestudy.theme.primaryColor
import com.target.targetcasestudy.ui.components.toolbar.TargetToolBar
import com.target.targetcasestudy.ui.screens.cart.domain.CartItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CartScreenContent(
    state: CartScreenStates,
    deleteCartItem: (CartItem) -> Unit = {},
    navigateBack: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TargetToolBar(
                title = {
                    Text(
                        fontFamily = RebotoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = TextUnit(24f, TextUnitType.Sp),
                        text = "Cart"
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
                        ErrorDialog(title = "", error = state.errorState.errorMessage)
                    }
                    Column {
                        LazyColumn(
                            modifier = Modifier
                                .padding(paddingValues)
                                .weight(12f)
                        ) {
                            items(
                                items = state.products
                            ) {
                                CartItemsCard(
                                    deal = it,
                                    deleteCartItem = { deleteCartItem(it) }
                                )
                                Spacer(modifier = Modifier.size(10.dp))
                            }
                        }
                        Text(
                            modifier = Modifier.weight(2f),
                            text = "Total price: ${state.cartTotalInPennies}"
                        )
                    }
                }
            }

            CartScreenStates.Loading -> {
                LoadingDialog()
            }
        }

    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CartItemsCard(
    deal: CartItem,
    deleteCartItem: (deal: CartItem) -> Unit = {},
) {
    var showTrashIcon by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .padding(
                all = dpValue16,
            )
            .background(
                color = Color.White,
            )
            .combinedClickable(
                onLongClick = {
                    showTrashIcon = true
                },
                onClick = {
                    showTrashIcon = false
                },
            ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
    ) {
        Row() {
            Box(
                modifier = Modifier
                    .padding(dpValue4)
                    .size(140.dp)
                    .clip(RoundedCornerShape(dpValue8))
                    .background(
                        color = Color.Gray,
                        shape = RoundedCornerShape(dpValue8)
                    )
            ) {
                AsyncImage(
                    model = deal.imageUrl,
                    contentDescription = "Product image of a ${deal.title} in catalog"
                )
            }
            Column(
                modifier = Modifier
                    .size(
                        width = 172.dp, 141.dp
                    )
            ) {
                Text(
                    modifier = Modifier
                        .weight(5f, fill = false)
                        .padding(dpValue12),
                    fontSize = TextUnit(12f, TextUnitType.Sp),
                    fontFamily = RebotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    text = deal.title
                )

                //Price
                Text(
                    modifier = Modifier.padding(horizontal = dpValue4),
                    fontSize = TextUnit(21f, TextUnitType.Sp),
                    fontFamily = RebotoFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6D6B6B),
                    text = deal.price.displayString
                )

                //Price
                Text(
                    modifier = Modifier.padding(horizontal = dpValue4),
                    fontSize = TextUnit(14f, TextUnitType.Sp),
                    fontFamily = RebotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF6D6B6B),
                    text = "Quantity: ${deal.quantity}"
                )
            }
        }
        if (showTrashIcon) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        showTrashIcon = false
                        deleteCartItem(deal)
                    }
                    .background(Color.Red),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.bin_icon
                    ), contentDescription = ""
                )
            }
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