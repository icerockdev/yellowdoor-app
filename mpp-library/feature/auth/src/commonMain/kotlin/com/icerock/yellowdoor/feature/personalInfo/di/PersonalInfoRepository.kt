package com.icerock.yellowdoor.feature.personalInfo.di

import dev.icerock.moko.widgets.core.Image


interface PersonalInfoRepository {
    suspend fun savePersonalInfo(
        avatar: Image?,
        birthday: String?,
        region: Int?,
        city: Int?,
        education: String?,
        about: String?
    )
}