package com.grdj.devigettest.di

import com.grdj.devigettest.api.NetworkHelper
import com.grdj.devigettest.api.NetworkHelperImpl
import com.grdj.devigettest.api.RedditApi
import com.grdj.devigettest.api.RedditApiService
import com.grdj.devigettest.repository.Repository
import com.grdj.devigettest.repository.RepositoryImp
import com.grdj.devigettest.resources.ResourcesProvider
import com.grdj.devigettest.resources.ResourcesProviderImpl
import com.grdj.devigettest.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{
    single { NetworkHelperImpl() as NetworkHelper }
    single { RepositoryImp(
        RedditApiService(),
        NetworkHelperImpl()
    ) as Repository }
    single { ResourcesProviderImpl(get()) as ResourcesProvider }
    viewModel { MainViewModel(get(), RepositoryImp(
        RedditApiService(),
        NetworkHelperImpl()),
        ResourcesProviderImpl(get())
    )}
}