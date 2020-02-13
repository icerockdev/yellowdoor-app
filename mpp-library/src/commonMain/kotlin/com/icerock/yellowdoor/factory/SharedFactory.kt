package com.icerock.yellowdoor.factory

import com.icerock.yellowdoor.domain.DomainFactory
import com.icerock.yellowdoor.feature.forgotPassword.di.ForgotPasswordFactory
import com.icerock.yellowdoor.feature.login.di.SignInFactory
import com.icerock.yellowdoor.feature.passwordRecovery.di.PasswordRecoveryFactory
import com.icerock.yellowdoor.feature.personalInfo.di.PersonalInfoFactory
import com.icerock.yellowdoor.feature.register.di.SignUpFactory
import com.icerock.yellowdoor.feature.selectItem.di.SelectItemFactory
import com.icerock.yellowdoor.feature.smsCodeConfirmation.di.SMSCodeConfirmationFactory


class SharedFactory {
    val domainFactory: DomainFactory = DomainFactory()
    val signInFactory: SignInFactory = domainFactory.createSignInFactory()
    val signUpFactory: SignUpFactory = domainFactory.createSignUpFactory()
    val smsCodeConfirmationFactory: SMSCodeConfirmationFactory =
        domainFactory.createSMSCodeConfirmationFactory()
    val forgotPasswordFactory: ForgotPasswordFactory = domainFactory.createForgotPasswordFactory()
    val passwordRecoveryFactory: PasswordRecoveryFactory =
        domainFactory.createPasswordRecoveryFactory()
    val personalInfoFactory: PersonalInfoFactory = domainFactory.createPersonalInfoFactory()
    val selectItemFactory: SelectItemFactory = domainFactory.createSelectItemFactory()
}