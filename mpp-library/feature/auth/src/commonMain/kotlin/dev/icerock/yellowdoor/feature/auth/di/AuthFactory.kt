/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.feature.auth.di

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.yellowdoor.feature.auth.model.AuthRepository
import dev.icerock.yellowdoor.feature.auth.model.PhoneNumber
import dev.icerock.yellowdoor.feature.auth.presentation.confirmation.ConfirmationScreen
import dev.icerock.yellowdoor.feature.auth.presentation.confirmation.ConfirmationViewModel
import dev.icerock.yellowdoor.feature.auth.presentation.signin.SignInScreen
import dev.icerock.yellowdoor.feature.auth.presentation.signin.SignInViewModel
import dev.icerock.yellowdoor.feature.auth.presentation.signup.SignUpScreen
import dev.icerock.yellowdoor.feature.auth.presentation.signup.SignUpViewModel

class AuthFactory(
    private val theme: Theme,
    private val authRepository: AuthRepository,
    private val strings: Strings,
    private val validation: Validation,
    private val images: Images
) {
    private fun createSignInViewModel(
        eventsDispatcher: EventsDispatcher<SignInViewModel.EventsListener>
    ): SignInViewModel {
        return SignInViewModel(
            authRepository = authRepository,
            eventsDispatcher = eventsDispatcher,
            validation = validation.signIn
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
            strings = strings.signIn
        )
    }

    private fun createConfirmationViewModel(
        eventsDispatcher: EventsDispatcher<ConfirmationViewModel.EventsListener>
    ): ConfirmationViewModel {
        return ConfirmationViewModel(
            authRepository = authRepository,
            eventsDispatcher = eventsDispatcher
        )
    }

    fun createConfirmationScreen(
        theme: Theme? = null,
        styles: ConfirmationScreen.Styles,
        routeNext: Route<Unit>,
        routeBack: Route<Unit>
    ): ConfirmationScreen {
        return ConfirmationScreen(
            theme = theme ?: this.theme,
            strings = strings.confirmation,
            images = images.confirmation,
            styles = styles,
            routeNext = routeNext,
            routeBack = routeBack,
            createViewModelBlock = this::createConfirmationViewModel
        )
    }

    private fun createSignUpViewModel(
        eventsDispatcher: EventsDispatcher<SignUpViewModel.EventsListener>
    ): SignUpViewModel {
        return SignUpViewModel(
            repository = authRepository,
            validation = validation.signUp,
            eventsDispatcher = eventsDispatcher
        )
    }

    fun createSignUpScreen(
        theme: Theme? = null,
        styles: SignUpScreen.Styles,
        userAgreementRoute: Route<Unit>,
        backRoute: Route<Unit>,
        smsCodeConfirmationRoute: Route<PhoneNumber>
    ): SignUpScreen {
        return SignUpScreen(
            theme = theme ?: this.theme,
            strings = strings.signUp,
            images = images.signUp,
            styles = styles,
            createViewModelBlock = this::createSignUpViewModel,
            userAgreementRoute = userAgreementRoute,
            routeBack = backRoute,
            smsCodeConfirmationRoute = smsCodeConfirmationRoute
        )
    }

    data class Strings(
        val signIn: SignInScreen.Strings,
        val signUp: SignUpScreen.Strings,
        val confirmation: ConfirmationScreen.Strings
    )

    data class Images(
        val signUp: SignUpScreen.Images,
        val confirmation: ConfirmationScreen.Images
    )

    data class Validation(
        val signIn: SignInViewModel.Validation,
        val signUp: SignUpViewModel.Validation
    )
}
