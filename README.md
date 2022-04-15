# ViewModel Easy Factories

Use a simple syntax to manually create new ViewModel instances and provide all parameters directly instead of using [SavedStateHandle](https://developer.android.com/reference/androidx/lifecycle/SavedStateHandle).

The usage looks like this:

```kotlin
val mainViewModel by viewModel {
    MainViewModel(args)
}
```
Here `args` is a reference of some arbitrary type you wanna pass into the MainViewModel.

If you need to use SavedStateHandle for saving state inside a ViewModel use `savedStateViewModel` instead:

```kotlin
val savedStateMainViewModel by savedStateViewModel { savedStateHandle ->
    SavedStateMainViewModel(args, savedStateHandle)
}
```

These methods are provided as extensions for [ViewModelStoreOwner](https://developer.android.com/reference/androidx/lifecycle/ViewModelStoreOwner), which means they can be with Activity, Fragment or [NavBackStackEntry](https://developer.android.com/reference/androidx/navigation/NavBackStackEntry) as a receiver. Or simply inside you Activity or Fragment classes.

You may also find it convenient to use these methods together with [Dagger](https://dagger.dev/) and [@AssistedInject](https://dagger.dev/dev-guide/assisted-injection.html). You may find the samples for Dagger/Hilt/Anvil in my navigation library project here: [samples-di](https://github.com/olshevski/compose-navigation-reimagined/tree/main/samples-di).

**Note:** As it is intended as an alternative to SavedStateHandle for passing parameters into a ViewModel, SavedStateHandle instances are **not pre-filled** with arguments from Activity, Fragment and NavBackStackEntry.

### Jetpack Compose

There are similar methods for you to use inside your composables:

```kotlin
val composeViewModel = viewModel {
    ComposeViewModel(args)
}

val savedStateComposeViewModel = savedStateViewModel { savedStateHandle ->
    SavedStateComposeViewModel(args, savedStateHandle)
}
```

## Setup

Just add:

```kotlin
implementation("dev.olshevski.viewmodel:easy-factories:1.0.0")
```

For Compose use this instead:

```kotlin
implementation("dev.olshevski.viewmodel:easy-factories-compose:1.0.0")
```
