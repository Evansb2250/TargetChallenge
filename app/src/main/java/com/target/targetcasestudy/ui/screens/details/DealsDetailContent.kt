package com.target.targetcasestudy.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
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
import com.example.chooseu.ui.ui_components.dialog.ErrorDialog
import com.example.chooseu.ui.ui_components.dialog.LoadingDialog
import com.target.targetcasestudy.R
import com.target.targetcasestudy.theme.RebotoFontFamily
import com.target.targetcasestudy.theme.dpValue4
import com.target.targetcasestudy.ui.components.generic.ErrorScreen
import com.target.targetcasestudy.ui.components.toolbar.TargetToolBar
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