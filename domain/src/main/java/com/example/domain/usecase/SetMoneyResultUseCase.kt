package com.example.domain.usecase

import com.example.domain.UseCase
import com.example.domain.model.Money
import java.text.DecimalFormat

class SetMoneyResultUseCase : UseCase {

    operator fun invoke(moneyList: List<Money>?) : Array<String>{

        val decimal = DecimalFormat("###,###")

        var income = 0
        var outcome = 0

        moneyList?.let {list ->
            list.forEach {
                if(it.checked == "수입"){
                    income += it.money.toInt()
                }else{
                    outcome += it.money.toInt()
                }
            }
        }

        val result  = income - outcome


        return arrayOf(decimal.format(income).toString(),decimal.format(outcome).toString(),decimal.format(result).toString())

    }

}