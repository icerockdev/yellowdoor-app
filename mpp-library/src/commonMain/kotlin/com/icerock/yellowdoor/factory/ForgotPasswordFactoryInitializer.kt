package com.icerock.yellowdoor.factory

import com.icerock.yellowdoor.domain.DomainFactory
import com.icerock.yellowdoor.feature.forgotPassword.ForgotPasswordScreen
import com.icerock.yellowdoor.feature.forgotPassword.ForgotPasswordViewModel
import com.icerock.yellowdoor.feature.forgotPassword.di.ForgotPasswordFactory
import com.icerock.yellowdoor.feature.forgotPassword.di.ForgotPasswordRepository
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import org.example.library.MR


fun DomainFactory.createForgotPasswordFactory(): ForgotPasswordFactory {
    return ForgotPasswordFactory(
        repository = object : ForgotPasswordRepository {
            override suspend fun recoverPassword(phone: String) {

            }
        },
        strings = object : ForgotPasswordScreen.Strings {
            override val nextButtonTitle: StringResource = MR.strings.passwordRecovery_next
            override val textFieldTitle: StringResource = MR.strings.passwordRecovery_phoneNumber
            override val title: StringResource = MR.strings.passwordRecovery_passwordRecovery
        },
        validation = object : ForgotPasswordViewModel.Validation {
            override fun validatePhone(phone: String): StringDesc? {
                return null
            }
        },
        images = object : ForgotPasswordScreen.Images {
            override val backImage: ImageResource = MR.images.backIcon
        }
    )
}