package com.icerock.yellowdoor.domain.repository


class AuthRepository {

    suspend fun signIn(phone: String, password: String): String {
        return "token"
    }
}