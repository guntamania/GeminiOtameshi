package com.guntamania.geminiotameshi.repository

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.guntamania.geminiotameshi.BuildConfig

class PromptRepository {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey
    )

    private val chat = generativeModel.startChat()

    suspend fun sendMessage(prompt: String): String? {
        val response = chat.sendMessage(
            content {
                text(prompt)
            }
        )
        return response.text
    }
}