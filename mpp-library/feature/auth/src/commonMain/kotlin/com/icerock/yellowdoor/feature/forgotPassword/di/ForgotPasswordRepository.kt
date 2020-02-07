package com.icerock.yellowdoor.feature.forgotPassword.di


interface ForgotPasswordRepository {
    suspend fun recoverPassword(phone: String)
}