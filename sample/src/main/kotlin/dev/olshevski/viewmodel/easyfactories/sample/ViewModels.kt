package dev.olshevski.viewmodel.easyfactories.sample

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class MainViewModel(param: String) : ViewModel() {

    init {
        Log.v("MainViewModel", "param=$param")
    }

}

class SavedStateMainViewModel(param: String, savedStateHandle: SavedStateHandle) : ViewModel() {

    init {
        Log.v("SavedStateMainViewModel", "param=$param")
        Log.v("SavedStateMainViewModel", "saved=${savedStateHandle.get<String>("saved")}")
        savedStateHandle["saved"] = "saved_value"
    }

}

class ComposeViewModel(param: String) : ViewModel() {

    init {
        Log.v("ComposeViewModel", "param=$param")
    }

}

class SavedStateComposeViewModel(param: String, savedStateHandle: SavedStateHandle) : ViewModel() {

    init {
        Log.v("SStateComposeViewModel", "param=$param")
        Log.v("SStateComposeViewModel", "saved=${savedStateHandle.get<String>("saved")}")
        savedStateHandle["saved"] = "saved_value"
    }

}