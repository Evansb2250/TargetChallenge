package com.target.targetcasestudy.ui.screens.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.mutableStateOf

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private var _state = mutableStateOf(
        LoginScreenStates.InProgress()
    )
    val state = _state.value

}