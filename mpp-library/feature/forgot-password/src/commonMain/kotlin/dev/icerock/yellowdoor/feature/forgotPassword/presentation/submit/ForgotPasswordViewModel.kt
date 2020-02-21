/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.feature.forgotPassword.presentation.submit

import dev.icerock.moko.fields.FormField
import dev.icerock.moko.fields.liveBlock
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.all
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.yellowdoor.feature.forgotPassword.model.ForgotPasswordRepository
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val repository: ForgotPasswordRepository,
    private val validation: Validation,
    val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel() {

    val phoneField: FormField<String, StringDesc> =
        FormField("", validation = liveBlock(validation::validatePhone))

    val isFormValid: LiveData<Boolean> = listOf<LiveData<Boolean>>(
        phoneField.isValid
    ).all(true)

    fun didTapNextButton() {
        if (!isFormValid.value)
            return

        viewModelScope.launch {
            try {
                repository.recoverPassword(phoneField.value())

                eventsDispatcher.dispatchEvent {
                    routeNext()
                }
            } catch (error: Throwable) {

            }
        }
    }

    fun didTapBackButton() {
        eventsDispatcher.dispatchEvent {
            routeBack()
        }
    }

    interface EventsListener {
        fun routeNext()
        fun routeBack()
    }

    interface Validation {
        fun validatePhone(phone: String): StringDesc?
    }
}
