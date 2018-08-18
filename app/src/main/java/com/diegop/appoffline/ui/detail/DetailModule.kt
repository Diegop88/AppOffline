package com.diegop.appoffline.ui.detail

import com.diegop.appoffline.data.repository.AppRepository
import com.diegop.appoffline.domain.usecase.issue.GetIssuesByRepo
import dagger.Module
import dagger.Provides

@Module
class DetailModule {

    @Provides
    fun provideGetIssuesByRepo(repo: AppRepository) = GetIssuesByRepo(repo)

    @Provides
    fun provideFactory(getIssuesByRepo: GetIssuesByRepo) = DetailViewModel.Factory(getIssuesByRepo)
}
