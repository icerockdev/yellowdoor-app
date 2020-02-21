/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.feature.auth.presentation

import dev.icerock.moko.fields.FormField
import dev.icerock.moko.fields.liveBlock
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.yellowdoor.feature.auth.model.AuthRepository

class SignInViewModel(
    private val authRepository: AuthRepository,
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
    validation: Validation
) : ViewModel(), EventsDispatcherOwner<SignInViewModel.EventsListener> {

    val phoneField: FormField<String, StringDesc> =
        FormField("", validation = liveBlock(validation::validatePhone))
    val passwordField: FormField<String, StringDesc> =
        FormField("", validation = liveBlock(validation::validatePhone))

    fun didTapSignUpButton() {
        eventsDispatcher.dispatchEvent {
            routeToSignUp()
        }
    }

    fun didTapSignInButton() {
        // TODO signin action
    }

    fun didTapForgotPassword() {
        eventsDispatcher.dispatchEvent {
            routeToForgotPassword()
        }
    }

    interface EventsListener {
        fun routeToSignUp()
        fun routeToForgotPassword()
    }

    interface Validation {
        fun validatePhone(phone: String): StringDesc?
        fun validatePassword(password: String): StringDesc?
    }
}
