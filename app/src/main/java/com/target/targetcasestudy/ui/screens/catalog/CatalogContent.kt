package com.target.targetcasestudy.ui.screens.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.chooseu.ui.ui_components.dialog.ErrorDialog
import com.example.chooseu.ui.ui_components.dialog.LoadingDialog
import com.target.targetcasestudy.theme.RebotoFontFamily
import com.target.targetcasestudy.theme.dpValue4
import com.target.targetcasestudy.ui.components.generic.ErrorScreen
import com.target.targetcasestudy.ui.components.toolbar.TargetToolBar

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



