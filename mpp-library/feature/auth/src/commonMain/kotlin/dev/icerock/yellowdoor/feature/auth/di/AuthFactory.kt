/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.feature.auth.di

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.yellowdoor.feature.auth.model.AuthRepository
import dev.icerock.yellowdoor.feature.auth.presentation.SignInScreen
import dev.icerock.yellowdoor.feature.auth.presentation.SignInViewModel

class AuthFactory(
    private val theme: Theme,
    private val authRepository: AuthRepository,
    private val strings: SignInScreen.Strings,
    private val validation: SignInViewModel.Validation
) {
    private fun createSignInViewModel(
        eventsDispatcher: EventsDispatcher<SignInViewModel.EventsListener>
    ): SignInViewModel {
        return SignInViewModel(
            authRepository = authRepository,
            eventsDispatcher = eventsDispatcher,
            validation = validation
        )
    }

    fun createSignInScreen(
        theme: Theme? = null,
        styles: SignInScreen.Styles,
        signUpRoute: Route<Unit>,
        forgotPasswordRoute: Route<Unit>
    ): SignInScreen {
        return SignInScreen(
            theme = theme ?: this.theme,
            styles = styles,
            createViewModelBlock = this::createSignInViewModel,
            signUpRoute = signUpRoute,
            forgotPasswordRoute = forgotPasswordRoute,
            strings = strings
        )
    }
}
