package com.example.presentation.module

import com.example.data.local.dao.provideDB
import com.example.data.local.dao.provideToDoDao
import com.example.data.repository.MoneyRepositoryImpl
import com.example.domain.repository.MoneyRepository
import com.example.domain.usecase.*
import com.example.presentation.detail.DetailMode
import com.example.presentation.detail.DetailViewModel
import com.example.presentation.list.ListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {

    single { Dispatchers.Main }
    single { Dispatchers.IO }

    factory { GetMoneyListUseCase(get()) }
    factory { GetMoneyItemUseCase(get()) }
    factory { SetMoneyResultUseCase() }
    factory { InsertMoneyListUseCase(get()) }
    factory { InsertMoneyUseCase(get()) }
    factory { DeleteMoneyItemUseCase(get()) }
    factory { DeleteAllMoneyItemUseCase(get()) }
    factory { UpdateMoneyUseCase(get()) }


    single<MoneyRepository>{ MoneyRepositoryImpl(get(),get()) }

    single { provideDB(androidApplication()) }
    single { provideToDoDao(get()) }

    viewModel{ListViewModel(get(),get(),get(),get())}
    viewModel{ (detailMode : DetailMode, id : Long) -> DetailViewModel(detailMode,id,get(),get(),get(),get())}


}