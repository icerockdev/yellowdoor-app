package com.icerock.yellowdoor.feature.selectItem.di

import com.icerock.yellowdoor.feature.selectItem.SelectItemScreen
import com.icerock.yellowdoor.feature.selectItem.SelectItemViewModel
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.navigation.Route


class SelectItemFactory(
    private val repository: SelectItemRepository,
    private val strings: SelectItemScreen.Strings,
    private val images: SelectItemScreen.Images
) {

    fun createSelectItemViewModel(
        eventsDispatcher: EventsDispatcher<SelectItemViewModel.EventsListener>
    ): SelectItemViewModel {
        return SelectItemViewModel(
            repository = repository,
            eventsDispatcher = eventsDispatcher
        )
    }

    fun createSelectItemScreen(
        theme: Theme,
        styles: SelectItemScreen.Styles,
        backRoute: Route<Unit>
    ): SelectItemScreen {
        return SelectItemScreen(
            theme = theme,
            strings = strings,
            styles = styles,
            images = images,
            backRoute = backRoute,
            createViewModelBlock = this::createSelectItemViewModel
        )
    }
}