package com.target.targetcasestudy.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.target.targetcasestudy.theme.RebotoFontFamily
import com.target.targetcasestudy.ui.components.toolbar.TargetToolBar
import com.target.targetcasestudy.ui.screens.catalog.PriceCard
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
                        text = "Details"
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
                if (state.errorState.isError || state.deal == null) {
                    ErrorDialog(
                        title = "Couldn't Find Deal",
                        error = state.errorState.errorMessage,
                    )
                } else {
                    DealDetailsCard(
                        deal = state.deal,
                        addToCart = addToCart
                    )
                }
            }
        }
    }
}


@Composable
fun DealDetailsCard(
    deal: DealDetails,
    addToCart: (deal: DealDetails) -> Unit = {},
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(8.dp)
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .size(328.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    color = Color.Red,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = deal.imageUrl,
                contentDescription = "Product image of a ${deal.title} in catalog"
            )
        }

        Text(
            fontSize = TextUnit(18f, TextUnitType.Sp),
            fontFamily = RebotoFontFamily,
            fontWeight = FontWeight.Normal,
            text = deal.title
        )

        PriceCard(
            regularPrice = deal.regularPrice.displayString,
            specialPrice = deal.salePrice?.displayString,
            fulfillment = deal.fulfillment,
        )


        Spacer(
            modifier = Modifier
                .background(
                    color = Color(0XFF888888)
                )
                .height(12.dp)
                .fillMaxWidth()
        )

        Text(
            fontSize = TextUnit(18f, TextUnitType.Sp),
            fontFamily = RebotoFontFamily,
            fontWeight = FontWeight.Bold,
            text = "Product Details",
        )

        Text(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            fontSize = TextUnit(16f, TextUnitType.Sp),
            fontFamily = RebotoFontFamily,
            fontWeight = FontWeight.Normal,
            text = deal.description,
            color = Color(0XFF888888)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0XFFCC0000),
                    shape = RoundedCornerShape(4.dp)
                    )
                ,
            contentAlignment = Alignment.BottomCenter,
        ) {
            Button(
                onClick = { addToCart(deal) }
            ) {
                Text(text = "Add to cart")
            }
        }
    }

}

