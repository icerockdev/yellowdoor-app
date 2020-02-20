package com.icerock.yellowdoor.domain

import com.icerock.yellowdoor.domain.repository.AuthRepository


class DomainFactory {
    val authRepository: AuthRepository by lazy {
        AuthRepository()
    }
}