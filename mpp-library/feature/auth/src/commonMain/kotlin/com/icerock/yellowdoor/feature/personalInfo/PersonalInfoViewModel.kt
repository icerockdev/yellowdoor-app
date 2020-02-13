package com.icerock.yellowdoor.feature.personalInfo

import com.icerock.yellowdoor.feature.personalInfo.di.PersonalInfoRepository
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.readOnly
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.widgets.core.Image


class PersonalInfoViewModel(
    private val repository: PersonalInfoRepository,
    private val images: PersonalInfoScreen.Images,
    val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel() {

    private val _avatarImage: MutableLiveData<Image> =
        MutableLiveData<Image>(images.avatarPlaceholderImage)
    val avatarImage: LiveData<Image> = _avatarImage.readOnly()

    private val _birthday: MutableLiveData<String> = MutableLiveData("")
    val birthday: LiveData<String> = _birthday.readOnly()


    fun didTapUploadNewPhotoButton() {

    }

    fun didTapBirthday() {

    }

    fun didTapRegion() {
        println("2")
        eventsDispatcher.dispatchEvent {
            routeToRegionSelection()
        }
    }

    fun didTapCity() {

    }

    fun didTapCloseButton() {

    }

    fun didTapSaveButton() {

    }

    interface EventsListener {
        fun routeToNews()
        fun routeToRegionSelection()
    }
}