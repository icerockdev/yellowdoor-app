package com.icerock.yellowdoor.feature.passwordRecovery.di


interface PasswordRecoveryRepository {
    suspend fun changePassword(password: String, confirmation: String)
}