package com.icerock.yellowdoor.feature.selectItem

import com.icerock.yellowdoor.feature.selectItem.di.SelectItemRepository
import com.icerock.yellowdoor.feature.selectItem.items.SelectItemUnit
import dev.icerock.moko.mvvm.State
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.launch


class SelectItemViewModel(
    private val repository: SelectItemRepository,
    val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel() {

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