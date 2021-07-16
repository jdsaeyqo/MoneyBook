package com.example.data.mapper

import com.example.data.entity.MoneyEntity
import com.example.domain.model.Money

object MoneyMapper {

    fun mapperToMoney(moneyEntity: MoneyEntity) : Money{
        return Money(
            id = moneyEntity.id,
            checked = moneyEntity.checked,
            date = moneyEntity.date,
            money = moneyEntity.money,
            separation = moneyEntity.separation,
            use = moneyEntity.use,
            description = moneyEntity.description,
        )
    }
    fun mapperToMoneyEntity(money: Money) : MoneyEntity{
        return MoneyEntity(
            id = money.id,
            checked = money.checked,
            date = money.date,
            money = money.money,
            separation = money.separation,
            use = money.use,
            description = money.description,
        )
    }
}