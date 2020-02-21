package com.icerock.yellowdoor.factory

import com.icerock.yellowdoor.domain.DomainFactory
import com.icerock.yellowdoor.feature.login.di.SignInFactory


class SharedFactory {
    val domainFactory: DomainFactory = DomainFactory()
    val signInFactory: SignInFactory = domainFactory.createSignInFactory()
}