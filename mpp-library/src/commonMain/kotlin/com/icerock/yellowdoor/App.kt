/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerock.yellowdoor

import com.icerock.yellowdoor.factory.SharedFactory
import com.icerock.yellowdoor.feature.login.SignInScreen
import com.icerock.yellowdoor.feature.login.di.SignInFactory
import com.icerock.yellowdoor.styles.*
import dev.icerock.moko.widgets.ButtonWidget
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.BaseApplication
import dev.icerock.moko.widgets.screen.ScreenDesc
import dev.icerock.moko.widgets.screen.TypedScreenDesc
import dev.icerock.moko.widgets.screen.navigation.NavigationScreen
import dev.icerock.moko.widgets.screen.navigation.createRouter


class RootNavigationScreen(
    initialScreen: TypedScreenDesc<Args.Empty, SignInScreen>,
    router: Router
) : NavigationScreen<SignInScreen>(initialScreen, router)


class App : BaseApplication() {
    private val factory: SharedFactory by lazy {
        return@lazy SharedFactory()
    }

    override fun setup(): ScreenDesc<Args.Empty> {
        val theme: Theme = Theme {
            factory[YellowButtonCategory] = YellowButtonFactory()
            factory[InputFieldCategory] = InputFieldFactory()
            factory[YellowTextButtonCategory] = YellowTextButtonFactory()
            factory[BoldText25Category] = BoldText25Factory()
        }

        return registerScreen(RootNavigationScreen::class) {
            val router = createRouter()

            val signInScreen: TypedScreenDesc<Args.Empty, SignInScreen> = registerSignInScreen(theme)

            return@registerScreen RootNavigationScreen(
                initialScreen = signInScreen,
                router = router
            )
        }
    }

    private fun registerSignInScreen(theme: Theme): TypedScreenDesc<Args.Empty, SignInScreen> {
        return registerScreen(SignInScreen::class) {
            SignInScreen(
                theme = theme,
                styles = SignInScreen.Styles(
                    titleText = BoldText25Category,
                    yellowButton = YellowButtonCategory,
                    textField = InputFieldCategory,
                    rightButtonTextField = InputFieldCategory,
                    yellowTextButton = YellowTextButtonCategory
                ),
                createViewModelBlock = factory.signInFactory::createSignInViewModel
            )
        }
    }
}


