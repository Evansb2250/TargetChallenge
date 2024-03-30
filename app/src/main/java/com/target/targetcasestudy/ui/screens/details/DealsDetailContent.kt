package com.target.targetcasestudy.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.chooseu.ui.ui_components.dialog.LoadingDialog
import com.target.targetcasestudy.R
import com.target.targetcasestudy.theme.RebotoFontFamily
import com.target.targetcasestudy.ui.components.toolbar.TargetToolBar
import com.target.targetcasestudy.ui.screens.details.domain.DealDetails

@Composable
fun DealsDetailContent(
    navigateBack: () -> Unit = {},
    state: DealScreenStates,
    addToCart: (deal: DealDetails) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TargetToolBar(
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack,
                    ) {
                        Image(
                            painter = painterResource(R.drawable.back_arrow),
                            contentDescription = ""
                        )
                    }
                },
                title = {
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        fontSize = TextUnit(18f, TextUnitType.Sp),
                        fontFamily = RebotoFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333),
                        text = "Product List"
                    )
                },
            )
        }
    ) { it ->
        HorizontalDivider()
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
}