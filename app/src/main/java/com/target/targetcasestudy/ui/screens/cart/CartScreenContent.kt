package com.target.targetcasestudy.ui.screens.cart

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
                        ErrorDialog(
                            title = "",
                            error = state.errorState.errorMessage,
                            onDismiss = onDismissDialog
                            )
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
                                    cartItem = it,
                                    deleteCartItem = { deleteCartItem(it) }
                                )
                            }
                        }
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(dpValue4)
                                .weight(2f),
                            fontSize = TextUnit(23f, TextUnitType.Sp),
                            fontFamily = RebotoFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6D6B6B),
                            text = "Items in Cart: ${state.products.sumOf { it.quantity }}" +
                                    "\nTotal price: \$${(state.cartTotalInPennies.toDouble() / 100)}"
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


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CartItemsCard(
    cartItem: CartItem,
    deleteCartItem: (deal: CartItem) -> Unit = {},
    cardColor: Color = Color.White,
) {
    var showTrashIcon by remember {
        mutableStateOf(false)
    }
    var showToastMessage by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = showToastMessage) {
        if (showToastMessage) {
            Toast.makeText(context, "Item deleted from to Cart !!", Toast.LENGTH_SHORT).show()
            showToastMessage = false
        }
    }

    Card(
        modifier = Modifier
            .padding(
                all = dpValue16,
            )
            .background(
                color = cardColor,
            )
            .combinedClickable(
                onLongClick = {
                    showTrashIcon = true
                },
                onClick = {
                    showTrashIcon = false
                },
            ),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
    ) {
        Row(
            modifier = Modifier.background(color = cardColor)
        ) {
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
                    model = cartItem.imageUrl,
                    contentDescription = "Product image of a ${cartItem.title} in catalog"
                )
            }
            Column(
                modifier = Modifier
                    .background(color = cardColor)
                    .size(
                        width = 172.dp, 141.dp
                    )
            ) {
                Text(
                    modifier = Modifier
                        .weight(5f, fill = false)
                        .padding(
                            vertical = dpValue12,
                            horizontal = dpValue4,
                        ),
                    fontSize = TextUnit(12f, TextUnitType.Sp),
                    fontFamily = RebotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    text = cartItem.title
                )

                //Price
                Text(
                    modifier = Modifier.padding(
                        horizontal = dpValue4,
                    ),
                    fontSize = TextUnit(21f, TextUnitType.Sp),
                    fontFamily = RebotoFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6D6B6B),
                    text = cartItem.price.displayString
                )

                //Price
                Text(
                    modifier = Modifier
                        .padding(horizontal = dpValue4),
                    fontSize = TextUnit(14f, TextUnitType.Sp),
                    fontFamily = RebotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF6D6B6B),
                    text = "Quantity: ${cartItem.quantity}"
                )
            }
        }
        if (showTrashIcon) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showToastMessage = true
                        showTrashIcon = false
                        deleteCartItem(cartItem)
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

    object Error: CartScreenStates()
}