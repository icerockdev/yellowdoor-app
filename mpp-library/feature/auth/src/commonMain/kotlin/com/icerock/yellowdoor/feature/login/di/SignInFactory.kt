package com.icerock.yellowdoor.feature.login.di

import com.icerock.yellowdoor.feature.login.SignInScreen
import com.icerock.yellowdoor.feature.login.SignInViewModel
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.navigation.Route


class SignInFactory(
    val signInRepository: SignInRepository,
    val strings: SignInScreen.Strings,
    val validation: SignInViewModel.Validation
) {
    fun createSignInViewModel(
        eventsDispatcher: EventsDispatcher<SignInViewModel.EventsListener>
    ): SignInViewModel {

        return SignInViewModel(
            repository = signInRepository,
            eventsDispatcher = eventsDispatcher,
            validation = validation
        )
    }

    fun createSignInScreen(
        theme: Theme,
        styles: SignInScreen.Styles,
        signUpRoute: Route<Unit>,
        forgotPasswordRoute: Route<Unit>
    ): SignInScreen {
        return SignInScreen(
            theme = theme,
            styles = styles,
            createViewModelBlock = this::createSignInViewModel,
            signUpRoute = signUpRoute,
            forgotPasswordRoute = forgotPasswordRoute,
            strings = strings
        )
    }
}