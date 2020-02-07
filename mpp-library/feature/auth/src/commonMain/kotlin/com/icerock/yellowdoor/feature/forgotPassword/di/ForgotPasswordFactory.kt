package com.icerock.yellowdoor.feature.forgotPassword.di

import com.icerock.yellowdoor.feature.forgotPassword.ForgotPasswordScreen
import com.icerock.yellowdoor.feature.forgotPassword.ForgotPasswordViewModel
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.navigation.Route


class ForgotPasswordFactory(
    private val repository: ForgotPasswordRepository,
    private val strings: ForgotPasswordScreen.Strings,
    private val validation: ForgotPasswordViewModel.Validation
) {

    fun createForgotPasswordViewModel(
        eventsDispatcher: EventsDispatcher<ForgotPasswordViewModel.EventsListener>
    ): ForgotPasswordViewModel {
        return ForgotPasswordViewModel(
            repository = repository,
            validation = validation,
            eventsDispatcher = eventsDispatcher
        )
    }

    fun createForgotPasswordScreen(
        theme: Theme,
        nextRoute: Route<Unit>,
        styles: ForgotPasswordScreen.Styles
    ): ForgotPasswordScreen {
        return ForgotPasswordScreen(
            theme = theme,
            strings = strings,
            styles = styles,
            nextRoute = nextRoute,
            createViewModelBlock = this::createForgotPasswordViewModel
        )
    }
}