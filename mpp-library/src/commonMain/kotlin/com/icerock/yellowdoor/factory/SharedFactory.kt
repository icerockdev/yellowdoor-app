package com.icerock.yellowdoor.factory

import com.icerock.yellowdoor.domain.DomainFactory
import com.icerock.yellowdoor.feature.login.di.SignInFactory
import com.icerock.yellowdoor.feature.register.di.SignUpFactory
import com.icerock.yellowdoor.feature.smsCodeConfirmation.di.SMSCodeConfirmationFactory


class SharedFactory {
    val domainFactory: DomainFactory = DomainFactory()
    val signInFactory: SignInFactory = domainFactory.createSignInFactory()
    val signUpFactory: SignUpFactory = domainFactory.createSignUpFactory()
    val smsCodeConfirmationFactory: SMSCodeConfirmationFactory =
        domainFactory.createSMSCodeConfirmationFactory()
}