package com.guntamania.geminiotameshi.baking

import java.util.Date

data class BakingViewData(
    val messages: List<Entry>
) {
    sealed interface Entry {
        data class Message(
            val message: String,
            val date: Date,
            val sender: Sender,
        ): Entry
        data object Loading: Entry
        data class Error(
            val message: String
        ): Entry
    }

    enum class Sender {
        AI,
        SYSTEM,
        YOU;

        val userName: String
            get() = when (this) {
                AI -> "AI"
                SYSTEM -> "SYSTEM"
                YOU -> "YOU"
            }
    }
}
