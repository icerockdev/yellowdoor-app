/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.di

import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.yellowdoor.domain.DomainFactory
import dev.icerock.yellowdoor.feature.forgotPassword.di.ForgotPasswordFactory
import dev.icerock.yellowdoor.feature.forgotPassword.model.ForgotPasswordRepository
import org.example.library.MR

fun DomainFactory.createForgotPasswordFactory(theme: Theme): ForgotPasswordFactory {
    return ForgotPasswordFactory(
        theme = theme,
        repository = object : ForgotPasswordRepository {
            override suspend fun changePassword(password: String, confirmation: String) {

            }

            override suspend fun recoverPassword(phone: String) {

            }
        },
        strings = object : ForgotPasswordFactory.Strings {
            override val nextButtonTitle: StringResource = MR.strings.password_recovery_next
            override val textFieldTitle: StringResource = MR.strings.password_recovery_phone_number
            override val title: StringResource = MR.strings.password_recovery_password_recovery
            override val newPassword: StringResource = MR.strings.password_recovery_new_password
            override val newPasswordRepeat: StringResource = MR.strings.password_recovery_repeat_password
            override val savePassword: StringResource = MR.strings.password_recovery_save_password
        },
        validation = object : ForgotPasswordFactory.Validation {
            override fun validatePassword(password: String): StringDesc? = null

            override fun validatePasswordConfirmation(password: String, confirmation: String): StringDesc? = null

            override fun validatePhone(phone: String): StringDesc? = null
        },
        images = object : ForgotPasswordFactory.Images {
            override val backButton: ImageResource = MR.images.back_icon
            override val backImage: ImageResource = MR.images.back_icon
        }
    )
}
