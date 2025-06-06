package com.guntamania.geminiotameshi.baking

import java.util.Date

data class BakingViewData(
    val messages: List<Entry>
) {
    data class Entry (
        val message: String,
        val date: Date
    )
}
