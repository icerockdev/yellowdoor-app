package com.icerock.yellowdoor.factory

import com.icerock.yellowdoor.domain.DomainFactory
import com.icerock.yellowdoor.feature.smsCodeConfirmation.SMSCodeConfirmationScreen
import com.icerock.yellowdoor.feature.smsCodeConfirmation.di.SMSCodeConfirmationFactory
import com.icerock.yellowdoor.feature.smsCodeConfirmation.di.SMSCodeConfirmationRepository
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import org.example.library.MR


fun DomainFactory.createSMSCodeConfirmationFactory(): SMSCodeConfirmationFactory {
    return SMSCodeConfirmationFactory(
        repository = object : SMSCodeConfirmationRepository {
            override suspend fun confirm(code: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        },
        strings = object : SMSCodeConfirmationScreen.Strings {
            override val next: StringResource = MR.strings.smsCodeConfirmation_next
            override val numberConfirmation: StringResource =
                MR.strings.smsCodeConfirmation_numberConfirmation
            override val smsCode: StringResource = MR.strings.smsCodeConfirmation_smsCode
            override val smsCodeSent: StringResource =
                MR.strings.smsCodeConfirmation_smsCodeSentToPhone
        },
        images = object: SMSCodeConfirmationScreen.Images {
            override val backImage: ImageResource = MR.images.backIcon
        }
    )
}