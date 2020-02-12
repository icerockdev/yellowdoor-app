package com.icerock.yellowdoor.feature.personalInfo

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.readOnly
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.widgets.core.Image


class PersonalInfoViewModel(
    private val images: PersonalInfoScreen.Images,
    val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel() {

    private val _avatarImage: MutableLiveData<Image> =
        MutableLiveData<Image>(images.avatarPlaceholderImage)
    val avatarImage: LiveData<Image> = _avatarImage.readOnly()


    interface EventsListener {

    }
}