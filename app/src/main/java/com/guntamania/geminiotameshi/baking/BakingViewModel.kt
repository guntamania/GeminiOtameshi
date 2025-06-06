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

    private var messages: MutableList<BakingViewData.Entry> = mutableListOf()

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
                    messages.add(
                        BakingViewData.Entry(
                            message = outputContent,
                            date = Date(),
                        )
                    )
                    _uiState.value = BakingUiState.Success(BakingViewData(messages))
                }
            } catch (e: Exception) {
                _uiState.value = BakingUiState.Error(e.localizedMessage ?: "")
            }
        }
    }
}