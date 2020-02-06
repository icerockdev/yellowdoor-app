package com.icerock.yellowdoor.feature.smsCodeConfirmation

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.resources.desc.plus
import dev.icerock.moko.widgets.*
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.core.Value
import dev.icerock.moko.widgets.screen.*
import dev.icerock.moko.widgets.screen.navigation.NavigationBar
import dev.icerock.moko.widgets.screen.navigation.NavigationItem
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.moko.widgets.style.view.SizeSpec
import dev.icerock.moko.widgets.style.view.WidgetSize


class SMSCodeConfirmationScreen(
    private val theme: Theme,
    private val strings: Strings,
    private val styles: Styles,
    private val routeNext: Route<Unit>,
    private val createViewModelBlock: (
        EventsDispatcher<SMSCodeConfirmationViewModel.EventsListener>
    ) -> SMSCodeConfirmationViewModel
) : WidgetScreen<Args.Parcel<SMSCodeConfirmationScreen.Arg>>(), SMSCodeConfirmationViewModel.EventsListener, NavigationItem {

    override val navigationBar: NavigationBar = NavigationBar.Normal(strings.numberConfirmation.desc())
    override val isDismissKeyboardOnTap: Boolean = true
    override val isKeyboardResizeContent: Boolean = true

    override fun createContentWidget() = with(theme) {
        val viewModel: SMSCodeConfirmationViewModel = getViewModel {
            createViewModelBlock(createEventsDispatcher())
        }

        viewModel.eventsDispatcher.listen(this@SMSCodeConfirmationScreen, this@SMSCodeConfirmationScreen)

        constraint(size = WidgetSize.AsParent) {
            val smsCodeTextField = +input(
                category = styles.textField,
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.WrapContent),
                id = Id.SMSCodeField,
                field = viewModel.smsCodeField,
                label = const(strings.smsCode.desc() as StringDesc)
            )

            val instruction: StringDesc = strings.smsCodeSent.desc() + getArgument().phone.desc()

            val instructionLabel = +text(
                category = styles.instructionText,
                id = Id.InstructionLabel,
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.WrapContent),
                text = const(instruction)
            )

            val signUpButton = +button(
                category = styles.yellowButton,
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.Exact(46.0f)),
                id = Id.NextButton,
                content = ButtonWidget.Content.Text(Value.data(strings.next.desc() as StringDesc)),
                onTap = viewModel::didTapNextButton,
                enabled = viewModel.isFormValid
            )

            constraints {
                smsCodeTextField leftToLeft root.safeArea offset 16
                smsCodeTextField topToTop root.safeArea offset 24
                smsCodeTextField rightToRight root.safeArea offset 16

                instructionLabel leftRightToLeftRight smsCodeTextField
                instructionLabel topToBottom smsCodeTextField offset 24

                signUpButton leftRightToLeftRight smsCodeTextField
                signUpButton bottomToBottom root.safeArea offset 16
            }
        }
    }

    override fun routeToPersonalData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Parcelize
    data class Arg(val phone: String) : Parcelable

    class Styles(
        val textField: InputWidget.Category,
        val instructionText: TextWidget.Category,
        val yellowButton: ButtonWidget.Category
    )

    interface Strings {
        val numberConfirmation: StringResource
        val smsCode: StringResource
        val smsCodeSent: StringResource
        val next: StringResource
    }

    object Id {
        object SMSCodeField: InputWidget.Id
        object InstructionLabel: TextWidget.Id
        object NextButton: ButtonWidget.Id
    }
}