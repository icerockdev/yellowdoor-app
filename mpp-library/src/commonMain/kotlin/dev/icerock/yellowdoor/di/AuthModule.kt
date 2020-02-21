/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.di

import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.yellowdoor.domain.DomainFactory
import dev.icerock.yellowdoor.feature.auth.di.AuthFactory
import dev.icerock.yellowdoor.feature.auth.model.AuthRepository
import dev.icerock.yellowdoor.feature.auth.presentation.confirmation.ConfirmationScreen
import dev.icerock.yellowdoor.feature.auth.presentation.signin.SignInScreen
import dev.icerock.yellowdoor.feature.auth.presentation.signin.SignInViewModel
import dev.icerock.yellowdoor.feature.auth.presentation.signup.SignUpScreen
import dev.icerock.yellowdoor.feature.auth.presentation.signup.SignUpViewModel
import org.example.library.MR

fun DomainFactory.createAuthFactory(
    theme: Theme
): AuthFactory = AuthFactory(
    theme = theme,
    strings = AuthFactory.Strings(
        signIn = object : SignInScreen.Strings {
            override val password: StringResource = MR.strings.auth_password
            override val signIn: StringResource = MR.strings.auth_sign_in
            override val phone: StringResource = MR.strings.auth_phone
            override val signUp: StringResource = MR.strings.auth_sign_up
            override val forgotPassword: StringResource = MR.strings.auth_forgot_password
        },
        signUp = object : SignUpScreen.Strings {
            override val lastName: StringResource = MR.strings.register_last_name
            override val firstName: StringResource = MR.strings.register_first_name
            override val phone: StringResource = MR.strings.register_phone
            override val email: StringResource = MR.strings.register_email
            override val password: StringResource = MR.strings.register_password
            override val repeatPassword: StringResource = MR.strings.register_repeat_password
            override val signUp: StringResource = MR.strings.register_sign_up
            override val iAcceptTheUserAgreement: StringResource = MR.strings.register_accept_agreement
        },
        confirmation = object : ConfirmationScreen.Strings {
            override val next: StringResource = MR.strings.confirmation_next
            override val numberConfirmation: StringResource = MR.strings.confirmation_number_confirmation
            override val smsCode: StringResource = MR.strings.confirmation_sms_code
            override val smsCodeSent: StringResource = MR.strings.confirmation_code_sent
        }
    ),
    images = AuthFactory.Images(
        signUp = object : SignUpScreen.Images {
            override val backImage: ImageResource = MR.images.back_icon
        },
        confirmation = object : ConfirmationScreen.Images {
            override val backImage: ImageResource = MR.images.back_icon
        }
    ),
    validation = AuthFactory.Validation(
        signIn = object : SignInViewModel.Validation {
            override fun validatePhone(phone: String): StringDesc? = null

            override fun validatePassword(password: String): StringDesc? = null
        },
        signUp = object : SignUpViewModel.Validation {
            override fun validatePhone(phone: String): StringDesc? = null

            override fun validatePassword(password: String): StringDesc? = null

            override fun validateNonEmpty(value: String): StringDesc? = null

            override fun validateEmail(email: String): StringDesc? = null

            override fun validateRepeatPassword(password: String, repeatPassword: String): StringDesc? = null
        }
    ),
    authRepository = object : AuthRepository {
        override suspend fun signIn(phone: String, password: String): String {
            return authRepository.signIn(phone, password)
        }

        override suspend fun confirm(code: String) {

        }

        override suspend fun signUp(
            lastName: String,
            firstName: String,
            phone: String,
            email: String,
            password: String
        ) {

        }
    }
)
