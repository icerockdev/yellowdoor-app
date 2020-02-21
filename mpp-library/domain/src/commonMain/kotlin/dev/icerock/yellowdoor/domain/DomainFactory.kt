/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.domain

class DomainFactory {
    val authRepository: AuthRepository by lazy { AuthRepository() }
}
