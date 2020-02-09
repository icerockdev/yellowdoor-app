package com.icerock.yellowdoor.feature.passwordRecovery.di

import com.icerock.yellowdoor.feature.passwordRecovery.PasswordRecoveryScreen
import com.icerock.yellowdoor.feature.passwordRecovery.PasswordRecoveryViewModel
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.navigation.Route


class PasswordRecoveryFactory(
    private val repository: PasswordRecoveryRepository,
    private val strings: PasswordRecoveryScreen.Strings,
    private val validation: PasswordRecoveryViewModel.Validation
) {

    fun createPasswordRecoveryViewModel(
        eventsDispatcher: EventsDispatcher<PasswordRecoveryViewModel.EventsListener>
    ): PasswordRecoveryViewModel {
        return PasswordRecoveryViewModel(
            repository = repository,
            validation = validation,
            eventsDispatcher = eventsDispatcher
        )
    }

    fun createPasswordRecoveryScreen(
        theme: Theme,
        styles: PasswordRecoveryScreen.Styles,
        authRoute: Route<Unit>
    ): PasswordRecoveryScreen {
        return PasswordRecoveryScreen(
            theme = theme,
            styles = styles,
            strings = strings,
            authRoute = authRoute,
            createViewModelBlock = this::createPasswordRecoveryViewModel
        )
    }
}