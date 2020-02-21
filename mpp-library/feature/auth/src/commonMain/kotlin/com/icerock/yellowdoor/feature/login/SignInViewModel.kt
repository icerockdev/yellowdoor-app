package com.icerock.yellowdoor.feature.login

import com.icerock.yellowdoor.feature.login.di.SignInRepository
import dev.icerock.moko.fields.FormField
import dev.icerock.moko.fields.liveBlock
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc


class SignInViewModel(
    private val repository: SignInRepository,
    val eventsDispatcher: EventsDispatcher<EventsListener>,
    private val validation: Validation
) : ViewModel() {

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