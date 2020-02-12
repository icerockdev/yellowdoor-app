package com.icerock.yellowdoor.feature.register.di

import com.icerock.yellowdoor.feature.register.SignUpScreen
import com.icerock.yellowdoor.feature.register.SignUpViewModel
import com.icerock.yellowdoor.feature.smsCodeConfirmation.SMSCodeConfirmationScreen
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.navigation.Route


class SignUpFactory(
    val repository: SignUpRepository,
    val strings: SignUpScreen.Strings,
    val validation: SignUpViewModel.Validation,
    val images: SignUpScreen.Images
) {

    fun createSignUpViewModel(
        eventsDispatcher: EventsDispatcher<SignUpViewModel.EventsListener>
    ): SignUpViewModel {
        return SignUpViewModel(
            repository = repository,
            validation = validation,
            eventsDispatcher = eventsDispatcher
        )
    }

    fun createSignUpScreen(
        theme: Theme,
        styles: SignUpScreen.Styles,
        userAgreementRoute: Route<Unit>,
        backRoute: Route<Unit>,
        smsCodeConfirmationRoute: Route<SMSCodeConfirmationScreen.Arg>
    ): SignUpScreen {
        return SignUpScreen(
            strings = strings,
            theme = theme,
            images = images,
            styles = styles,
            createViewModelBlock = this::createSignUpViewModel,
            userAgreementRoute = userAgreementRoute,
            routeBack = backRoute,
            smsCodeConfirmationRoute = smsCodeConfirmationRoute
        )
    }
}