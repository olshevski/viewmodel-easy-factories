package dev.olshevski.viewmodel.easyfactories

import androidx.annotation.MainThread
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.savedstate.SavedStateRegistryOwner

/**
 * Returns a [Lazy] delegate to an existing [ViewModel] or a new one in the receiver
 * [ViewModelStoreOwner] (usually, a fragment or an activity).
 *
 * The created [ViewModel] is associated with the receiver [ViewModelStoreOwner] and will be
 * retained as long as the owner is alive (e.g. if it is an activity, until it is
 * finished or process is killed).
 *
 * In order to create a ViewModel with [SavedStateHandle] use [savedStateViewModel] instead.
 *
 * @param key the key to use to identify the [ViewModel]
 *
 * @param factory trailing lambda that should be used to create the [ViewModel]
 *
 * @return a [Lazy] delegate to a [ViewModel] that is an instance of the given [VM] type
 */
@MainThread
inline fun <reified VM : ViewModel> ViewModelStoreOwner.viewModel(
    key: String? = null,
    crossinline factory: () -> VM
): Lazy<VM> = lazy(LazyThreadSafetyMode.NONE) {
    ViewModelProvider(
        owner = this,
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
 * Returns a [Lazy] delegate to an existing [ViewModel] or a new one in the receiver
 * [ViewModelStoreOwner] (usually, a fragment or an activity).
 *
 * The created [ViewModel] is associated with the receiver [ViewModelStoreOwner] and will be
 * retained as long as the owner is alive (e.g. if it is an activity, until it is
 * finished or process is killed).
 *
 * In order to create a ViewModel with [SavedStateHandle] use [savedStateViewModel] instead.
 *
 * @param key the key to use to identify the [ViewModel]
 *
 * @param factory trailing lambda that should be used to create the [ViewModel]. Provides
 * [SavedStateHandle] associated with this ViewModel as a parameter.
 *
 * @return a [Lazy] delegate to a [ViewModel] that is an instance of the given [VM] type
 */
@MainThread
inline fun <reified VM : ViewModel> ViewModelStoreOwner.savedStateViewModel(
    key: String? = null,
    crossinline factory: (SavedStateHandle) -> VM
): Lazy<VM> = lazy(LazyThreadSafetyMode.NONE) {
    val savedStateRegistryOwner = this as? SavedStateRegistryOwner
        ?: error("ViewModelStoreOwner should also implement SavedStateRegistryOwner")
    ViewModelProvider(
        owner = this,
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