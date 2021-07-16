package com.example.domain.usecase

import com.example.domain.UseCase
import com.example.domain.repository.MoneyRepository

class DeleteAllMoneyItemUseCase(
    private val moneyRepository: MoneyRepository
) : UseCase {

    suspend operator fun invoke(){
        return moneyRepository.deleteAll()
    }
}