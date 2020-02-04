package com.icerock.yellowdoor.feature.login

import com.icerock.yellowdoor.feature.login.di.SignInRepository
import dev.icerock.moko.fields.FormField
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc


class SignInViewModel(
    private val repository: SignInRepository,
    val eventsDispatcher: EventsDispatcher<EventsListener>,
    var strings: Strings,
    private val validation: Validation
): ViewModel() {

    interface EventsListener {
        fun routeToSignUp()
        fun routeToForgotPassword()
    }

    interface Strings {
        val signIn: StringResource
        val signUp: StringResource
        val phone: StringResource
        val password: StringResource
        val forgotPassword: StringResource
    }

    interface Validation {
        fun validatePhone(phone: String?): StringDesc?
        fun validatePassword(password: String?): StringDesc?
    }

    val phoneField = FormField("", {
        it.map { value: String ->
            return@map validation.validatePhone(value)
        }
    })

    val passwordField = FormField("", {
        it.map { value: String ->
            return@map validation.validatePassword(value)
        }
    })

    fun didTapSignUpButton() {

    }

    fun didTapSignInButton() {

    }
}