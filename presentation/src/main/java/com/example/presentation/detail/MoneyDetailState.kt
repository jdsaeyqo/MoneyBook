package com.example.presentation.detail

import com.example.domain.model.Money

sealed class MoneyDetailState {

    object UnInitialized : MoneyDetailState()

    object Loading : MoneyDetailState()

    data class Success(
        val moneyItem : Money
    ) : MoneyDetailState()

    object Delete : MoneyDetailState()

    object Modify : MoneyDetailState()

    object Error : MoneyDetailState()

    object Write : MoneyDetailState()

}