package com.icerock.yellowdoor.factory

import com.icerock.yellowdoor.domain.DomainFactory
import com.icerock.yellowdoor.feature.register.SignUpScreen
import com.icerock.yellowdoor.feature.register.SignUpViewModel
import com.icerock.yellowdoor.feature.register.di.SignUpFactory
import com.icerock.yellowdoor.feature.register.di.SignUpRepository
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import org.example.library.MR


fun DomainFactory.createSignUpFactory(): SignUpFactory {
    return SignUpFactory(
        strings = object : SignUpScreen.Strings {
            override val lastName: StringResource = MR.strings.signUp_lastName
            override val firstName: StringResource = MR.strings.signUp_firstName
            override val phone: StringResource = MR.strings.signUp_phone
            override val email: StringResource = MR.strings.signUp_email
            override val password: StringResource = MR.strings.signUp_password
            override val repeatPassword: StringResource = MR.strings.signUp_repeatPassword
            override val signUp: StringResource = MR.strings.signUp_signUp
            override val iAcceptTheUserAgreement: StringResource =
                MR.strings.signUp_iAcceptTheUserAgreement
        },
        repository = object : SignUpRepository {
            override suspend fun signUp(
                lastName: String,
                firstName: String,
                phone: String,
                email: String,
                password: String
            ) {

            }
        },
        validation = object : SignUpViewModel.Validation {
            override fun validateEmail(value: String): StringDesc? {
                return null
            }

            override fun validateNonEmpty(value: String): StringDesc? {
                return null
            }

            override fun validatePassword(value: String): StringDesc? {
                return null
            }

            override fun validatePhone(value: String): StringDesc? {
                return null
            }

            override fun validateRepeatPassword(
                password: String,
                repeatPassword: String
            ): StringDesc? {
                return null
            }
        }
    )
}