package com.icerock.yellowdoor.feature.personalInfo.di

import com.icerock.yellowdoor.feature.personalInfo.PersonalInfoScreen
import com.icerock.yellowdoor.feature.personalInfo.PersonalInfoViewModel
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.navigation.Route


class PersonalInfoFactory(
    private val repository: PersonalInfoRepository,
    private val strings: PersonalInfoScreen.Strings,
    private val images: PersonalInfoScreen.Images
) {

    fun createPersonalInfoViewModel(
        eventsDispatcher: EventsDispatcher<PersonalInfoViewModel.EventsListener>
    ): PersonalInfoViewModel {
        return PersonalInfoViewModel(
            repository = repository,
            images = images,
            eventsDispatcher = eventsDispatcher
        )
    }

    fun createPersonalInfoScreen(
        theme: Theme,
        styles: PersonalInfoScreen.Styles,
        closeRoute: Route<Unit>,
        newsRoute: Route<Unit>,
        regionRoute: Route<Unit>
    ): PersonalInfoScreen {
        return PersonalInfoScreen(
            theme = theme,
            styles = styles,
            closeRoute = closeRoute,
            newsRoute = newsRoute,
            createViewModelBlock = this::createPersonalInfoViewModel,
            images = images,
            strings = strings,
            regionRoute = regionRoute
        )
    }
}