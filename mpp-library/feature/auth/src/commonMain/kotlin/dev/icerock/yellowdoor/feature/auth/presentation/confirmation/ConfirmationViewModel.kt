/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.feature.auth.presentation.confirmation

import dev.icerock.moko.fields.FormField
import dev.icerock.moko.fields.liveBlock
import dev.icerock.moko.fields.validate
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.yellowdoor.feature.auth.model.AuthRepository
import kotlinx.coroutines.launch


class ConfirmationViewModel(
    private val authRepository: AuthRepository,
    val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel() {

    val smsCodeField: FormField<String, StringDesc> = FormField("", validation = liveBlock { null })

    private val fields = listOf(
        smsCodeField
    )

    fun didTapNextButton() {
        if (!fields.validate()) return

        viewModelScope.launch {
            try {
                authRepository.confirm(smsCodeField.value())

                eventsDispatcher.dispatchEvent {
                    routeToPersonalData()
                }
            } catch (error: Throwable) {

            }
        }
    }

    interface EventsListener {
        fun routeToPersonalData()
    }
}
