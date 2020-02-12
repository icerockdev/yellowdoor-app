package com.icerock.yellowdoor.feature.personalInfo

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.ImageWidget
import dev.icerock.moko.widgets.constraint
import dev.icerock.moko.widgets.core.Image
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.core.Widget
import dev.icerock.moko.widgets.image
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.WidgetScreen
import dev.icerock.moko.widgets.screen.getViewModel
import dev.icerock.moko.widgets.screen.navigation.NavigationBar
import dev.icerock.moko.widgets.screen.navigation.NavigationItem
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.moko.widgets.screen.navigation.route
import dev.icerock.moko.widgets.style.view.SizeSpec
import dev.icerock.moko.widgets.style.view.WidgetSize


class PersonalInfoScreen(
    private val theme: Theme,
    private val strings: Strings,
    private val styles: Styles,
    private val images: Images,
    private val createViewModelBlock: (
        EventsDispatcher<PersonalInfoViewModel.EventsListener>
    ) -> PersonalInfoViewModel,
    private val closeRoute: Route<Unit>,
    private val newRoute: Route<Unit>
) : WidgetScreen<Args.Empty>(), NavigationItem, PersonalInfoViewModel.EventsListener {

    override val navigationBar: NavigationBar = NavigationBar.Normal(
        title = strings.title,
        styles = styles.navigationBar,
        backButton = NavigationBar.Normal.BarButton(
            icon = images.backImage,
            action = {
                //routeBack.route()
            }
        )
    )

    override fun createContentWidget() = with(theme) {
        val viewModel: PersonalInfoViewModel = getViewModel {
            createViewModelBlock(createEventsDispatcher())
        }

        constraint(size = WidgetSize.AsParent) {
            val avatarImage = +image(
                size = WidgetSize.Const(SizeSpec.Exact(48.0f), SizeSpec.Exact(48.0f)),
                image = viewModel.avatarImage,
                id = Id.AvatarImage
            )

            constraints {
                avatarImage.topToTop(root.safeArea).offset(32)
                avatarImage.leftToLeft(root.safeArea).offset(32)
            }
        }
    }

    class Styles(
        val navigationBar: NavigationBar.Normal.Styles
    )

    interface Strings {
        val title: StringDesc
        val uploadNewPhoto: StringDesc
        val notIndicated: StringDesc
        val birthday: StringDesc
        val region: StringDesc
        val city: StringDesc
        val education: StringDesc
        val aboutMe: StringDesc
        val photo: StringDesc
        val video: StringDesc
        val save: StringDesc
    }

    interface Images {
        val avatarPlaceholderImage: Image
        val backImage: ImageResource
    }

    object Id {
        object AvatarImage: ImageWidget.Id
    }
}