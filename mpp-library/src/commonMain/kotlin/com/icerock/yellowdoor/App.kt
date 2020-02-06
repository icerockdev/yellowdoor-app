/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerock.yellowdoor

import com.icerock.yellowdoor.factory.SharedFactory
import com.icerock.yellowdoor.feature.login.SignInScreen
import com.icerock.yellowdoor.feature.register.SignUpScreen
import com.icerock.yellowdoor.styles.*
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.container
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.factory.CheckboxSwitchViewFactory
import dev.icerock.moko.widgets.screen.*
import dev.icerock.moko.widgets.screen.navigation.*
import dev.icerock.moko.widgets.style.view.WidgetSize
import dev.icerock.moko.widgets.text
import org.example.library.MR


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
            factory[LinkButtonCategory] = LinkButtonFactory()
            factory[HTMLTextCategory] = HTMLTextFactory(12)
            factory[SignUpScreen.Id.Checkbox] = CheckboxSwitchViewFactory(
                checkedImage = MR.images.checkboxEnable,
                uncheckedImage = MR.images.checkboxDisable
            )
        }

        return registerScreen(RootNavigationScreen::class) {
            val router = createRouter()

            val mockScreen: TypedScreenDesc<Args.Empty, MockScreen> =
                registerScreen(MockScreen::class) {
                    MockScreen(theme)
                }

            val signUpScreen: TypedScreenDesc<Args.Empty, SignUpScreen> =
                registerScreen(SignUpScreen::class) {
                    factory.signUpFactory.createSignUpScreen(
                        theme = theme,
                        styles = SignUpScreen.Styles(
                            linkText = HTMLTextCategory,
                            textField = InputFieldCategory,
                            yellowButton = YellowButtonCategory
                        )
                    )
                }

            val signInScreen: TypedScreenDesc<Args.Empty, SignInScreen> =
                registerScreen(SignInScreen::class) {
                    factory.signInFactory.createSignInScreen(
                        theme = theme,
                        styles = SignInScreen.Styles(
                            titleText = BoldText25Category,
                            yellowButton = YellowButtonCategory,
                            textField = InputFieldCategory,
                            rightButtonTextField = InputFieldCategory,
                            yellowTextButton = YellowTextButtonCategory
                        ),
                        forgotPasswordRoute = router.createPushRoute(mockScreen),
                        signUpRoute = router.createPushRoute(signUpScreen)
                    )

                }

            return@registerScreen RootNavigationScreen(
                initialScreen = signInScreen,
                router = router
            )
        }
    }
}


class MockScreen(private val theme: Theme) : WidgetScreen<Args.Empty>(), NavigationItem {
    override val navigationBar: NavigationBar get() = NavigationBar.Normal("Title".desc())

    override fun createContentWidget() = with(theme) {
        container(size = WidgetSize.AsParent) {
            center {
                text(
                    size = WidgetSize.WrapContent,
                    text = const("UNDER CONSTRUCTION".desc() as StringDesc)
                )
            }
        }
    }
}