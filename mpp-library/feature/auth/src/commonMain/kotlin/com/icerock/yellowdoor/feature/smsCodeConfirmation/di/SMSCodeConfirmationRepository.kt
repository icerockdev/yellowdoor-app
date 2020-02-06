package com.icerock.yellowdoor.feature.smsCodeConfirmation.di


interface SMSCodeConfirmationRepository {
    suspend fun confirm(code: String)
}