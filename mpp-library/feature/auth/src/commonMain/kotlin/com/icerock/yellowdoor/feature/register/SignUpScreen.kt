package com.icerock.yellowdoor.feature.register

import com.icerock.yellowdoor.feature.smsCodeConfirmation.SMSCodeConfirmationScreen
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
import dev.icerock.moko.widgets.style.input.InputType
import dev.icerock.moko.widgets.style.view.SizeSpec
import dev.icerock.moko.widgets.style.view.WidgetSize


class SignUpScreen(
    private val strings: Strings,
    private val styles: Styles,
    private val theme: Theme,
    private val createViewModelBlock: (
        EventsDispatcher<SignUpViewModel.EventsListener>
    ) -> SignUpViewModel,
    private val userAgreementRoute: Route<Unit>,
    private val smsCodeConfirmationRoute: Route<SMSCodeConfirmationScreen.Arg>
) : WidgetScreen<Args.Empty>(), NavigationItem, SignUpViewModel.EventsListener {

    override val navigationBar: NavigationBar = NavigationBar.Normal(strings.signUp.desc())
    override val isDismissKeyboardOnTap: Boolean = true
    override val isKeyboardResizeContent: Boolean = true

    override fun createContentWidget() = with(theme) {
        val viewModel: SignUpViewModel = getViewModel {
            createViewModelBlock(createEventsDispatcher())
        }
        viewModel.eventsDispatcher.listen(this@SignUpScreen, this@SignUpScreen)
        constraint(size = WidgetSize.AsParent) {
            val scroll = +scroll(
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.MatchConstraint),
                id = Id.Scroll,
                child = constraint(
                    size = WidgetSize.Const(SizeSpec.AsParent, SizeSpec.WrapContent),
                    id = Id.Container
                ) {
                    val fields: List<ConstraintItem.Child> = listOf(
                        +input(
                            category = styles.textField,
                            size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.WrapContent),
                            id = Id.LastName,
                            field = viewModel.lastNameField,
                            label = const(strings.lastName.desc() as StringDesc)
                        ),
                        +input(
                            category = styles.textField,
                            size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.WrapContent),
                            id = Id.FirstName,
                            field = viewModel.firstNameField,
                            label = const(strings.firstName.desc() as StringDesc)
                        ),
                        +input(
                            category = styles.textField,
                            size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.WrapContent),
                            id = Id.Phone,
                            field = viewModel.phoneField,
                            label = const(strings.phone.desc() as StringDesc),
                            inputType = InputType.PHONE
                        ),
                        +input(
                            category = styles.textField,
                            size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.WrapContent),
                            id = Id.Email,
                            field = viewModel.emailField,
                            label = const(strings.email.desc() as StringDesc),
                            inputType = InputType.EMAIL
                        ),
                        +input(
                            category = styles.textField,
                            size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.WrapContent),
                            id = Id.Password,
                            field = viewModel.passwordField,
                            label = const(strings.password.desc() as StringDesc)
                        ),
                        +input(
                            category = styles.textField,
                            size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.WrapContent),
                            id = Id.RepeatPassword,
                            field = viewModel.repeatPasswordField,
                            label = const(strings.repeatPassword.desc() as StringDesc)
                        )
                    )

                    val checkbox = +switch(
                        size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.MatchConstraint),
                        id = Id.Checkbox,
                        state = viewModel.isUserAgreementAccepted
                    )

                    val userAgreementText = +text(
                        id = Id.UserAgreementText,
                        category = styles.linkText,
                        size = WidgetSize.WrapContent,
                        text = const(strings.iAcceptTheUserAgreement.desc() as StringDesc)
                    )

                    val signUpButton = +button(
                        category = styles.yellowButton,
                        size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.Exact(46.0f)),
                        id = Id.SignUp,
                        content = ButtonWidget.Content.Text(Value.data(strings.signUp.desc() as StringDesc)),
                        onTap = viewModel::didTapSignUpButton,
                        enabled = viewModel.isFormValid
                    )

                    constraints {
                        for (i in 0 until fields.count()) {
                            if (i == 0)
                                fields[i] topToTop root offset 24
                            else
                                fields[i] topToBottom fields[i - 1] offset 32
                            fields[i] leftToLeft root offset 16
                            fields[i] rightToRight root offset 16
                        }

                        checkbox leftToLeft fields.last()
                        checkbox topToBottom fields.last() offset 24

                        userAgreementText leftToRight checkbox offset 10
                        userAgreementText centerYToCenterY checkbox

                        signUpButton topToBottom checkbox offset 32
                        signUpButton leftRightToLeftRight fields.last()
                        signUpButton bottomToBottom root offset 16
                    }
                }
            )

            constraints {
                scroll leftRightToLeftRight root
                scroll topToTop root.safeArea
                scroll bottomToBottom root.safeArea
            }
        }
    }

    override fun routeToUserAgreement() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun routeToSMSCodeConfirmation(phone: String) {
        smsCodeConfirmationRoute.route(this, SMSCodeConfirmationScreen.Arg(phone))
    }

    class Styles(
        val textField: InputWidget.Category,
        val yellowButton: ButtonWidget.Category,
        val linkText: TextWidget.Category
    )

    interface Strings {
        val lastName: StringResource
        val firstName: StringResource
        val phone: StringResource
        val email: StringResource
        val password: StringResource
        val repeatPassword: StringResource
        val iAcceptTheUserAgreement: StringResource
        val signUp: StringResource
    }

    object Id {
        object LastName : InputWidget.Id
        object FirstName : InputWidget.Id
        object Phone : InputWidget.Id
        object Email : InputWidget.Id
        object Password : InputWidget.Id
        object RepeatPassword : InputWidget.Id
        object SignUp : ButtonWidget.Id
        object Scroll : ScrollWidget.Id
        object Container : ConstraintWidget.Id
        object Checkbox : SwitchWidget.Id
        object UserAgreementText : TextWidget.Id
    }
}