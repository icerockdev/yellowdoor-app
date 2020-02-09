package com.icerock.yellowdoor.feature.smsCodeConfirmation.di

import com.icerock.yellowdoor.feature.smsCodeConfirmation.SMSCodeConfirmationScreen
import com.icerock.yellowdoor.feature.smsCodeConfirmation.SMSCodeConfirmationViewModel
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.navigation.Route


class SMSCodeConfirmationFactory(
    private val repository: SMSCodeConfirmationRepository,
    private val strings: SMSCodeConfirmationScreen.Strings

) {
    fun createSMSCodeConfirmationViewModel(
        eventsDispatcher: EventsDispatcher<SMSCodeConfirmationViewModel.EventsListener>
    ): SMSCodeConfirmationViewModel {
        return SMSCodeConfirmationViewModel(
            repository = repository,
            eventsDispatcher = eventsDispatcher
        )
    }

    fun createSMSCodeConfirmationScreen(
        theme: Theme,
        styles: SMSCodeConfirmationScreen.Styles,
        routeNext: Route<Unit>
    ): SMSCodeConfirmationScreen {
        return SMSCodeConfirmationScreen(
            theme = theme,
            strings = strings,
            styles = styles,
            routeNext = routeNext,
            createViewModelBlock = this::createSMSCodeConfirmationViewModel
        )
    }
}