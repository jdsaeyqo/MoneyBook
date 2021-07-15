package com.example.presentation.list

sealed class MoneyState {

    object UnInitialized : MoneyState()

    object Loading : MoneyState()

    data class Success(
        val moneyList : List<MoneyEntity>
    ) : MoneyState()

    object Error : MoneyState()
}