package com.target.targetcasestudy.ui.screens.details

import com.target.targetcasestudy.core.domain.ErrorState
import com.target.targetcasestudy.ui.screens.details.domain.DealDetails

sealed class DealScreenStates {

    object Loading : DealScreenStates()

    data class ProductDetails(
        val deal: DealDetails?,
        val errorState: ErrorState = ErrorState(),
    ) : DealScreenStates()

    object Error: DealScreenStates()
}
