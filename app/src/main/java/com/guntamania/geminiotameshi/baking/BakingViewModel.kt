package com.guntamania.geminiotameshi.baking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.guntamania.geminiotameshi.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey
    )

    fun sendPrompt(
        prompt: String
    ) {
        _uiState.value = BakingUiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(
                    content {
                        text(prompt)
                    }
                )
                response.text?.let { outputContent ->
                    _messages.value += BakingViewData.Entry(
                        message = outputContent,
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