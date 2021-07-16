package com.example.domain.usecase

import com.example.domain.UseCase
import com.example.domain.repository.MoneyRepository

class DeleteMoneyItemUseCase(
    private val moneyRepository: MoneyRepository
) : UseCase {

    suspend operator fun invoke(id:Long){
        return moneyRepository.deleteMoneyItem(id)
    }
}