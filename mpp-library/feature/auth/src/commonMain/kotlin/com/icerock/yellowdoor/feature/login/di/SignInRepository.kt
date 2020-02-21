package com.icerock.yellowdoor.feature.login.di


interface SignInRepository {
    suspend fun signIn(phone: String, password: String): String
}