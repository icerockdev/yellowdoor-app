/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor

import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.factory.CheckboxSwitchViewFactory
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.BaseApplication
import dev.icerock.moko.widgets.screen.ScreenDesc
import dev.icerock.moko.widgets.screen.TypedScreenDesc
import dev.icerock.moko.widgets.screen.navigation.createPushRoute
import dev.icerock.moko.widgets.screen.navigation.createRouter
import dev.icerock.yellowdoor.di.createAuthFactory
import dev.icerock.yellowdoor.di.createForgotPasswordFactory
import dev.icerock.yellowdoor.di.createProfileFactory
import dev.icerock.yellowdoor.di.createSelectionFactory
import dev.icerock.yellowdoor.domain.DomainFactory
import dev.icerock.yellowdoor.feature.auth.di.AuthFactory
import dev.icerock.yellowdoor.feature.auth.presentation.confirmation.ConfirmationScreen
import dev.icerock.yellowdoor.feature.auth.presentation.signin.SignInScreen
import dev.icerock.yellowdoor.feature.auth.presentation.signup.SignUpScreen
import dev.icerock.yellowdoor.feature.forgotPassword.di.ForgotPasswordFactory
import dev.icerock.yellowdoor.feature.forgotPassword.presentation.recovery.PasswordRecoveryScreen
import dev.icerock.yellowdoor.feature.forgotPassword.presentation.submit.ForgotPasswordScreen
import dev.icerock.yellowdoor.feature.profile.di.ProfileFactory
import dev.icerock.yellowdoor.feature.profile.presentation.PersonalInfoScreen
import dev.icerock.yellowdoor.feature.selection.di.SelectionFactory
import dev.icerock.yellowdoor.feature.selection.presentation.SelectItemScreen
import dev.icerock.yellowdoor.screens.MockScreen
import dev.icerock.yellowdoor.screens.RootNavigationScreen
import dev.icerock.yellowdoor.theme.BlackTextButtonCategory
import dev.icerock.yellowdoor.theme.BlackTextButtonFactory
import dev.icerock.yellowdoor.theme.BlackTextCategory
import dev.icerock.yellowdoor.theme.BlackTextCategoryFactory
import dev.icerock.yellowdoor.theme.BoldText25Category
import dev.icerock.yellowdoor.theme.BoldText25Factory
import dev.icerock.yellowdoor.theme.HTMLTextCategory
import dev.icerock.yellowdoor.theme.HTMLTextFactory
import dev.icerock.yellowdoor.theme.InputFieldCategory
import dev.icerock.yellowdoor.theme.InputFieldFactory
import dev.icerock.yellowdoor.theme.InstructionTextCategory
import dev.icerock.yellowdoor.theme.InstructionTextFactory
import dev.icerock.yellowdoor.theme.LinkButtonCategory
import dev.icerock.yellowdoor.theme.LinkButtonFactory
import dev.icerock.yellowdoor.theme.YellowButtonCategory
import dev.icerock.yellowdoor.theme.YellowButtonFactory
import dev.icerock.yellowdoor.theme.YellowTextButtonCategory
import dev.icerock.yellowdoor.theme.YellowTextButtonFactory
import dev.icerock.yellowdoor.theme.createBlackNavigationBarStyle
import org.example.library.MR

