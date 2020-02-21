/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.feature.auth.model

interface AuthRepository {
    suspend fun signIn(phone: String, password: String): String
    suspend fun confirm(code: String)
    suspend fun signUp(
        lastName: String,
        firstName: String,
        phone: String,
        email: String,
        password: String
    )
}
