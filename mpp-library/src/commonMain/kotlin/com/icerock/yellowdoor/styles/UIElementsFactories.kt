package com.icerock.yellowdoor.styles

import dev.icerock.moko.graphics.Color
import dev.icerock.moko.widgets.factory.*
import dev.icerock.moko.widgets.style.background.*
import dev.icerock.moko.widgets.style.view.*


@Suppress("FunctionName")
fun YellowButtonFactory(): SystemButtonViewFactory {
    return SystemButtonViewFactory(
        background = StateBackground(
            normal = Background(fill = Fill.Solid(Colors.yellowButtonBackgroundColor),
                shape = Shape.Rectangle(cornerRadius = 8.0f)),
            disabled = Background(fill = Fill.Solid(Colors.yellowButtonBackgroundColor.withAlphaComponent(128)),
                shape = Shape.Rectangle(cornerRadius = 8.0f)),
            pressed = Background(fill = Fill.Solid(Colors.yellowButtonBackgroundColor.withAlphaComponent(128)),
                shape = Shape.Rectangle(cornerRadius = 8.0f))
        ),
        textStyle = TextStyle(size = 17, color = Colors.blackTextColor)
    )
}

@Suppress("FunctionName")
fun YellowTextButtonFactory(): SystemButtonViewFactory {
    return SystemButtonViewFactory(
        textStyle = TextStyle(size = 15, color = Colors.yellowTextColor, fontStyle = FontStyle.MEDIUM)
    )
}

@Suppress("FunctionName")
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

@Suppress("FunctionName")
fun BoldText25Factory(): SystemTextViewFactory {
    return SystemTextViewFactory(
        textStyle = TextStyle(
            size = 25,
            color = Colors.blackTextColor,
            fontStyle = FontStyle.BOLD
        )
    )
}