package com.example.domain.usecase

import com.example.domain.UseCase
import com.example.domain.model.Money
import com.example.domain.repository.MoneyRepository

class GetMoneyListUseCase(
    private val moneyRepository: MoneyRepository
) : UseCase {

    suspend operator fun invoke():List<Money> {
        return moneyRepository.getMoneyList()
    }
}