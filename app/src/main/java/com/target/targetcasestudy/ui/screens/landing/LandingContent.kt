package com.target.targetcasestudy.ui.screens.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.chooseu.ui.ui_components.dialog.LoadingDialog
import com.target.targetcasestudy.ui.components.toolbar.TargetToolBar
import com.target.targetcasestudy.ui.components.buttons.CartIconButton

@Composable()
fun LandingContent(
    state: LandingScreenStates,
    navigateToCart: (userId: String) -> Unit = {},
    navigateToCatalog: (userId: String) -> Unit = {},
    navigateToLoginScreen: () -> Unit = {},
    logout: () -> Unit = {},
    deleteAccount: (userId: String) -> Unit = {},
) {
    when (state) {
        is LandingScreenStates.LoggedIn -> {
            Scaffold(
                topBar = {
                    TargetToolBar(
                        title = {
                            Text(text = "Welcome ${state.currentUser.userName} !!")
                        },
                        actionIcon = {
                            CartIconButton(
                                cartItems = state.cartItems ,
                            ) {
                                navigateToCart(state.currentUser.userId)
                            }
                        }
                    )
                }
            ) { paddingValues ->

                Column(
                    modifier = Modifier
                        .background(color = Color.Red)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(
                        modifier = Modifier.padding(paddingValues),
                        onClick = { navigateToCatalog(state.currentUser.userId) }) {
                        Text(text = "View Catalog")
                    }


                    Button(onClick = logout) {
                        Text(text = "logout")
                    }

                    Button(onClick = { deleteAccount(state.currentUser.userId) }) {
                        Text(text = "Delete Account")
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