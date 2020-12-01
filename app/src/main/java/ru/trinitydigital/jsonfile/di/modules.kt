package ru.trinitydigital.jsonfile.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.trinitydigital.jsonfile.ui.main.MainViewModel


val viewModelModule = module {
    viewModel { MainViewModel() }
}

val appModules = listOf(viewModelModule)