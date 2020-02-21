/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.feature.forgotPassword.model

interface ForgotPasswordRepository {
    suspend fun changePassword(password: String, confirmation: String)
    suspend fun recoverPassword(phone: String)
}
