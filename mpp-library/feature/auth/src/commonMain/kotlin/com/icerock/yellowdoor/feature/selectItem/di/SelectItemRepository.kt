package com.icerock.yellowdoor.feature.selectItem.di


interface SelectItemRepository {
    suspend fun items(): List<String>
}