class App : BaseApplication() {
    override fun setup(): ScreenDesc<Args.Empty> {
        val theme = Theme {
            factory[YellowButtonCategory] = YellowButtonFactory()
            factory[InputFieldCategory] = InputFieldFactory()
            factory[YellowTextButtonCategory] = YellowTextButtonFactory()
            factory[BoldText25Category] = BoldText25Factory()
            factory[LinkButtonCategory] = LinkButtonFactory()
            factory[HTMLTextCategory] = HTMLTextFactory(12)
            factory[InstructionTextCategory] = InstructionTextFactory()
            factory[BlackTextButtonCategory] = BlackTextButtonFactory()
            factory[BlackTextCategory] = BlackTextCategoryFactory(15)
            factory[SignUpScreen.Id.Checkbox] = CheckboxSwitchViewFactory(
                checkedImage = MR.images.checkbox_enable,
                uncheckedImage = MR.images.checkbox_disable
            )
        }

        val domainFactory = DomainFactory()
        val signInFactory: AuthFactory = domainFactory.createAuthFactory(theme = theme)
        val forgotPasswordFactory: ForgotPasswordFactory = domainFactory.createForgotPasswordFactory(theme = theme)
        val selectionFactory: SelectionFactory = domainFactory.createSelectionFactory(theme = theme)
        val profileFactory: ProfileFactory = domainFactory.createProfileFactory(theme = theme)

        return registerScreen(RootNavigationScreen::class) {
            val router = createRouter()

            val mockScreen = registerScreen(MockScreen::class) { MockScreen(theme) }

            val selectItemScreen: TypedScreenDesc<Args.Empty, SelectItemScreen> =
                registerScreen(SelectItemScreen::class) {
                    selectionFactory.createSelectItemScreen(
                        styles = SelectItemScreen.Styles(
                            navigationBar = createBlackNavigationBarStyle(),
                            itemTitle = BlackTextCategory
                        ),
                        backRoute = router.createPopRoute()
                    )
                }

            val personalInfoScreen: TypedScreenDesc<Args.Empty, PersonalInfoScreen> =
                registerScreen(PersonalInfoScreen::class) {
                    profileFactory.createPersonalInfoScreen(
                        styles = PersonalInfoScreen.Styles(
                            navigationBar = createBlackNavigationBarStyle(),
                            uploadNewPhotoButton = BlackTextButtonCategory,
                            selectableFieldTitle = InstructionTextCategory,
                            selectableFieldContent = BlackTextCategory,
                            textField = InputFieldCategory,
                            yellowButton = YellowButtonCategory
                        ),
                        closeRoute = router.createPushRoute(mockScreen),
                        newsRoute = router.createPushRoute(mockScreen),
                        regionRoute = router.createPushRoute(selectItemScreen)
                    )
                }

            val passwordRecoveryScreen: TypedScreenDesc<Args.Empty, PasswordRecoveryScreen> =
                registerScreen(PasswordRecoveryScreen::class) {
                    forgotPasswordFactory.createPasswordRecoveryScreen(
                        styles = PasswordRecoveryScreen.Styles(
                            textField = InputFieldCategory,
                            yellowButton = YellowButtonCategory,
                            navigationBar = createBlackNavigationBarStyle()
                        ),
                        backRoute = router.createPopRoute(),
                        authRoute = router.createPushRoute(mockScreen) // TODO: pop to root
                    )
                }

            val forgotPasswordScreen: TypedScreenDesc<Args.Empty, ForgotPasswordScreen> =
                registerScreen(ForgotPasswordScreen::class) {
                    forgotPasswordFactory.createForgotPasswordScreen(
                        styles = ForgotPasswordScreen.Styles(
                            yellowButton = YellowButtonCategory,
                            textField = InputFieldCategory,
                            navigationBarStyle = createBlackNavigationBarStyle()
                        ),
                        nextRoute = router.createPushRoute(passwordRecoveryScreen),
                        backRoute = router.createPopRoute()
                    )
                }

            val smsCodeConfirmationScreen: TypedScreenDesc<Args.Parcel<ConfirmationScreen.Arg>, ConfirmationScreen> =
                registerScreen(ConfirmationScreen::class) {
                    signInFactory.createConfirmationScreen(
                        styles = ConfirmationScreen.Styles(
                            yellowButton = YellowButtonCategory,
                            instructionText = InstructionTextCategory,
                            textField = InputFieldCategory,
                            navigationBar = createBlackNavigationBarStyle()
                        ),
                        routeBack = router.createPopRoute(),
                        routeNext = router.createPushRoute(personalInfoScreen)
                    )
                }

            val signUpScreen: TypedScreenDesc<Args.Empty, SignUpScreen> =
                registerScreen(SignUpScreen::class) {
                    signInFactory.createSignUpScreen(
                        styles = SignUpScreen.Styles(
                            linkText = HTMLTextCategory,
                            textField = InputFieldCategory,
                            yellowButton = YellowButtonCategory,
                            navigationBar = createBlackNavigationBarStyle()
                        ),
                        smsCodeConfirmationRoute = router.createPushRoute(smsCodeConfirmationScreen) {
                            ConfirmationScreen.Arg(it.phone)
                        },
                        userAgreementRoute = router.createPushRoute(mockScreen),
                        backRoute = router.createPopRoute()
                    )
                }

            val signInScreen: TypedScreenDesc<Args.Empty, SignInScreen> =
                registerScreen(SignInScreen::class) {
                    signInFactory.createSignInScreen(
                        styles = SignInScreen.Styles(
                            titleText = BoldText25Category,
                            yellowButton = YellowButtonCategory,
                            textField = InputFieldCategory,
                            rightButtonTextField = InputFieldCategory,
                            yellowTextButton = YellowTextButtonCategory
                        ),
                        forgotPasswordRoute = router.createPushRoute(forgotPasswordScreen),
                        signUpRoute = router.createPushRoute(signUpScreen)
                    )

                }

            RootNavigationScreen(
                initialScreen = signInScreen,
                router = router
            )
        }
    }
}
