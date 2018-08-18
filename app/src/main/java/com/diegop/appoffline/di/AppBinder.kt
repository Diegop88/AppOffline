package com.diegop.appoffline.di

import com.diegop.appoffline.ui.detail.DetailActivity
import com.diegop.appoffline.ui.detail.DetailModule
import com.diegop.appoffline.ui.main.MainActivity
import com.diegop.appoffline.ui.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppBinder {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [DetailModule::class])
    abstract fun bindDetailActivity(): DetailActivity
}