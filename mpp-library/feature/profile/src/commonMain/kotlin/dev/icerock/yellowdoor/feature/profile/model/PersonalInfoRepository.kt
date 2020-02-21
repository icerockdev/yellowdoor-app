/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.feature.profile.model

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
