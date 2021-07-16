package com.example.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Money
import com.example.domain.usecase.DeleteAllMoneyItemUseCase
import com.example.domain.usecase.GetMoneyListUseCase
import com.example.domain.usecase.SetMoneyResultUseCase
import com.example.presentation.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


internal class ListViewModel(
    private val getMoneyListUseCase: GetMoneyListUseCase,
    private val setMoneyResultUseCase : SetMoneyResultUseCase,
    private val deleteAllMoneyItemUseCase: DeleteAllMoneyItemUseCase
) : BaseViewModel(){

    private var _moneyListLiveData = MutableLiveData<MoneyState>(MoneyState.UnInitialized)
    val moneyListLiveData : LiveData<MoneyState> = _moneyListLiveData


    override fun fetchData(): Job = viewModelScope.launch {
        _moneyListLiveData.postValue(MoneyState.Loading)
        _moneyListLiveData.postValue(MoneyState.Success(getMoneyListUseCase()))
    }

    fun deleteAll() = viewModelScope.launch {
        _moneyListLiveData.postValue(MoneyState.Loading)
        deleteAllMoneyItemUseCase()
        _moneyListLiveData.postValue(MoneyState.Success(getMoneyListUseCase()))
    }

    fun setMoneyResult(moneyList: List<Money>) : Array<String>{

        return setMoneyResultUseCase(moneyList)

    }


}