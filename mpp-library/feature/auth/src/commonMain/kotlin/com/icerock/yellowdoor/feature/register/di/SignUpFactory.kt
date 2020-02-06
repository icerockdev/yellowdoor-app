package com.icerock.yellowdoor.feature.register.di

import com.icerock.yellowdoor.feature.register.SignUpScreen
import com.icerock.yellowdoor.feature.register.SignUpViewModel
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.widgets.core.Theme


class SignUpFactory(
    val repository: SignUpRepository,
    val strings: SignUpScreen.Strings,
    val validation: SignUpViewModel.Validation
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
        styles: SignUpScreen.Styles
    ): SignUpScreen {
        return SignUpScreen(
            strings = strings,
            theme = theme,
            styles = styles,
            createViewModelBlock = this::createSignUpViewModel
        )
    }
}