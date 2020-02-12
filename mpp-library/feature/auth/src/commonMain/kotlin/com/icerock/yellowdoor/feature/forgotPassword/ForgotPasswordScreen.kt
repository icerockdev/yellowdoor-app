package com.icerock.yellowdoor.feature.forgotPassword

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.*
import dev.icerock.moko.widgets.core.Image
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.core.Value
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
    private val images: Images,
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
            action = {
                routeBack()
            }
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
                inputType = InputType.PHONE
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
        nextRoute.route(arg = Unit)
    }

    override fun routeBack() {
        backRoute.route(arg = Unit)
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