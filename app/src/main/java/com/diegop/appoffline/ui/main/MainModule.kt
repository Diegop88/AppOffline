package com.diegop.appoffline.ui.main

import com.diegop.appoffline.data.repository.AppRepository
import com.diegop.appoffline.domain.usecase.repo.GetReposByUser
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideGetReposByUser(repo: AppRepository) = GetReposByUser(repo)

    @Provides
    fun provideFactory(getReposByUser: GetReposByUser) = MainViewModel.Factory(getReposByUser)
}
