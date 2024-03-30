package com.target.targetcasestudy.ui.components.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.target.targetcasestudy.R
import com.target.targetcasestudy.theme.dpValue16

@Composable
fun CartIconButton(
    cartItems: Int,
    navigateToCart: () -> Unit
) {
    BadgedBox(
        modifier = Modifier.padding(
            horizontal = dpValue16
        ),
        badge = {
            Badge(
                containerColor = if (cartItems > 0) Color.Red else Color.Transparent,
                contentColor = Color.White,
            ) {
                if (cartItems > 0) {
                    Text("$cartItems")
                }
            }
        },
    ) {
        IconButton(
            onClick = navigateToCart,
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.cart_icon,
                ),
                contentDescription = "Cart Icon",
            )
        }
    }
}