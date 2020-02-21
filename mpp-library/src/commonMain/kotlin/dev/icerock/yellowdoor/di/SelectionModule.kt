/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.di

import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.widgets.core.Image
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.yellowdoor.domain.DomainFactory
import dev.icerock.yellowdoor.feature.selection.di.SelectionFactory
import dev.icerock.yellowdoor.feature.selection.model.SelectionSource
import dev.icerock.yellowdoor.feature.selection.presentation.SelectItemScreen
import org.example.library.MR

fun DomainFactory.createSelectionFactory(theme: Theme): SelectionFactory {
    return SelectionFactory(
        theme = theme,
        source = object : SelectionSource {
            override suspend fun items(): List<String> {
                return listOf<String>("Item0", "Item1", "Item2", "Item3")
            }
        },
        images = object : SelectItemScreen.Images {
            override val backImage: ImageResource = MR.images.back_icon
            override val checkmarkImage: Image = Image.resource(MR.images.checkmark)
        },
        strings = object : SelectItemScreen.Strings {
            override val title: StringResource = MR.strings.selection_region
        }
    )
}
