package com.example.presentation.list

import com.example.domain.model.Money

sealed class MoneyState {

    object UnInitialized : MoneyState()

    object Loading : MoneyState()

    data class Success(
        val moneyList : List<Money>
    ) : MoneyState()

    object Error : MoneyState()
}