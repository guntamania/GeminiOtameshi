package com.guntamania.geminiotameshi.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.guntamania.geminiotameshi.ui.theme.GeminiOtameshiTheme

@Composable
fun BaseComponent(content: @Composable () -> Unit) {
    GeminiOtameshiTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            content()
        }
    }
}
