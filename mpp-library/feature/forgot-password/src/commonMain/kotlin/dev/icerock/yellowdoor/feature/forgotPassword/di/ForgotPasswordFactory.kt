/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.feature.forgotPassword.di

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.yellowdoor.feature.forgotPassword.model.ForgotPasswordRepository
import dev.icerock.yellowdoor.feature.forgotPassword.presentation.recovery.PasswordRecoveryScreen
import dev.icerock.yellowdoor.feature.forgotPassword.presentation.recovery.PasswordRecoveryViewModel
import dev.icerock.yellowdoor.feature.forgotPassword.presentation.submit.ForgotPasswordScreen
import dev.icerock.yellowdoor.feature.forgotPassword.presentation.submit.ForgotPasswordViewModel

class ForgotPasswordFactory(
    private val theme: Theme,
    private val repository: ForgotPasswordRepository,
    private val strings: Strings,
    private val validation: Validation,
    private val images: Images
) {

    private fun createForgotPasswordViewModel(
        eventsDispatcher: EventsDispatcher<ForgotPasswordViewModel.EventsListener>
    ): ForgotPasswordViewModel {
        return ForgotPasswordViewModel(
            repository = repository,
            validation = validation,
            eventsDispatcher = eventsDispatcher
        )
    }

    fun createForgotPasswordScreen(
        theme: Theme? = null,
        nextRoute: Route<Unit>,
        backRoute: Route<Unit>,
        styles: ForgotPasswordScreen.Styles
    ): ForgotPasswordScreen {
        return ForgotPasswordScreen(
            theme = theme ?: this.theme,
            strings = strings,
            styles = styles,
            nextRoute = nextRoute,
            backRoute = backRoute,
            images = images,
            createViewModelBlock = this::createForgotPasswordViewModel
        )
    }

    private fun createPasswordRecoveryViewModel(
        eventsDispatcher: EventsDispatcher<PasswordRecoveryViewModel.EventsListener>
    ): PasswordRecoveryViewModel {
        return PasswordRecoveryViewModel(
            repository = repository,
            validation = validation,
            eventsDispatcher = eventsDispatcher
        )
    }

    fun createPasswordRecoveryScreen(
        theme: Theme? = null,
        styles: PasswordRecoveryScreen.Styles,
        authRoute: Route<Unit>,
        backRoute: Route<Unit>
    ): PasswordRecoveryScreen {
        return PasswordRecoveryScreen(
            theme = theme ?: this.theme,
            styles = styles,
            strings = strings,
            authRoute = authRoute,
            backRoute = backRoute,
            images = images,
            createViewModelBlock = this::createPasswordRecoveryViewModel
        )
    }

    interface Strings : PasswordRecoveryScreen.Strings, ForgotPasswordScreen.Strings
    interface Images : PasswordRecoveryScreen.Images, ForgotPasswordScreen.Images
    interface Validation : PasswordRecoveryViewModel.Validation, ForgotPasswordViewModel.Validation
}
