package com.icerock.yellowdoor.factory

import com.icerock.yellowdoor.domain.DomainFactory
import com.icerock.yellowdoor.feature.passwordRecovery.PasswordRecoveryScreen
import com.icerock.yellowdoor.feature.passwordRecovery.PasswordRecoveryViewModel
import com.icerock.yellowdoor.feature.passwordRecovery.di.PasswordRecoveryFactory
import com.icerock.yellowdoor.feature.passwordRecovery.di.PasswordRecoveryRepository
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import org.example.library.MR


fun DomainFactory.createPasswordRecoveryFactory(): PasswordRecoveryFactory {
    return PasswordRecoveryFactory(
        repository = object : PasswordRecoveryRepository {
            override suspend fun changePassword(password: String, confirmation: String) {

            }
        },
        strings = object : PasswordRecoveryScreen.Strings {
            override val newPassword: StringResource = MR.strings.passwordRecovery_newPassword
            override val newPasswordRepeat: StringResource =
                MR.strings.passwordRecovery_repeatPassword
            override val savePassword: StringResource = MR.strings.passwordRecovery_savePassword
            override val title: StringResource = MR.strings.passwordRecovery_passwordRecovery
        },
        images = object : PasswordRecoveryScreen.Images {
            override val backButton: ImageResource = MR.images.backIcon
        },
        validation = object : PasswordRecoveryViewModel.Validation {
            override fun validatePassword(password: String): StringDesc? {
                return null
            }

            override fun validatePasswordConfirmation(
                password: String,
                confirmation: String
            ): StringDesc? {
                return null
            }
        }
    )
}