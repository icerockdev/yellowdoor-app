/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.feature.selection.di

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.yellowdoor.feature.selection.model.SelectionSource
import dev.icerock.yellowdoor.feature.selection.presentation.SelectItemScreen
import dev.icerock.yellowdoor.feature.selection.presentation.SelectItemViewModel

class SelectionFactory(
    private val theme: Theme,
    private val source: SelectionSource,
    private val strings: SelectItemScreen.Strings,
    private val images: SelectItemScreen.Images
) {
    private fun createSelectItemViewModel(
        eventsDispatcher: EventsDispatcher<SelectItemViewModel.EventsListener>
    ): SelectItemViewModel {
        return SelectItemViewModel(
            repository = source,
            eventsDispatcher = eventsDispatcher
        )
    }

    fun createSelectItemScreen(
        theme: Theme? = null,
        styles: SelectItemScreen.Styles,
        backRoute: Route<Unit>
    ): SelectItemScreen {
        return SelectItemScreen(
            theme = theme ?: this.theme,
            strings = strings,
            styles = styles,
            images = images,
            backRoute = backRoute,
            createViewModelBlock = this::createSelectItemViewModel
        )
    }
}
