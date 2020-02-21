/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.feature.forgotPassword.presentation.submit

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.ButtonWidget
import dev.icerock.moko.widgets.InputWidget
import dev.icerock.moko.widgets.button
import dev.icerock.moko.widgets.constraint
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.core.Value
import dev.icerock.moko.widgets.input
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.WidgetScreen
import dev.icerock.moko.widgets.screen.getViewModel
import dev.icerock.moko.widgets.screen.listen
import dev.icerock.moko.widgets.screen.navigation.NavigationBar
import dev.icerock.moko.widgets.screen.navigation.NavigationItem
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.moko.widgets.screen.navigation.route
import dev.icerock.moko.widgets.style.input.InputType
import dev.icerock.moko.widgets.style.view.SizeSpec
import dev.icerock.moko.widgets.style.view.WidgetSize

class ForgotPasswordScreen(
    private val theme: Theme,
    private val strings: Strings,
    private val styles: Styles,
    images: Images,
    private val createViewModelBlock: (
        EventsDispatcher<ForgotPasswordViewModel.EventsListener>
    ) -> ForgotPasswordViewModel,
    private val nextRoute: Route<Unit>,
    private val backRoute: Route<Unit>
) : WidgetScreen<Args.Empty>(), NavigationItem, ForgotPasswordViewModel.EventsListener {

    override val navigationBar: NavigationBar = NavigationBar.Normal(
        title = strings.title.desc(),
        styles = styles.navigationBarStyle,
        backButton = NavigationBar.Normal.BarButton(
            icon = images.backImage,
            action = ::routeBack
        )
    )

    override val isDismissKeyboardOnTap: Boolean = true
    override val isKeyboardResizeContent: Boolean = true

    override fun createContentWidget() = with(theme) {
        val viewModel: ForgotPasswordViewModel = getViewModel {
            createViewModelBlock(createEventsDispatcher())
        }

        viewModel.eventsDispatcher.listen(this@ForgotPasswordScreen, this@ForgotPasswordScreen)

        constraint(size = WidgetSize.AsParent) {
            val phoneField = +input(
                category = styles.textField,
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.WrapContent),
                id = Id.PhoneField,
                field = viewModel.phoneField,
                label = const(strings.textFieldTitle.desc() as StringDesc),
                inputType = InputType.Phone()
            )

            val signInButton = +button(
                category = styles.yellowButton,
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.Exact(46.0f)),
                id = Id.NextButton,
                content = ButtonWidget.Content.Text(Value.data(strings.nextButtonTitle.desc() as StringDesc)),
                onTap = viewModel::didTapNextButton,
                enabled = viewModel.isFormValid
            )

            constraints {
                phoneField.leftToLeft(root.safeArea).offset(16)
                phoneField.topToTop(root.safeArea).offset(24)
                phoneField.rightToRight(root.safeArea).offset(16)

                signInButton.leftRightToLeftRight(phoneField)
                signInButton.bottomToBottom(root.safeArea).offset(16)
            }
        }
    }

    override fun routeNext() {
        nextRoute.route()
    }

    override fun routeBack() {
        backRoute.route()
    }

    class Styles(
        val textField: InputWidget.Category?,
        val yellowButton: ButtonWidget.Category?,
        val navigationBarStyle: NavigationBar.Normal.Styles
    )

    interface Strings {
        val title: StringResource
        val textFieldTitle: StringResource
        val nextButtonTitle: StringResource
    }

    interface Images {
        val backImage: ImageResource
    }

    object Id {
        object PhoneField : InputWidget.Id
        object NextButton : ButtonWidget.Id
    }
}
