/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.feature.auth.presentation.signin

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.ButtonWidget
import dev.icerock.moko.widgets.InputWidget
import dev.icerock.moko.widgets.TextWidget
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
import dev.icerock.moko.widgets.text

class SignInScreen(
    private val theme: Theme,
    private val styles: Styles,
    private val createViewModelBlock: (
        EventsDispatcher<SignInViewModel.EventsListener>
    ) -> SignInViewModel,
    private val signUpRoute: Route<Unit>,
    private val forgotPasswordRoute: Route<Unit>,
    private val strings: Strings
) : WidgetScreen<Args.Empty>(), NavigationItem,
    SignInViewModel.EventsListener {

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
                text = const(strings.signIn.desc() as StringDesc)
            )

            val signUpButton = +button(
                category = styles.yellowTextButton,
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.WrapContent),
                id = Id.SignUp,
                content = ButtonWidget.Content.Text(Value.data(strings.signUp.desc() as StringDesc)),
                onTap = viewModel::didTapSignUpButton
            )

            val phoneField = +input(
                category = styles.textField,
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.WrapContent),
                id = Id.Phone,
                field = viewModel.phoneField,
                label = const(strings.phone.desc() as StringDesc),
                inputType = InputType.Phone()
            )

            val passwordField = +input(
                category = styles.rightButtonTextField,
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.WrapContent),
                id = Id.Password,
                field = viewModel.passwordField,
                label = const(strings.password.desc() as StringDesc),
                inputType = InputType.Password()
            )

            val forgotPasswordButton = +button(
                category = styles.yellowTextButton,
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.WrapContent),
                id = Id.ForgotPassword,
                content = ButtonWidget.Content.Text(Value.data(strings.forgotPassword.desc() as StringDesc)),
                onTap = viewModel::didTapForgotPassword
            )

            val signInButton = +button(
                category = styles.yellowButton,
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.Exact(46.0f)),
                id = Id.SignIn,
                content = ButtonWidget.Content.Text(Value.data(strings.signIn.desc() as StringDesc)),
                onTap = viewModel::didTapSignInButton
            )

            constraints {
                titleText leftToLeft root.safeArea offset 16
                titleText topToTop root.safeArea offset 24

                signUpButton rightToRight root.safeArea offset 16
                signUpButton centerYToCenterY titleText

                phoneField topToBottom titleText offset 32
                phoneField leftToLeft root.safeArea offset 16
                phoneField rightToRight root.safeArea offset 16

                passwordField topToBottom phoneField offset 32
                passwordField leftToLeft phoneField
                passwordField rightToRight phoneField

                forgotPasswordButton topToBottom passwordField offset 24
                forgotPasswordButton rightToRight passwordField

                signInButton leftRightToLeftRight passwordField
                signInButton bottomToBottom root.safeArea offset 16
            }
        }
    }

    override fun routeToForgotPassword() {
        forgotPasswordRoute.route()
    }

    override fun routeToSignUp() {
        signUpRoute.route()
    }

    class Styles(
        val titleText: TextWidget.Category?,
        val textField: InputWidget.Category?,
        val rightButtonTextField: InputWidget.Category?,
        val yellowTextButton: ButtonWidget.Category?,
        val yellowButton: ButtonWidget.Category?
    )

    interface Strings {
        val signIn: StringResource
        val signUp: StringResource
        val phone: StringResource
        val password: StringResource
        val forgotPassword: StringResource
    }

    object Id {
        object Title : TextWidget.Id
        object SignUp : ButtonWidget.Id
        object Phone : InputWidget.Id
        object Password : InputWidget.Id
        object SignIn : ButtonWidget.Id
        object ForgotPassword : ButtonWidget.Id
    }
}
