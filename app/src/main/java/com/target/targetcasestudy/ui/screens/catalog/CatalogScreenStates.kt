package com.target.targetcasestudy.ui.screens.catalog

import com.target.targetcasestudy.core.domain.ErrorState
import com.target.targetcasestudy.ui.screens.catalog.domain.Deal

sealed class CatalogScreenStates {
    object Loading : CatalogScreenStates()
    data class Browsing(
        val userId: String,
        val deals: List<Deal>,
        val errorState: ErrorState = ErrorState(),
    ) : CatalogScreenStates()

    object Error: CatalogScreenStates()
}
