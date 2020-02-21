/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.feature.selection.units

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.flatMap
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.TextWidget
import dev.icerock.moko.widgets.clickable
import dev.icerock.moko.widgets.constraint
import dev.icerock.moko.widgets.core.Image
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.image
import dev.icerock.moko.widgets.style.view.SizeSpec
import dev.icerock.moko.widgets.style.view.WidgetSize
import dev.icerock.moko.widgets.text
import dev.icerock.moko.widgets.units.UnitItemRoot
import dev.icerock.moko.widgets.units.WidgetsTableUnitItem
import dev.icerock.moko.widgets.visibility

class SelectItemUnit(
    private val theme: Theme,
    private val titleCategory: TextWidget.Category,
    private val selectedImage: Image,
    data: Data
) : WidgetsTableUnitItem<SelectItemUnit.Data>(
    itemId = data.id,
    data = data
) {

    override val reuseId: String = "SelectItemUnit"

    override fun createWidget(data: LiveData<Data>): UnitItemRoot {
        return UnitItemRoot.from(
            theme.clickable(
                child = theme.constraint(
                    size = WidgetSize.WidthAsParentHeightWrapContent
                ) {
                    with(theme) {
                        val titleText = +text(
                            category = titleCategory,
                            size = WidgetSize.WrapContent,
                            text = data.map { it.title.desc() as StringDesc }
                        )

                        val checkImage = +visibility(
                            child = image(
                                size = WidgetSize.Const(SizeSpec.Exact(18.0f), SizeSpec.Exact(14.0f)),
                                image = const(selectedImage)
                            ),
                            showed = data.flatMap { it.isSelected }
                        )

                        constraints {
                            checkImage.rightToRight(root).offset(16)
                            checkImage.centerYToCenterY(root)

                            titleText.leftToLeft(root).offset(16)
                            titleText.topToTop(root).offset(16)
                            titleText.rightToLeft(checkImage).offset(16)
                            titleText.bottomToBottom(root).offset(16)
                        }
                    }
                },
                onClick = {
                    data.value.didTapBlock?.invoke()
                }
            )
        )
    }

    data class Data(
        val id: Long,
        val title: String,
        val isSelected: MutableLiveData<Boolean>,
        val didTapBlock: (() -> Unit)?
    )
}
