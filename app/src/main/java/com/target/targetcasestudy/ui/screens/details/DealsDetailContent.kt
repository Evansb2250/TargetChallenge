package com.target.targetcasestudy.ui.screens.details

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.chooseu.ui.ui_components.dialog.ErrorDialog
import com.example.chooseu.ui.ui_components.dialog.LoadingDialog
import com.target.targetcasestudy.R
import com.target.targetcasestudy.theme.RebotoFontFamily
import com.target.targetcasestudy.theme.dpValue12
import com.target.targetcasestudy.theme.dpValue16
import com.target.targetcasestudy.theme.dpValue4
import com.target.targetcasestudy.theme.dpValue8
import com.target.targetcasestudy.theme.primaryColor
import com.target.targetcasestudy.ui.components.generic.ErrorScreen
import com.target.targetcasestudy.ui.components.toolbar.TargetToolBar
import com.target.targetcasestudy.ui.screens.catalog.PriceCard
import com.target.targetcasestudy.ui.screens.details.domain.DealDetails

@Composable
fun DealsDetailContent(
    navigateBack: () -> Unit = {},
    state: DealScreenStates,
    addToCart: (deal: DealDetails) -> Unit = {},
    onDismiss: () -> Unit = {},
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
                        modifier = Modifier.padding(horizontal = dpValue4),
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
                        onDismiss = onDismiss
                    )
                } else {
                    DealDetailsCard(
                        deal = state.deal,
                        addToCart = addToCart
                    )
                }
            }

            DealScreenStates.Error -> {
                ErrorScreen()
            }
        }
    }
}

@Composable
private fun DealDetailsCard(
    deal: DealDetails,
    addToCart: (deal: DealDetails) -> Unit = {},
) {
    var showToastMessage by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = showToastMessage) {
        if (showToastMessage) {
            Toast.makeText(context, "Added to Cart !!", Toast.LENGTH_SHORT).show()
            showToastMessage = false
        }
    }

    Column {
        LazyColumn(
            modifier = Modifier
                .weight(8f)
        ) {

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dpValue8),
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .padding(top = dpValue8)
                            .clip(RoundedCornerShape(dpValue8))
                            .size(328.dp),
                        model = deal.imageUrl,
                        contentDescription = "Product image of a ${deal.title} in catalog"
                    )
                }
                Text(
                    modifier = Modifier.padding(dpValue12),
                    fontSize = TextUnit(18f, TextUnitType.Sp),
                    fontFamily = RebotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    text = deal.title
                )
                PriceCard(
                    modifier = Modifier.padding(vertical = dpValue16),
                    regularPrice = deal.regularPrice.displayString,
                    specialPrice = deal.salePrice?.displayString,
                    fulfillment = deal.fulfillment,
                )

            }



            item {
                Spacer(
                    modifier = Modifier
                        .background(
                            color = Color(0xFFF3F3F3)
                        )
                        .height(dpValue16)
                        .fillMaxWidth()
                )

            }

            item {
                Column(
                    modifier = Modifier.padding(
                        horizontal = dpValue12,
                    )
                ) {

                    Text(
                        modifier = Modifier.padding(vertical = dpValue16),
                        fontSize = TextUnit(18f, TextUnitType.Sp),
                        fontFamily = RebotoFontFamily,
                        fontWeight = FontWeight.Bold,
                        text = "Product Details",
                    )


                    Text(
                        modifier = Modifier
                            .padding(vertical = dpValue16)
                            .fillMaxWidth(),
                        fontSize = TextUnit(16f, TextUnitType.Sp),
                        fontFamily = RebotoFontFamily,
                        fontWeight = FontWeight.Normal,
                        text = buildAnnotatedString {
                            withStyle(style = ParagraphStyle(lineHeight = 20.sp)) {
                                append(deal.description)
                            }
                        },
                        color = Color(0XFF888888)
                    )
                }

            }

        }

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(all = dpValue16)
                .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter,
        ) {
            Button(
                modifier = Modifier
                    .height(44.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor,
                ),
                onClick = {
                    showToastMessage = true
                    addToCart(deal)
                },
                shape = RoundedCornerShape(dpValue4),
            ) {
                Text(
                    text = "Add to cart"
                )
            }
        }
    }


}

