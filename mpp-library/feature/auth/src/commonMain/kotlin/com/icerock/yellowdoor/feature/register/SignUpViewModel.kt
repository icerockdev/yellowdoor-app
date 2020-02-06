package com.icerock.yellowdoor.feature.register

import com.icerock.yellowdoor.feature.register.di.SignUpRepository
import dev.icerock.moko.fields.FormField
import dev.icerock.moko.fields.liveBlock
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.all
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.launch


class SignUpViewModel(
    private val repository: SignUpRepository,
    val eventsDispatcher: EventsDispatcher<SignUpViewModel.EventsListener>,
    private val validation: SignUpViewModel.Validation
) : ViewModel() {

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
        it.map { value: String ->
            return@map validation.validateRepeatPassword(
                password = passwordField.value(),
                repeatPassword = value
            )
        }
    })

    val isUserAgreementAccepted: MutableLiveData<Boolean> = MutableLiveData(false)

    val isFormValid: LiveData<Boolean> = listOf<LiveData<Boolean>>(
        lastNameField.isValid,
        firstNameField.isValid,
        phoneField.isValid,
        emailField.isValid,
        emailField.isValid,
        passwordField.isValid,
        repeatPasswordField.isValid
    ).all(true)

    fun didTapSignUpButton() {
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

    fun didTapUserAgreementButton() {

    }

    interface EventsListener {
        fun routeToUserAgreement()
        fun routeToSMSCodeConfirmation(phone: String)
    }

    interface Validation {
        fun validateNonEmpty(value: String): StringDesc?
        fun validatePhone(value: String): StringDesc?
        fun validateEmail(value: String): StringDesc?
        fun validatePassword(value: String): StringDesc?
        fun validateRepeatPassword(password: String, repeatPassword: String): StringDesc?
    }
}