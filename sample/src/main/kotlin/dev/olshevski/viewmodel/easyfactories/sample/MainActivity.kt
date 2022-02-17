package dev.olshevski.viewmodel.easyfactories.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.olshevski.viewmodel.easyfactories.sample.theme.ViewModelEasyFactoriesTheme
import dev.olshevski.viewmodel.easyfactories.savedStateViewModel
import dev.olshevski.viewmodel.easyfactories.viewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModel {
        MainViewModel("param_value")
    }

    private val savedStateMainViewModel by savedStateViewModel { savedStateHandle ->
        SavedStateMainViewModel("param_value", savedStateHandle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // access view models to "un-lazy" them
        mainViewModel
        savedStateMainViewModel

        setContent {
            ViewModelEasyFactoriesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Screen()
                }
            }
        }
    }
}

@Composable
fun Screen() {
    Column(Modifier.fillMaxSize()) {
        Text(
            text = "Hello, View Models!",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            style = MaterialTheme.typography.h5
        )

        val composeViewModel = viewModel {
            ComposeViewModel("param_value")
        }

        val savedStateComposeViewModel = savedStateViewModel { savedStateHandle ->
            SavedStateComposeViewModel("param_value", savedStateHandle)
        }

    }
}
