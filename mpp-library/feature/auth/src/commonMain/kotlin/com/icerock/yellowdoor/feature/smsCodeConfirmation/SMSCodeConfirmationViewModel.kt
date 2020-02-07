package com.icerock.yellowdoor.feature.smsCodeConfirmation

import com.icerock.yellowdoor.feature.smsCodeConfirmation.di.SMSCodeConfirmationRepository
import dev.icerock.moko.fields.FormField
import dev.icerock.moko.fields.liveBlock
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.all
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.launch


class SMSCodeConfirmationViewModel(
    private val repository: SMSCodeConfirmationRepository,
    val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel() {

    val smsCodeField: FormField<String, StringDesc> =
        FormField("", validation = liveBlock { null })

    val isFormValid: LiveData<Boolean> = listOf<LiveData<Boolean>>(
        smsCodeField.isValid
    ).all(true)

    fun didTapNextButton() {
        if (!isFormValid.value) {
            return
        }

        viewModelScope.launch {
            try {
                repository.confirm(smsCodeField.value())

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