/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.feature.selection.presentation

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.units.TableUnitItem
import dev.icerock.moko.widgets.ListWidget
import dev.icerock.moko.widgets.StatefulWidget
import dev.icerock.moko.widgets.TextWidget
import dev.icerock.moko.widgets.container
import dev.icerock.moko.widgets.core.Image
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.list
import dev.icerock.moko.widgets.progressBar
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.WidgetScreen
import dev.icerock.moko.widgets.screen.getViewModel
import dev.icerock.moko.widgets.screen.navigation.NavigationBar
import dev.icerock.moko.widgets.screen.navigation.NavigationItem
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.moko.widgets.screen.navigation.route
import dev.icerock.moko.widgets.stateful
import dev.icerock.moko.widgets.style.view.WidgetSize
import dev.icerock.yellowdoor.feature.selection.units.SelectItemUnit

class SelectItemScreen(
    private val theme: Theme,
    private val styles: Styles,
    strings: Strings,
    private val images: Images,
    private val createViewModelBlock: (EventsDispatcher<SelectItemViewModel.EventsListener>) -> SelectItemViewModel,
    backRoute: Route<Unit>
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
            action = backRoute::route
        )
    )

    override fun createContentWidget() = with(theme) {
        stateful(
            size = WidgetSize.AsParent,
            state = viewModel.items,
            id = Id.Stateful,
            data = { items: LiveData<List<SelectItemUnit.Data>?> ->
                list(
                    size = WidgetSize.AsParent,
                    id = Id.Table,
                    items = items.map { list: List<SelectItemUnit.Data>? ->
                        list?.map { data: SelectItemUnit.Data ->
                            SelectItemUnit(
                                theme = theme,
                                selectedImage = images.checkmarkImage,
                                titleCategory = styles.itemTitle,
                                data = data
                            ) as TableUnitItem
                        } ?: listOf()
                    }
                )
            },
            empty = {
                container(size = WidgetSize.AsParent) {

                }
            },
            loading = {
                container(size = WidgetSize.AsParent) {
                    center {
                        progressBar(size = WidgetSize.WrapContent)
                    }
                }
            },
            error = {
                container(size = WidgetSize.AsParent) {

                }
            }
        )
    }

    class Styles(
        val navigationBar: NavigationBar.Normal.Styles,
        val itemTitle: TextWidget.Category
    )

    interface Strings {
        val title: StringResource
    }

    interface Images {
        val backImage: ImageResource
        val checkmarkImage: Image
    }

    object Id {
        object Stateful : StatefulWidget.Id
        object Table : ListWidget.Id
    }
}
