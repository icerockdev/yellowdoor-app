/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.di

import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.core.Image
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.yellowdoor.domain.DomainFactory
import dev.icerock.yellowdoor.feature.profile.di.ProfileFactory
import dev.icerock.yellowdoor.feature.profile.model.PersonalInfoRepository
import dev.icerock.yellowdoor.feature.profile.presentation.PersonalInfoScreen
import org.example.library.MR

fun DomainFactory.createProfileFactory(theme: Theme): ProfileFactory {
    return ProfileFactory(
        theme = theme,
        repository = object : PersonalInfoRepository {
            override suspend fun savePersonalInfo(
                avatar: Image?,
                birthday: String?,
                region: Int?,
                city: Int?,
                education: String?,
                about: String?
            ) {

            }
        },
        images = object : PersonalInfoScreen.Images {
            override val avatarPlaceholderImage: Image = Image.resource(MR.images.avatar_placeholder)
            override val closeImage: ImageResource = MR.images.close_icon
            override val rightButtonArrow: Image = Image.resource(MR.images.right_button_arrow)
        },
        strings = object : PersonalInfoScreen.Strings {
            override val aboutMe: StringDesc = MR.strings.profile_about_me.desc()
            override val birthday: StringDesc = MR.strings.profile_birthday.desc()
            override val city: StringDesc = MR.strings.profile_city.desc()
            override val education: StringDesc = MR.strings.profile_education.desc()
            override val notIndicated: StringDesc = MR.strings.profile_not_indicated.desc()
            override val photo: StringDesc = MR.strings.profile_photo.desc()
            override val region: StringDesc = MR.strings.profile_region.desc()
            override val save: StringDesc = MR.strings.profile_save.desc()
            override val title: StringDesc = MR.strings.profile_personal_data.desc()
            override val uploadNewPhoto: StringDesc = MR.strings.profile_upload_photo.desc()
            override val video: StringDesc = MR.strings.profile_video.desc()
        }
    )
}
