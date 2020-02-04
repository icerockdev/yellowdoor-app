package com.icerock.yellowdoor.feature.login

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.StringResource


class SignInViewModel(
    val eventsDispatcher: EventsDispatcher<EventsListener>,
    var strings: Strings
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
}