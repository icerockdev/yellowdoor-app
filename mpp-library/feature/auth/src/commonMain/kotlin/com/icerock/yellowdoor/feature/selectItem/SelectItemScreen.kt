package com.icerock.yellowdoor.feature.selectItem

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.constraint
import dev.icerock.moko.widgets.core.Image
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.WidgetScreen
import dev.icerock.moko.widgets.screen.getViewModel
import dev.icerock.moko.widgets.screen.navigation.NavigationBar
import dev.icerock.moko.widgets.screen.navigation.NavigationItem
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.moko.widgets.screen.navigation.route
import dev.icerock.moko.widgets.style.view.WidgetSize


class SelectItemScreen(
    private val theme: Theme,
    private val styles: Styles,
    private val strings: Strings,
    private val images: Images,
    private val createViewModelBlock: (EventsDispatcher<SelectItemViewModel.EventsListener>) -> SelectItemViewModel,
    private val backRoute: Route<Unit>
) : WidgetScreen<Args.Empty>(), NavigationItem,
    SelectItemViewModel.EventsListener {

    private val viewModel: SelectItemViewModel = getViewModel {
        createViewModelBlock(createEventsDispatcher())
    }

    override val navigationBar: NavigationBar = NavigationBar.Normal(
        title = strings.title.desc(),
        styles = styles.navigationBar,
        backButton = NavigationBar.Normal.BarButton(
            icon = images.backImage,
            action = {
                backRoute.route()
            }
        )
    )

    override fun createContentWidget() = with(theme) {
        constraint(size = WidgetSize.AsParent) {
            constraints {

            }
        }
    }

    class Styles(
        val navigationBar: NavigationBar.Normal.Styles
    )

    interface Strings {
        val title: StringResource
    }

    interface Images {
        val backImage: ImageResource
        val checkmarkImage: Image
    }

    object Id {

    }
}