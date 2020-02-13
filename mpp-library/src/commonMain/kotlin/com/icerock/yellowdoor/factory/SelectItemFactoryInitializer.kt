package com.icerock.yellowdoor.factory

import com.icerock.yellowdoor.domain.DomainFactory
import com.icerock.yellowdoor.feature.selectItem.SelectItemScreen
import com.icerock.yellowdoor.feature.selectItem.di.SelectItemFactory
import com.icerock.yellowdoor.feature.selectItem.di.SelectItemRepository
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.widgets.core.Image
import org.example.library.MR


fun DomainFactory.createSelectItemFactory(): SelectItemFactory {
    return SelectItemFactory(
        repository = object: SelectItemRepository {
            override suspend fun items(): List<String> {
                return listOf<String>("Item0", "Item1", "Item2", "Item3")
            }
        },
        images = object: SelectItemScreen.Images {
            override val backImage: ImageResource = MR.images.backIcon
            override val checkmarkImage: Image = Image.resource(MR.images.checkmark)
        },
        strings = object: SelectItemScreen.Strings {
            override val title: StringResource = MR.strings.selectRegion_region
        }
    )
}