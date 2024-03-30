package com.target.targetcasestudy.ui.screens.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.chooseu.ui.ui_components.dialog.ErrorDialog
import com.example.chooseu.ui.ui_components.dialog.LoadingDialog
import com.target.targetcasestudy.R
import com.target.targetcasestudy.theme.RebotoFontFamily
import com.target.targetcasestudy.theme.dpValue12
import com.target.targetcasestudy.theme.dpValue16
import com.target.targetcasestudy.theme.dpValue4
import com.target.targetcasestudy.theme.dpValue8
import com.target.targetcasestudy.ui.components.generic.ErrorScreen
import com.target.targetcasestudy.ui.components.toolbar.TargetToolBar
import com.target.targetcasestudy.ui.screens.catalog.domain.Deal

@Composable
fun CatalogContent(
    state: CatalogScreenStates,
    navigateToDetailsPage: (userId: String, dealId: String) -> Unit = { _, _ -> },
    dismissDialog: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TargetToolBar(
                title = {
                    Text(
                        modifier = Modifier.padding(horizontal = dpValue4),
                        fontSize = TextUnit(18f, TextUnitType.Sp),
                        fontFamily = RebotoFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333),
                        text = "Product List"
                    )
                },
            )
        }
    ) { innerPadding ->
        when (state) {
            is CatalogScreenStates.Browsing -> {
                if (state.errorState.isError) {
                    ErrorDialog(
                        title = "Failed to connect to server",
                        error = state.errorState.errorMessage,
                        onDismiss = dismissDialog
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .background(
                            color = Color.White
                        )
                        .padding(innerPadding)
                ) {
                    items(
                        items = state.deals
                    ) { deal ->
                        DealCard(
                            deal = deal,
                            onClick = { dealId ->
                                navigateToDetailsPage(state.userId, dealId)
                            }
                        )
                        HorizontalDivider()
                        Spacer(modifier = Modifier.size(10.dp))
                    }
                }
            }

            CatalogScreenStates.Loading -> {
                LoadingDialog()
            }

            CatalogScreenStates.Error -> {
                ErrorScreen()
            }
        }
    }

}


@Composable
fun DealCard(
    deal: Deal,
    onClick: (dealId: String) -> Unit = {},
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                all = dpValue16,
            )
            .clickable {
                onClick(deal.id.toString())
            }
    ) {
        Box(
            modifier = Modifier
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
            PriceCard(
                regularPrice = deal.regularPrice.displayString,
                specialPrice = deal.salePrice?.displayString,
                fulfillment = deal.fulfillment,
            )
            Text(
                fontSize = TextUnit(14f, TextUnitType.Sp),
                fontFamily = RebotoFontFamily,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(
                        vertical = dpValue16,
                        horizontal = dpValue12,
                    )
                    .weight(1f, fill = true)
                    .fillMaxWidth(),
                text = deal.title,
            )

            Row(
                modifier = Modifier.padding(
                    horizontal = dpValue8,
                )
            ) {
                Text(
                    modifier = Modifier.padding(
                        horizontal = dpValue4,
                    ),
                    fontSize = TextUnit(12f, TextUnitType.Sp),
                    fontFamily = RebotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF008300),
                    text = deal.availability,
                )
                Text(
                    fontSize = TextUnit(12f, TextUnitType.Sp),
                    fontFamily = RebotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF666666),
                    text = "in aisle ${deal.aisle}"
                )

            }
        }
    }
}


@Composable
fun PriceCard(
    modifier: Modifier = Modifier,
    regularPrice: String,
    specialPrice: String?,
    fulfillment: String,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = dpValue8,
                )
                .fillMaxWidth(),
        ) {
            //Price
            Text(
                modifier = Modifier.padding(horizontal = dpValue4),
                fontSize = TextUnit(21f, TextUnitType.Sp),
                fontFamily = RebotoFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFAA0000),
                text = specialPrice ?: regularPrice
            )
            Text(
                fontSize = TextUnit(12f, TextUnitType.Sp),
                fontFamily = RebotoFontFamily,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF333333),
                text = "reg ${regularPrice}"
            )
        }
        Text(
            fontSize = TextUnit(14f, TextUnitType.Sp),
            modifier = Modifier.padding(horizontal = dpValue12),
            fontFamily = RebotoFontFamily,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF666666),
            text = fulfillment,
            textAlign = TextAlign.Left,
        )
    }
}



