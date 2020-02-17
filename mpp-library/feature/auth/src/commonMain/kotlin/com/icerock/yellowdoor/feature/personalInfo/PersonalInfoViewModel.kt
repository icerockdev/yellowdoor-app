package com.icerock.yellowdoor.feature.personalInfo

import com.icerock.yellowdoor.feature.personalInfo.di.PersonalInfoRepository
import dev.icerock.moko.fields.FormField
import dev.icerock.moko.fields.liveBlock
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.readOnly
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
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

    private val _region: MutableLiveData<String> = MutableLiveData("")
    val region: LiveData<String> = _region.readOnly()

    private val _city: MutableLiveData<String> = MutableLiveData("")
    val city: LiveData<String> = _city.readOnly()

    val education: FormField<String, StringDesc> = FormField("", liveBlock {
        return@liveBlock "".desc()
    })

    val about: FormField<String, StringDesc> = FormField("", liveBlock {
        return@liveBlock "".desc()
    })

    fun didTapUploadNewPhotoButton() {

    }

    fun didTapBirthday() {

    }

    fun didTapRegion() {
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