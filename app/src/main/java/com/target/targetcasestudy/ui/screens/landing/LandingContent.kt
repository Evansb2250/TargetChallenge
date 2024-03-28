package com.target.targetcasestudy.ui.screens.landing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.chooseu.ui.ui_components.dialog.LoadingDialog
import com.target.targetcasestudy.ui.components.buttons.CartButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable()
fun LandingContent(
    state: LandingScreenStates,
    navigateToCart: (userId: String) -> Unit = {},
    navigateToCatalog: (userId: String) -> Unit = {},
    logout: () -> Unit = {},
    navigateToLoginScreen: () -> Unit = {},
) {
    when (state) {
        is LandingScreenStates.LoggedIn -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {

                        },
                        actions = {
                            CartButton {
                                navigateToCart(state.userId)
                            }
                        },
                    )
                }
            ) { paddingValues ->

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(
                        modifier = Modifier.padding(paddingValues),
                        onClick = { navigateToCatalog(state.userId) }) {
                        Text(text = "View Catalog")
                    }


                    Button(onClick = logout) {
                        Text(text = "logout")
                    }
                }

            }
        }

        LandingScreenStates.Logout -> {
            LaunchedEffect(key1 = Unit) {
                navigateToLoginScreen()
            }
        }

        else -> {
            LoadingDialog()
        }
    }
}