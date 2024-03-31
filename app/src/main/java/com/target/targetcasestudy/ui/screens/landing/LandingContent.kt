package com.target.targetcasestudy.ui.screens.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.chooseu.ui.ui_components.dialog.LoadingDialog
import com.target.targetcasestudy.theme.RebotoFontFamily
import com.target.targetcasestudy.theme.dpValue12
import com.target.targetcasestudy.theme.primaryColor
import com.target.targetcasestudy.ui.components.toolbar.TargetToolBar
import com.target.targetcasestudy.ui.components.buttons.CartIconButton
import com.target.targetcasestudy.ui.components.buttons.MenuButton

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
                            Text(
                                fontFamily = RebotoFontFamily ,
                                fontWeight = FontWeight.Bold,
                                fontSize = TextUnit(24f, TextUnitType.Sp),
                                color = Color.White,
                                text = "Welcome ${state.currentUser.userName} !!"
                            )
                        },
                        actionIcon = {
                            CartIconButton(
                                cartItems = state.cartItems,
                            ) {
                                navigateToCart(state.currentUser.userId)
                            }
                        },
                        color = primaryColor
                    )
                }
            ) { paddingValues ->

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    MenuButton(
                        text = "View Catalog",
                        onClick = { navigateToCatalog(state.currentUser.userId) },
                    )

                    MenuButton(
                        text = "Logout" ,
                        onClick = logout
                        )

                    MenuButton(
                        text = "Delete Account" ,
                        onClick = { deleteAccount(state.currentUser.userId) },
                    )
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