package com.icerock.yellowdoor.feature.register.di


interface SignUpRepository {
    suspend fun signUp(
        lastName: String,
        firstName: String,
        phone: String,
        email: String,
        password: String
    )
}