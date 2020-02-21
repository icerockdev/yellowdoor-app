package com.icerock.yellowdoor.factory

import com.icerock.yellowdoor.domain.DomainFactory
import com.icerock.yellowdoor.feature.login.SignInScreen
import com.icerock.yellowdoor.feature.login.SignInViewModel
import com.icerock.yellowdoor.feature.login.di.SignInFactory
import com.icerock.yellowdoor.feature.login.di.SignInRepository
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import org.example.library.MR


fun DomainFactory.createSignInFactory(): SignInFactory {
    return SignInFactory(
        strings = object : SignInScreen.Strings {
            override val forgotPassword: StringResource = MR.strings.signIn_forgotPassword
            override val password: StringResource = MR.strings.signIn_password
            override val signIn: StringResource = MR.strings.signIn_signIn
            override val phone: StringResource = MR.strings.signIn_phone
            override val signUp: StringResource = MR.strings.signIn_signUp
        },
        signInRepository = object : SignInRepository {
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
}