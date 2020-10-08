package com.grdj.devigettest.di

import com.grdj.devigettest.api.ErrorManagerHelper
import com.grdj.devigettest.api.ErrorManagerHelperImpl
import com.grdj.devigettest.api.RedditApiService
import com.grdj.devigettest.repository.Repository
import com.grdj.devigettest.repository.RepositoryImp
import com.grdj.devigettest.resources.ResourcesProvider
import com.grdj.devigettest.resources.ResourcesProviderImpl
import com.grdj.devigettest.util.network.NetworkManager
import com.grdj.devigettest.util.network.NetworkManagerImpl
import com.grdj.devigettest.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{
    single { ErrorManagerHelperImpl() as ErrorManagerHelper }
    single { ResourcesProviderImpl(get()) as ResourcesProvider }
    single { NetworkManagerImpl(get()) as NetworkManager }
    single { RepositoryImp(
        RedditApiService(),
        ErrorManagerHelperImpl(),
        NetworkManagerImpl(get()),
        ResourcesProviderImpl(get())
    ) as Repository }
    viewModel { MainViewModel(get(), RepositoryImp(
        RedditApiService(),
        ErrorManagerHelperImpl(),
        NetworkManagerImpl(get()),
        ResourcesProviderImpl(get())),
        ResourcesProviderImpl(get())
    )}
}