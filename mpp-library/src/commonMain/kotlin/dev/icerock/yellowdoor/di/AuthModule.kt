/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.di

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.yellowdoor.domain.DomainFactory
import dev.icerock.yellowdoor.feature.auth.di.AuthFactory
import dev.icerock.yellowdoor.feature.auth.model.AuthRepository
import dev.icerock.yellowdoor.feature.auth.presentation.SignInScreen
import dev.icerock.yellowdoor.feature.auth.presentation.SignInViewModel
import org.example.library.MR

fun DomainFactory.createAuthFactory(
    theme: Theme
): AuthFactory = AuthFactory(
    theme = theme,
    strings = object : SignInScreen.Strings {
        override val forgotPassword: StringResource = MR.strings.auth_forgot_password
        override val password: StringResource = MR.strings.auth_password
        override val signIn: StringResource = MR.strings.auth_sign_in
        override val phone: StringResource = MR.strings.auth_phone
        override val signUp: StringResource = MR.strings.auth_sign_up
    },
    authRepository = object : AuthRepository {
        override suspend fun signIn(phone: String, password: String): String {
            return authRepository.signIn(phone, password)
        }
    },
    validation = object : SignInViewModel.Validation {
        override fun validatePhone(phone: String): StringDesc? {
            return null
        }

        override fun validatePassword(password: String): StringDesc? {
            return null
        }
    }
)
