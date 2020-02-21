/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.feature.selection.presentation

import dev.icerock.yellowdoor.feature.selection.model.SelectionSource
import dev.icerock.yellowdoor.feature.selection.units.SelectItemUnit
import dev.icerock.moko.mvvm.State
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.launch

class SelectItemViewModel(
    private val repository: SelectionSource,
    override val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel(), EventsDispatcherOwner<SelectItemViewModel.EventsListener> {

    val items: MutableLiveData<State<List<SelectItemUnit.Data>, StringDesc>> =
        MutableLiveData(State.Loading())

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                items.value = State.Loading()

                val strings: List<String> = repository.items()

                items.value = State.Data(strings.mapIndexed { index: Int, value: String ->
                    return@mapIndexed SelectItemUnit.Data(
                        id = index.toLong(),
                        title = value,
                        didTapBlock = {
                            didTapItem(index)
                        },
                        isSelected = MutableLiveData<Boolean>(false)
                    )
                })
            } catch (error: Throwable) {
                val message: String = error.message ?: return@launch

                items.value = State.Error(message.desc())
            }
        }
    }

    private fun didTapItem(index: Int) {
        val items: List<SelectItemUnit.Data> = items.value.dataValue() ?: return

        for ((i: Int, item: SelectItemUnit.Data) in items.withIndex())
            item.isSelected.value = (i == index)
    }

    interface EventsListener {
        //fun didSelectItem(index: Int)
    }
}
