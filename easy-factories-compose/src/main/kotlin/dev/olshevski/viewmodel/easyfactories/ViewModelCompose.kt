package dev.olshevski.viewmodel.easyfactories

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

/**
 * Returns an existing [ViewModel] or creates a new one in the given owner (usually, a fragment or
 * an activity), defaulting to the owner provided by [LocalViewModelStoreOwner].
 *
 * The created [ViewModel] is associated with the given [viewModelStoreOwner] and will be retained
 * as long as the owner is alive (e.g. if it is an activity, until it is
 * finished or process is killed).
 *
 * In order to create a ViewModel with [SavedStateHandle] use [savedStateViewModel] instead.
 *
 * @param viewModelStoreOwner the owner of the [ViewModel] that controls the scope and lifetime
 * of the returned [ViewModel]. Defaults to using [LocalViewModelStoreOwner].
 *
 * @param key the key to use to identify the [ViewModel]
 *
 * @param factory trailing lambda that should be used to create the [ViewModel]
 *
 * @return a [ViewModel] that is an instance of the given [VM] type
 */
@Composable
inline fun <reified VM : ViewModel> viewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    key: String? = null,
    crossinline factory: @DisallowComposableCalls () -> VM
): VM = remember(viewModelStoreOwner, key) {
    ViewModelProvider(
        owner = viewModelStoreOwner,
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return factory() as T
            }
        }
    ).run {
        if (key == null) {
            get(VM::class.java)
        } else {
            get(key, VM::class.java)
        }
    }
}

/**
 * Returns an existing [ViewModel] or creates a new one in the given owner (usually, a fragment or
 * an activity), defaulting to the owner provided by [LocalViewModelStoreOwner].
 *
 * The created [ViewModel] is associated with the given [viewModelStoreOwner] and will be retained
 * as long as the owner is alive (e.g. if it is an activity, until it is
 * finished or process is killed).
 *
 * @param viewModelStoreOwner the owner of the [ViewModel] that controls the scope and lifetime
 * of the returned [ViewModel]. Defaults to using [LocalViewModelStoreOwner].
 *
 * @param key the key to use to identify the [ViewModel]
 *
 * @param factory trailing lambda that should be used to create the [ViewModel]. Provides
 * [SavedStateHandle] associated with this ViewModel as a parameter.
 *
 * @return a [ViewModel] that is an instance of the given [VM] type
 */
@Composable
inline fun <reified VM : ViewModel> savedStateViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    key: String? = null,
    crossinline factory: @DisallowComposableCalls (SavedStateHandle) -> VM
): VM {
    val savedStateRegistryOwner = LocalSavedStateRegistryOwner.current
    return remember(viewModelStoreOwner, savedStateRegistryOwner, key) {
        ViewModelProvider(
            owner = viewModelStoreOwner,
            factory = object : AbstractSavedStateViewModelFactory(savedStateRegistryOwner, null) {
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    @Suppress("UNCHECKED_CAST")
                    return factory(handle) as T
                }
            }
        ).run {
            if (key == null) {
                get(VM::class.java)
            } else {
                get(key, VM::class.java)
            }
        }
    }
}