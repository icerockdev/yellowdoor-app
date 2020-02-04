package com.icerock.yellowdoor.feature.login

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
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
import dev.icerock.moko.widgets.style.input.InputType
import dev.icerock.moko.widgets.style.view.SizeSpec
import dev.icerock.moko.widgets.style.view.WidgetSize


class SignInScreen(
    private val theme: Theme,
    private val styles: Styles,
    private val createViewModelBlock: (
        EventsDispatcher<SignInViewModel.EventsListener>) -> SignInViewModel,
    private val signUpRoute: Route<Unit>,
    private val forgotPasswordRoute: Route<Unit>
): WidgetScreen<Args.Empty>(), NavigationItem, SignInViewModel.EventsListener {

    class Styles(
        val titleText: TextWidget.Category?,
        val textField: InputWidget.Category?,
        val rightButtonTextField: InputWidget.Category?,
        val yellowTextButton: ButtonWidget.Category?,
        val yellowButton: ButtonWidget.Category?
    )

    object Id {
        object Title: TextWidget.Id
        object SignUp: ButtonWidget.Id
        object Phone: InputWidget.Id
        object Password: InputWidget.Id
        object SignIn: ButtonWidget.Id
        object ForgotPassword: ButtonWidget.Id
    }

    override val navigationBar: NavigationBar get() = NavigationBar.None
    override val isDismissKeyboardOnTap: Boolean = true
    override val isKeyboardResizeContent: Boolean = true

    override fun createContentWidget() = with(theme) {
        val viewModel: SignInViewModel = getViewModel {
            createViewModelBlock(createEventsDispatcher())
        }

        viewModel.eventsDispatcher.listen(this@SignInScreen, this@SignInScreen)

        constraint(size = WidgetSize.AsParent) {
            val titleText = +text(
                category = styles.titleText,
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.WrapContent),
                id = Id.Title,
                text = const(viewModel.strings.signIn.desc() as StringDesc)
            )

            val signUpButton = +button(
                category = styles.yellowTextButton,
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.WrapContent),
                id = Id.SignUp,
                content = ButtonWidget.Content.Text(Value.data(viewModel.strings.signUp.desc() as StringDesc)),
                onTap = viewModel::didTapSignUpButton
            )

            val phoneField = +input(
                category = styles.textField,
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.WrapContent),
                id = Id.Phone,
                field = viewModel.phoneField,
                label = const(viewModel.strings.phone.desc() as StringDesc),
                inputType = InputType.PHONE
            )

            val passwordField = +input(
                category = styles.rightButtonTextField,
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.WrapContent),
                id = Id.Password,
                field = viewModel.passwordField,
                label = const(viewModel.strings.password.desc() as StringDesc),
                inputType = InputType.PASSWORD
            )

            val forgotPasswordButton = +button(
                category = styles.yellowTextButton,
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.WrapContent),
                id = Id.ForgotPassword,
                content = ButtonWidget.Content.Text(Value.data(viewModel.strings.forgotPassword.desc() as StringDesc)),
                onTap = viewModel::didTapForgotPassword
            )

            val signInButton = +button(
                category = styles.yellowButton,
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.Exact(46.0f)),
                id = Id.SignIn,
                content = ButtonWidget.Content.Text(Value.data(viewModel.strings.signIn.desc() as StringDesc)),
                onTap = viewModel::didTapSignInButton
            )

            constraints {
                titleText.leftToLeft(root.safeArea).offset(16)
                titleText.topToTop(root.safeArea).offset(24)

                signUpButton.rightToRight(root.safeArea).offset(16)
                signUpButton.centerYToCenterY(titleText)

                phoneField.topToBottom(titleText).offset(32)
                phoneField.leftToLeft(root.safeArea).offset(16)
                phoneField.rightToRight(root.safeArea).offset(16)

                passwordField.topToBottom(phoneField).offset(32)
                passwordField.leftToLeft(phoneField)
                passwordField.rightToRight(phoneField)

                forgotPasswordButton.topToBottom(passwordField).offset(24)
                forgotPasswordButton.rightToRight(passwordField)

                signInButton.leftRightToLeftRight(passwordField)
                signInButton.bottomToBottom(root.safeArea).offset(16)
            }
        }
    }

    override fun routeToForgotPassword() {
        forgotPasswordRoute.route(this, Unit)
    }

    override fun routeToSignUp() {
        signUpRoute.route(this, Unit)
    }
}