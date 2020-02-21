/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.feature.forgotPassword.presentation.recovery

import dev.icerock.moko.fields.FormField
import dev.icerock.moko.fields.liveBlock
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.all
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.yellowdoor.feature.forgotPassword.model.ForgotPasswordRepository
import kotlinx.coroutines.launch

class PasswordRecoveryViewModel(
    private val repository: ForgotPasswordRepository,
    private val validation: Validation,
    val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel() {

    val passwordField: FormField<String, StringDesc> =
        FormField("", validation = liveBlock(validation::validatePassword))
    val passwordConfirmationField: FormField<String, StringDesc> =
        FormField("", validation = { field: LiveData<String> ->
            field.map { value: String ->
                return@map validation.validatePasswordConfirmation(passwordField.value(), value)
            }
        })

    val isFormValid: LiveData<Boolean> = listOf<LiveData<Boolean>>(
        passwordField.isValid,
        passwordConfirmationField.isValid
    ).all(true)

    fun didTapSavePasswordButton() {
        if (!isFormValid.value)
            return

        viewModelScope.launch {
            try {
                repository.changePassword(passwordField.value(), passwordConfirmationField.value())

                eventsDispatcher.dispatchEvent {
                    routeToAuth()
                }
            } catch (error: Throwable) {

            }
        }
    }

    interface EventsListener {
        fun routeToAuth()
    }

    interface Validation {
        fun validatePassword(password: String): StringDesc?
        fun validatePasswordConfirmation(password: String, confirmation: String): StringDesc?
    }
}
