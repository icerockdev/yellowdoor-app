/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.domain

class AuthRepository {

    suspend fun signIn(phone: String, password: String): String {
        return "token"
    }
}
