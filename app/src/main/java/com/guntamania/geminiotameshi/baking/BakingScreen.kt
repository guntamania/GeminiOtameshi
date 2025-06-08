package com.guntamania.geminiotameshi.baking

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.guntamania.geminiotameshi.R
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BakingScreen(
    navController: NavController,
    bakingViewModel: BakingViewModel = viewModel()
) {
    val placeholderPrompt = stringResource(R.string.prompt_placeholder)
    val placeholderResult = emptyList<BakingViewData.Entry>()
    var prompt by rememberSaveable { mutableStateOf(placeholderPrompt) }
    val uiState by bakingViewModel.uiState.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.baking_title),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {

            Row(
                modifier = Modifier.padding(all = 16.dp)
            ) {
                TextField(
                    value = prompt,
                    label = { Text(stringResource(R.string.label_prompt)) },
                    onValueChange = { prompt = it },
                    modifier = Modifier
                        .weight(0.8f)
                        .padding(end = 16.dp)
                        .align(Alignment.CenterVertically)
                )

                Button(
                    onClick = {
                        bakingViewModel.sendPrompt(prompt)
                    },
                    enabled = prompt.isNotEmpty(),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                ) {
                    Text(text = stringResource(R.string.action_go))
                }
            }

            if (uiState is BakingUiState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                var textColor = MaterialTheme.colorScheme.onSurface
                val messages =
                    when (uiState) {
                        is BakingUiState.Error -> {
                            textColor = MaterialTheme.colorScheme.error
                            listOf(
                                BakingViewData.Entry(
                                    date = Date(),
                                    message = (uiState as BakingUiState.Error).errorMessage
                                )
                            )
                        }

                        is BakingUiState.Success -> {
                            textColor = MaterialTheme.colorScheme.onSurface
                            (uiState as BakingUiState.Success).data.messages
                        }

                        else -> {
                            emptyList()
                        }
                    }

                val scrollState = rememberScrollState()

                LazyColumn(Modifier.fillMaxSize()) {
                    items(messages.size) {
                        ListItem(
                            result = messages.get(it),
                            scrollState = scrollState,
                            textColor = textColor
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun ListItem(
    result: BakingViewData.Entry,
    modifier: Modifier = Modifier,
    scrollState: ScrollState,
    textColor: Color
) {
    Row(modifier.fillMaxWidth()) {
        Text(
            text = result.message,
            textAlign = TextAlign.Start,
            color = textColor,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun BakingScreenPreview() {
    BakingScreen(
        rememberNavController()
    )
}