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
    private val _messages = MutableStateFlow<List<BakingViewData.Entry>>(emptyList())
    val messages: StateFlow<List<BakingViewData.Entry>> = _messages.asStateFlow()

    private val promptRepository = PromptRepository()

    fun sendPrompt(
        prompt: String
    ) {
        // Add user message with Loading state
        val userMessage = BakingViewData.Entry.Message(
            message = prompt,
            date = Date(),
            sender = BakingViewData.Sender.YOU,
        )

        val loadingMessage = BakingViewData.Entry.Loading

        _messages.value += listOf(userMessage, loadingMessage)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val outputContent = promptRepository.generateContent(prompt)
                outputContent?.let {
                    // Update user message state to Success
                    _messages.value = _messages.value.map { entry ->
                        if (entry is BakingViewData.Entry.Loading) {
                            BakingViewData.Entry.Message(
                                message = it,
                                date = Date(),
                                sender = BakingViewData.Sender.AI,
                            )
                        } else {
                            entry
                        }
                    }
                } ?: run {
                    // Handle null output content as an error for the user message
                     _messages.value = _messages.value.map { entry ->
                        if (entry == userMessage) {
                            BakingViewData.Entry.Error(
                                message = "empty response",
                            )
                        } else {
                            entry
                        }
                    }
                }
            } catch (e: Exception) {
                // Update user message state to Error
                _messages.value = _messages.value.map { entry ->
                    if (entry == userMessage) {
                        BakingViewData.Entry.Error(
                            message = e.localizedMessage ?: "Unknown error occurred",
                        )
                    } else {
                        entry
                    }
                }
            }
        }
    }
}