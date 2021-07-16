package com.example.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Money
import com.example.domain.usecase.DeleteMoneyItemUseCase
import com.example.domain.usecase.GetMoneyItemUseCase
import com.example.domain.usecase.InsertMoneyUseCase
import com.example.domain.usecase.UpdateMoneyUseCase
import com.example.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

internal class DetailViewModel(
    var detailMode: DetailMode,
    var id : Long = -1,
    private val getMoneyItemUseCase: GetMoneyItemUseCase,
    private val deleteMoneyItemUseCase: DeleteMoneyItemUseCase,
    private val updateMoneyUseCase: UpdateMoneyUseCase,
    private val insertMoneyUseCase: InsertMoneyUseCase
) : BaseViewModel() {

    private var _moneyDetailLiveData = MutableLiveData<MoneyDetailState>(MoneyDetailState.UnInitialized)
    val moneyDetailLiveData : LiveData<MoneyDetailState> = _moneyDetailLiveData

    override fun fetchData() = viewModelScope.launch {
        when(detailMode){
            DetailMode.WRITE -> {
                _moneyDetailLiveData.postValue(MoneyDetailState.Write)
            }
            DetailMode.DETAIL -> {
                _moneyDetailLiveData.postValue(MoneyDetailState.Loading)
                try {
                    getMoneyItemUseCase(id)?.let {
                        _moneyDetailLiveData.postValue(MoneyDetailState.Success(it))
                    }?: kotlin.run {
                        _moneyDetailLiveData.postValue(MoneyDetailState.Error)
                    }
                } catch (e : Exception){
                    e.printStackTrace()
                    _moneyDetailLiveData.postValue(MoneyDetailState.Error)
                }
            }
        }
    }

    fun deleteMoney() = viewModelScope.launch {
        _moneyDetailLiveData.postValue(MoneyDetailState.Loading)
        try {
            deleteMoneyItemUseCase(id)
            _moneyDetailLiveData.postValue(MoneyDetailState.Delete)
        }catch (e : Exception){
            e.printStackTrace()
            _moneyDetailLiveData.postValue(MoneyDetailState.Error)
        }
    }

    fun setModifyMode() = viewModelScope.launch {
        _moneyDetailLiveData.postValue(MoneyDetailState.Modify)
    }

    fun writeMoney(
        checked : String,
        date : String,
        money : String,
        separation : String,
        use : String,
        description: String

    ) = viewModelScope.launch {
        _moneyDetailLiveData.postValue(MoneyDetailState.Loading)
        when(detailMode){

            DetailMode.WRITE -> {
                try {
                    val moneyData = Money(0,checked = checked,date = date,money = money,separation = separation,use = use,description = description)
                    id = insertMoneyUseCase(moneyData)
                    _moneyDetailLiveData.postValue(MoneyDetailState.Success(moneyData))
                    detailMode  = DetailMode.DETAIL
                }catch (e : Exception){
                    e.printStackTrace()
                    _moneyDetailLiveData.postValue(MoneyDetailState.Error)
                }
            }

            DetailMode.DETAIL -> {
                try {
                    getMoneyItemUseCase(id)?.let {
                        val updateMoney = it.copy(checked = checked,date = date,money = money,separation = separation,use = use,description = description)

                        updateMoneyUseCase(updateMoney)
                        _moneyDetailLiveData.postValue(MoneyDetailState.Success(updateMoney))
                    } ?: kotlin.run {
                        _moneyDetailLiveData.postValue(MoneyDetailState.Error)
                    }
                }catch (e : Exception){
                    e.printStackTrace()
                    _moneyDetailLiveData.postValue(MoneyDetailState.Error)
                }
            }
        }
    }

}