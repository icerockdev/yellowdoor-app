/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.feature.auth.presentation.signup

import dev.icerock.moko.fields.FormField
import dev.icerock.moko.fields.liveBlock
import dev.icerock.moko.fields.validate
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.all
import dev.icerock.moko.mvvm.livedata.mergeWith
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.yellowdoor.feature.auth.model.AuthRepository
import kotlinx.coroutines.launch


class SignUpViewModel(
    private val repository: AuthRepository,
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
    private val validation: Validation
) : ViewModel(), EventsDispatcherOwner<SignUpViewModel.EventsListener> {

    val lastNameField: FormField<String, StringDesc> =
        FormField("", liveBlock(validation::validateNonEmpty))
    val firstNameField: FormField<String, StringDesc> =
        FormField("", liveBlock(validation::validateNonEmpty))
    val phoneField: FormField<String, StringDesc> =
        FormField("", liveBlock(validation::validatePhone))
    val emailField: FormField<String, StringDesc> =
        FormField("", liveBlock(validation::validateEmail))
    val passwordField: FormField<String, StringDesc> =
        FormField("", liveBlock(validation::validatePassword))
    val repeatPasswordField: FormField<String, StringDesc> = FormField("", { it: LiveData<String> ->
        passwordField.data.mergeWith(it) { first, second ->
            validation.validateRepeatPassword(
                password = first,
                repeatPassword = second
            )
        }
    })

    val isUserAgreementAccepted: MutableLiveData<Boolean> = MutableLiveData(false)

    private val fields: List<FormField<*, *>> = listOf(
        lastNameField,
        firstNameField,
        phoneField,
        emailField,
        passwordField,
        repeatPasswordField
    )

    val isSignUpEnabled = fields.map { it.isValid }.plus(isUserAgreementAccepted).all(true)

    fun didTapSignUpButton() {
        if (!fields.validate()) return

        viewModelScope.launch {
            try {
                repository.signUp(
                    lastName = lastNameField.value(),
                    firstName = firstNameField.value(),
                    phone = phoneField.value().filter { it in '0'..'9' },
                    email = emailField.value(),
                    password = passwordField.value()
                )

                eventsDispatcher.dispatchEvent {
                    routeToSMSCodeConfirmation(phoneField.value())
                }
            } catch (error: Throwable) {

            }
        }
    }

    interface EventsListener {
        fun routeToUserAgreement()
        fun routeToSMSCodeConfirmation(phone: String)
    }

    interface Validation {
        fun validateNonEmpty(value: String): StringDesc?
        fun validatePhone(phone: String): StringDesc?
        fun validateEmail(email: String): StringDesc?
        fun validatePassword(password: String): StringDesc?
        fun validateRepeatPassword(password: String, repeatPassword: String): StringDesc?
    }
}