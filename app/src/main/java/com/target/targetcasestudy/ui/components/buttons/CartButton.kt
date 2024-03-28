package com.target.targetcasestudy.ui.components.buttons

import androidx.compose.foundation.Image
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.target.targetcasestudy.R

@Composable
fun CartButton(
    navigateToCart: () -> Unit
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