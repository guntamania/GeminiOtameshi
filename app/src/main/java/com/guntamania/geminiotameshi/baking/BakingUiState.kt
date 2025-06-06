package com.guntamania.geminiotameshi.baking

/**
 * A sealed hierarchy describing the state of the text generation.
 */
sealed interface BakingUiState {

    /**
     * Empty state when the screen is first shown
     */
    data object Initial : BakingUiState

    /**
     * Still loading
     */
    data object Loading : BakingUiState

    /**
     * Text has been generated
     */
    data class Success(val data: BakingViewData) : BakingUiState

    /**
     * There was an error generating text
     */
    data class Error(val errorMessage: String) : BakingUiState
}