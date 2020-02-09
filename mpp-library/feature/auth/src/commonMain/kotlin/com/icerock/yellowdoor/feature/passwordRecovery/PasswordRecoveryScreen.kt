package com.icerock.yellowdoor.feature.passwordRecovery

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.*
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


class PasswordRecoveryScreen(
    private val theme: Theme,
    private val strings: Strings,
    private val styles: Styles,
    private val createViewModelBlock: (
        EventsDispatcher<PasswordRecoveryViewModel.EventsListener>
    ) -> PasswordRecoveryViewModel,
    private val authRoute: Route<Unit>
) : WidgetScreen<Args.Empty>(), NavigationItem, PasswordRecoveryViewModel.EventsListener {

    override val navigationBar: NavigationBar = NavigationBar.Normal(strings.title.desc())
    override val isDismissKeyboardOnTap: Boolean = true
    override val isKeyboardResizeContent: Boolean = true

    override fun createContentWidget() = with(theme) {
        val viewModel: PasswordRecoveryViewModel = getViewModel {
            createViewModelBlock(createEventsDispatcher())
        }

        viewModel.eventsDispatcher.listen(this@PasswordRecoveryScreen, this@PasswordRecoveryScreen)

        constraint(size = WidgetSize.AsParent) {
            val newPasswordField = +input(
                category = styles.textField,
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.WrapContent),
                id = Id.NewPasswordTextField,
                field = viewModel.passwordField,
                label = const(strings.newPassword.desc() as StringDesc),
                inputType = InputType.PASSWORD
            )

            val confirmPasswordField = +input(
                category = styles.textField,
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.WrapContent),
                id = Id.ConfirmPasswordTextField,
                field = viewModel.passwordConfirmationField,
                label = const(strings.newPasswordRepeat.desc() as StringDesc),
                inputType = InputType.PASSWORD
            )

            val savePasswordButton = +button(
                category = styles.yellowButton,
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.Exact(46.0f)),
                id = Id.SavePasswordButton,
                content = ButtonWidget.Content.Text(Value.data(strings.savePassword.desc() as StringDesc)),
                onTap = viewModel::didTapSavePasswordButton,
                enabled = viewModel.isFormValid
            )

            constraints {
                newPasswordField.topToTop(root.safeArea).offset(24)
                newPasswordField.leftToLeft(root.safeArea).offset(16)
                newPasswordField.rightToRight(root.safeArea).offset(16)

                confirmPasswordField.topToBottom(newPasswordField).offset(24)
                confirmPasswordField.leftRightToLeftRight(newPasswordField)

                savePasswordButton.bottomToBottom(root.safeArea).offset(16)
                savePasswordButton.leftRightToLeftRight(newPasswordField)
            }
        }
    }

    override fun routeToAuth() {
        authRoute.route(this)
    }

    class Styles(
        val textField: InputWidget.Category?,
        val yellowButton: ButtonWidget.Category?
    )

    interface Strings {
        val title: StringResource
        val newPassword: StringResource
        val newPasswordRepeat: StringResource
        val savePassword: StringResource
    }

    object Id {
        object NewPasswordTextField : InputWidget.Id
        object ConfirmPasswordTextField : InputWidget.Id
        object SavePasswordButton : ButtonWidget.Id
    }
}