package com.guntamania.geminiotameshi.baking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import com.guntamania.geminiotameshi.repository.PromptRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class BakingViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<BakingUiState> =
        MutableStateFlow(BakingUiState.Initial)
    val uiState: StateFlow<BakingUiState> =
        _uiState.asStateFlow()

    private val _messages = MutableStateFlow<List<BakingViewData.Entry>>(emptyList())

    private val promptRepository = PromptRepository()

    fun sendPrompt(
        prompt: String
    ) {
        _uiState.value = BakingUiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val outputContent = promptRepository.generateContent(prompt)
                outputContent?.let {
                    _messages.value += BakingViewData.Entry(
                        message = it,
                        date = Date(),
                    )
                    _uiState.value = BakingUiState.Success(BakingViewData(_messages.value))
                }
            } catch (e: Exception) {
                _uiState.value = BakingUiState.Error(e.localizedMessage ?: "")
            }
        }
    }
}