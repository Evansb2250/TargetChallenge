package com.target.targetcasestudy.ui.components.cards

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.target.targetcasestudy.R
import com.target.targetcasestudy.theme.RebotoFontFamily
import com.target.targetcasestudy.theme.dpValue12
import com.target.targetcasestudy.theme.dpValue16
import com.target.targetcasestudy.theme.dpValue4
import com.target.targetcasestudy.theme.dpValue8
import com.target.targetcasestudy.theme.primaryColor
import com.target.targetcasestudy.ui.screens.cart.domain.CartItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CartItemsCard(
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
                    .background(primaryColor),
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