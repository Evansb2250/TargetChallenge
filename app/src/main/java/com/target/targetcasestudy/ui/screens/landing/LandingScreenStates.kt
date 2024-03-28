package com.target.targetcasestudy.ui.screens.landing

sealed class LandingScreenStates {

    object Loading: LandingScreenStates()
    data class LoggedIn(val userId: String): LandingScreenStates()
    object Logout: LandingScreenStates()
}
