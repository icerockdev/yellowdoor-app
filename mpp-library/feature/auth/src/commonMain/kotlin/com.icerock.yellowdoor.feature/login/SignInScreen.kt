package com.icerock.yellowdoor.feature.login

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.WidgetScreen
import dev.icerock.moko.widgets.screen.navigation.NavigationBar
import dev.icerock.moko.widgets.screen.navigation.NavigationItem


class SignInScreen(
    private val theme: Theme,
    private val createViewModel: (
        EventsDispatcher<SignInViewModel.EventsListener>
    ) -> SignInViewModel
): WidgetScreen<Args.Empty>(), NavigationItem {

    override val navigationBar: NavigationBar get() = NavigationBar.None

}