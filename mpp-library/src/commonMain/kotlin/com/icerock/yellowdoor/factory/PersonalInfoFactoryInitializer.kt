package com.icerock.yellowdoor.factory

import com.icerock.yellowdoor.domain.DomainFactory
import com.icerock.yellowdoor.feature.personalInfo.PersonalInfoScreen
import com.icerock.yellowdoor.feature.personalInfo.di.PersonalInfoFactory
import com.icerock.yellowdoor.feature.personalInfo.di.PersonalInfoRepository
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.core.Image
import org.example.library.MR


fun DomainFactory.createPersonalInfoFactory(): PersonalInfoFactory {
    return PersonalInfoFactory(
        repository = object: PersonalInfoRepository {
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
        images = object: PersonalInfoScreen.Images {
            override val avatarPlaceholderImage: Image = Image.resource(MR.images.avatarPlaceholder)
            override val closeImage: ImageResource = MR.images.closeIcon
            override val rightButtonArrow: Image = Image.resource(MR.images.rightButtonArrow)
        },
        strings = object: PersonalInfoScreen.Strings {
            override val aboutMe: StringDesc = MR.strings.personalData_aboutMe.desc()
            override val birthday: StringDesc = MR.strings.personalData_birthday.desc()
            override val city: StringDesc = MR.strings.personalData_city.desc()
            override val education: StringDesc = MR.strings.personalData_education.desc()
            override val notIndicated: StringDesc = MR.strings.personalData_notIndicated.desc()
            override val photo: StringDesc = MR.strings.personalData_photo.desc()
            override val region: StringDesc = MR.strings.personalData_region.desc()
            override val save: StringDesc = MR.strings.personalData_save.desc()
            override val title: StringDesc = MR.strings.personalData_personalData.desc()
            override val uploadNewPhoto: StringDesc = MR.strings.personalData_uploadNewPhoto.desc()
            override val video: StringDesc = MR.strings.personalData_video.desc()
        }
    )
}