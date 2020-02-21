/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.feature.profile.di

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.yellowdoor.feature.profile.model.PersonalInfoRepository
import dev.icerock.yellowdoor.feature.profile.presentation.PersonalInfoScreen
import dev.icerock.yellowdoor.feature.profile.presentation.PersonalInfoViewModel

class ProfileFactory(
    private val theme: Theme,
    private val repository: PersonalInfoRepository,
    private val strings: PersonalInfoScreen.Strings,
    private val images: PersonalInfoScreen.Images
) {

    private fun createPersonalInfoViewModel(
        eventsDispatcher: EventsDispatcher<PersonalInfoViewModel.EventsListener>
    ): PersonalInfoViewModel {
        return PersonalInfoViewModel(
            repository = repository,
            images = images,
            eventsDispatcher = eventsDispatcher
        )
    }

    fun createPersonalInfoScreen(
        theme: Theme? = null,
        styles: PersonalInfoScreen.Styles,
        closeRoute: Route<Unit>,
        newsRoute: Route<Unit>,
        regionRoute: Route<Unit>
    ): PersonalInfoScreen {
        return PersonalInfoScreen(
            theme = theme ?: this.theme,
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
