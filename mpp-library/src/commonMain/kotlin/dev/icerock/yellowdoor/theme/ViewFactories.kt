/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("FunctionName")

package dev.icerock.yellowdoor.theme

import dev.icerock.moko.widgets.factory.FloatingLabelInputViewFactory
import dev.icerock.moko.widgets.factory.SystemButtonViewFactory
import dev.icerock.moko.widgets.factory.SystemTextViewFactory
import dev.icerock.moko.widgets.screen.navigation.NavigationBar
import dev.icerock.moko.widgets.style.background.Background
import dev.icerock.moko.widgets.style.background.Fill
import dev.icerock.moko.widgets.style.background.Shape
import dev.icerock.moko.widgets.style.background.StateBackground
import dev.icerock.moko.widgets.style.view.Colors
import dev.icerock.moko.widgets.style.view.FontStyle
import dev.icerock.moko.widgets.style.view.TextStyle


fun YellowButtonFactory(): SystemButtonViewFactory {
    return SystemButtonViewFactory(
        background = StateBackground(
            normal = Background(
                fill = Fill.Solid(Colors.yellowButtonBackgroundColor),
                shape = Shape.Rectangle(cornerRadius = 8.0f)
            ),
            disabled = Background(
                fill = Fill.Solid(Colors.yellowButtonBackgroundColor.copy(red = 128)),
                shape = Shape.Rectangle(cornerRadius = 8.0f)
            ),
            pressed = Background(
                fill = Fill.Solid(Colors.yellowButtonBackgroundColor.copy(red = 128)),
                shape = Shape.Rectangle(cornerRadius = 8.0f)
            )
        ),
        textStyle = TextStyle(size = 17, color = Colors.blackTextColor)
    )
}

fun YellowTextButtonFactory(): SystemButtonViewFactory {
    return SystemButtonViewFactory(
        textStyle = TextStyle(
            size = 15,
            color = Colors.yellowTextColor,
            fontStyle = FontStyle.MEDIUM
        )
    )
}

fun InputFieldFactory(): FloatingLabelInputViewFactory {
    return FloatingLabelInputViewFactory(
        underLineColor = Colors.separatorColor,
        underLineFocusedColor = Colors.blackTextColor,
        textStyle = TextStyle(size = 15, color = Colors.blackTextColor),
        labelTextStyle = TextStyle(size = 15, color = Colors.placeholderTextColor),
        errorTextStyle = TextStyle(size = 15, color = Colors.errorRedColor)
    )
}

fun ForgotPasswordInputFieldFactory(): FloatingLabelInputViewFactory {
    return FloatingLabelInputViewFactory(
        underLineColor = Colors.separatorColor,
        underLineFocusedColor = Colors.blackTextColor,
        textStyle = TextStyle(size = 15, color = Colors.blackTextColor),
        labelTextStyle = TextStyle(size = 15, color = Colors.placeholderTextColor),
        errorTextStyle = TextStyle(size = 15, color = Colors.errorRedColor)
    )
}

fun BoldText25Factory(): SystemTextViewFactory {
    return SystemTextViewFactory(
        textStyle = TextStyle(
            size = 25,
            color = Colors.blackTextColor,
            fontStyle = FontStyle.BOLD
        )
    )
}

fun LinkButtonFactory(): SystemButtonViewFactory {
    return SystemButtonViewFactory(
        textStyle = TextStyle(
            size = 15,
            color = Colors.yellowTextColor,
            fontStyle = FontStyle.MEDIUM
        )
    )
}

fun HTMLTextFactory(fontSize: Int): SystemTextViewFactory {
    return SystemTextViewFactory(
        textStyle = TextStyle(
            size = fontSize
        ),
        isHtmlConverted = true
    )
}

fun InstructionTextFactory(): SystemTextViewFactory {
    return SystemTextViewFactory(
        textStyle = TextStyle(
            size = 12,
            color = Colors.grayTextColor
        )
    )
}

fun BlackTextButtonFactory(): SystemButtonViewFactory {
    return SystemButtonViewFactory(
        textStyle = TextStyle(
            size = 14,
            color = Colors.blackTextColor
        )
    )
}

fun BlackTextCategoryFactory(size: Int): SystemTextViewFactory {
    return SystemTextViewFactory(
        textStyle = TextStyle(
            size = size,
            color = Colors.blackTextColor
        )
    )
}

fun createBlackNavigationBarStyle(): NavigationBar.Normal.Styles {
    return NavigationBar.Normal.Styles(
        backgroundColor = Colors.black,
        textStyle = TextStyle(
            size = 17,
            fontStyle = FontStyle.MEDIUM,
            color = Colors.white
        ),
        tintColor = Colors.yellowButtonBackgroundColor
    )
}
