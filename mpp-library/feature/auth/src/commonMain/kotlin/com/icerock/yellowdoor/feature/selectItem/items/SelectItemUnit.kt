package com.icerock.yellowdoor.feature.selectItem.items

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.widgets.*
import dev.icerock.moko.widgets.core.Image
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.style.view.SizeSpec
import dev.icerock.moko.widgets.style.view.WidgetSize
import dev.icerock.moko.widgets.units.UnitItemRoot
import dev.icerock.moko.widgets.units.WidgetsTableUnitItem


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
                            text = const(data.value.title)
                        )

                        val checkImage = +visibility(
                            child = image(
                                size = WidgetSize.Const(SizeSpec.Exact(18.0f), SizeSpec.Exact(14.0f)),
                                image = const(selectedImage)
                            ),
                            showed = data.value.isSelected
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