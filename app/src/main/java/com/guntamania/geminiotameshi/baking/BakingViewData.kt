package com.guntamania.geminiotameshi.baking

import java.util.Date

data class BakingViewData(
    val messages: List<Entry>
) {
    data class Entry(
        val message: String,
        val date: Date,
        val sender: Sender
    )

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
