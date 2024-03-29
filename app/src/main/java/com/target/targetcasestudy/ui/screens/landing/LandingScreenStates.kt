package com.target.targetcasestudy.ui.screens.landing

import com.target.targetcasestudy.core.domain.User

sealed class LandingScreenStates {

    object Loading: LandingScreenStates()
    data class LoggedIn(val currentUser: User, val cartItems: Int = 0,): LandingScreenStates()
    object Logout: LandingScreenStates()
}
