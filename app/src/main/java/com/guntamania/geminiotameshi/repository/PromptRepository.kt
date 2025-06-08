package com.guntamania.geminiotameshi.repository

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.guntamania.geminiotameshi.BuildConfig

class PromptRepository {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey
    )

    suspend fun generateContent(prompt: String): String? {
        val response = generativeModel.generateContent(
            content {
                text(prompt)
            }
        )
        return response.text
    }
}