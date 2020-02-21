/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor

import dev.icerock.yellowdoor.di.createAuthFactory
import dev.icerock.yellowdoor.theme.BoldText25Category
import dev.icerock.yellowdoor.theme.BoldText25Factory
import dev.icerock.yellowdoor.theme.InputFieldCategory
import dev.icerock.yellowdoor.theme.InputFieldFactory
import dev.icerock.yellowdoor.theme.YellowButtonCategory
import dev.icerock.yellowdoor.theme.YellowButtonFactory
import dev.icerock.yellowdoor.theme.YellowTextButtonCategory
import dev.icerock.yellowdoor.theme.YellowTextButtonFactory
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.BaseApplication
import dev.icerock.moko.widgets.screen.ScreenDesc
import dev.icerock.moko.widgets.screen.TypedScreenDesc
import dev.icerock.moko.widgets.screen.navigation.createPushRoute
import dev.icerock.moko.widgets.screen.navigation.createRouter
import dev.icerock.yellowdoor.domain.DomainFactory
import dev.icerock.yellowdoor.feature.auth.di.AuthFactory
import dev.icerock.yellowdoor.feature.auth.presentation.SignInScreen
import dev.icerock.yellowdoor.screens.MockScreen
import dev.icerock.yellowdoor.screens.RootNavigationScreen

class App : BaseApplication() {
    override fun setup(): ScreenDesc<Args.Empty> {
        val theme = Theme {
            factory[YellowButtonCategory] = YellowButtonFactory()
            factory[InputFieldCategory] = InputFieldFactory()
            factory[YellowTextButtonCategory] =
                YellowTextButtonFactory()
            factory[BoldText25Category] = BoldText25Factory()
        }

        val domainFactory = DomainFactory()
        val signInFactory: AuthFactory = domainFactory.createAuthFactory(theme = theme)

        return registerScreen(RootNavigationScreen::class) {
            val router = createRouter()

            val mockScreen: TypedScreenDesc<Args.Empty, MockScreen> =
                registerScreen(MockScreen::class) {
                    MockScreen(theme)
                }

            val signInScreen: TypedScreenDesc<Args.Empty, SignInScreen> =
                registerScreen(SignInScreen::class) {
                    signInFactory.createSignInScreen(
                        theme = theme,
                        styles = SignInScreen.Styles(
                            titleText = BoldText25Category,
                            yellowButton = YellowButtonCategory,
                            textField = InputFieldCategory,
                            rightButtonTextField = InputFieldCategory,
                            yellowTextButton = YellowTextButtonCategory
                        ),
                        forgotPasswordRoute = router.createPushRoute(mockScreen),
                        signUpRoute = router.createPushRoute(mockScreen)
                    )

                }

            RootNavigationScreen(
                initialScreen = signInScreen,
                router = router
            )
        }
    }
}
