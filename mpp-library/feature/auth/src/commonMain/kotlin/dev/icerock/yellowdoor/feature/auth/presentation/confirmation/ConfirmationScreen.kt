/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.feature.auth.presentation.confirmation

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.resources.desc.plus
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
import dev.icerock.moko.widgets.screen.getArgument
import dev.icerock.moko.widgets.screen.getViewModel
import dev.icerock.moko.widgets.screen.listen
import dev.icerock.moko.widgets.screen.navigation.NavigationBar
import dev.icerock.moko.widgets.screen.navigation.NavigationItem
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.moko.widgets.screen.navigation.route
import dev.icerock.moko.widgets.style.view.SizeSpec
import dev.icerock.moko.widgets.style.view.WidgetSize
import dev.icerock.moko.widgets.text


class ConfirmationScreen(
    private val theme: Theme,
    private val strings: Strings,
    private val styles: Styles,
    images: Images,
    private val routeNext: Route<Unit>,
    routeBack: Route<Unit>,
    private val createViewModelBlock: (
        EventsDispatcher<ConfirmationViewModel.EventsListener>
    ) -> ConfirmationViewModel
) : WidgetScreen<Args.Parcel<ConfirmationScreen.Arg>>(), ConfirmationViewModel.EventsListener, NavigationItem {

    override val navigationBar: NavigationBar = NavigationBar.Normal(
        title = strings.numberConfirmation.desc(),
        styles = styles.navigationBar,
        backButton = NavigationBar.Normal.BarButton(
            icon = images.backImage,
            action = routeBack::route
        )
    )

    override val isDismissKeyboardOnTap: Boolean = true
    override val isKeyboardResizeContent: Boolean = true

    override fun createContentWidget() = with(theme) {
        val viewModel: ConfirmationViewModel = getViewModel {
            createViewModelBlock(createEventsDispatcher())
        }

        viewModel.eventsDispatcher.listen(
            this@ConfirmationScreen,
            this@ConfirmationScreen
        )

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
                onTap = viewModel::didTapNextButton
            )

            constraints {
                smsCodeTextField.leftToLeft(root.safeArea).offset(16)
                smsCodeTextField.topToTop(root.safeArea).offset(24)
                smsCodeTextField.rightToRight(root.safeArea).offset(16)

                instructionLabel.leftRightToLeftRight(smsCodeTextField)
                instructionLabel.topToBottom(smsCodeTextField).offset(24)

                signUpButton.leftRightToLeftRight(smsCodeTextField)
                signUpButton.bottomToBottom(root.safeArea).offset(16)
            }
        }
    }

    override fun routeToPersonalData() {
        routeNext.route()
    }

    @Parcelize
    data class Arg(val phone: String) : Parcelable

    class Styles(
        val textField: InputWidget.Category,
        val instructionText: TextWidget.Category,
        val yellowButton: ButtonWidget.Category,
        val navigationBar: NavigationBar.Normal.Styles
    )

    interface Strings {
        val numberConfirmation: StringResource
        val smsCode: StringResource
        val smsCodeSent: StringResource
        val next: StringResource
    }

    interface Images {
        val backImage: ImageResource
    }

    object Id {
        object SMSCodeField : InputWidget.Id
        object InstructionLabel : TextWidget.Id
        object NextButton : ButtonWidget.Id
    }
}
