package com.icerock.yellowdoor.feature.login.di

import com.icerock.yellowdoor.feature.login.SignInViewModel
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher


interface SignInRepository {
    suspend fun signIn(phone: String, password: String): String
}


class SignInFactory(
    val signInRepository: SignInRepository,
    val strings: SignInViewModel.Strings,
    val validation: SignInViewModel.Validation
) {
    fun createSignInViewModel(
        eventsDispatcher: EventsDispatcher<SignInViewModel.EventsListener>
    ): SignInViewModel {

        return SignInViewModel(
            repository = signInRepository,
            eventsDispatcher = eventsDispatcher,
            strings = strings,
            validation = validation
        )
    }
}