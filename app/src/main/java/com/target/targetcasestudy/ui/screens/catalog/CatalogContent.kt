package com.target.targetcasestudy.ui.screens.catalog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.chooseu.ui.ui_components.dialog.ErrorDialog
import com.example.chooseu.ui.ui_components.dialog.LoadingDialog
import com.target.targetcasestudy.ui.components.toolbar.TargetToolBar

@Composable
fun CatalogContent(
    state: CatalogScreenStates,
    navigateToDetailsPage: (userId: String, dealId: String) -> Unit = { _, _ -> },
) {
    Scaffold(
        topBar = {
            TargetToolBar(
                title = {
                    Text(text = "Product List")
                },
            )
        }
    ) { innerPadding ->

        when (state) {
            is CatalogScreenStates.Browsing -> {

                if (state.errorState.isError) {
                    ErrorDialog(
                        title = "Failed to connect to server",
                        error = state.errorState.errorMessage
                    )
                }

                LazyColumn(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    items(
                        items = state.deals
                    ) { deal ->
                        Card(
                            modifier = Modifier.clickable {
                                navigateToDetailsPage(state.userId, deal.id.toString())
                            }
                        ) {

                            AsyncImage(
                                model = deal.imageUrl,
                                contentDescription = "image of ${deal.title} in catalog"
                            )

                            Text(text = deal.id.toString())

                            Text(text = deal.title)
                            Text(text = deal.aisle)
                            Text(text = deal.regularPrice.amountInCents.toString())
                            Spacer(modifier = Modifier.size(10.dp))
                        }
                    }
                }
            }

            CatalogScreenStates.Loading -> {
                LoadingDialog()
            }
        }
    }

}