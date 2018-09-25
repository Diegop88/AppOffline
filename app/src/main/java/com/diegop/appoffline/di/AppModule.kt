package com.diegop.appoffline.di

import android.app.Application
import androidx.room.Room
import com.diegop.appoffline.data.database.AppDao
import com.diegop.appoffline.data.database.AppDatabase
import com.diegop.appoffline.data.network.ApiService
import com.diegop.appoffline.data.repository.AppRepository
import com.diegop.appoffline.utils.NetworkHandler
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .client(client)
                .build()
                .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application) =
            Room.databaseBuilder(application, AppDatabase::class.java, "AppDatabase").build()

    @Provides
    @Singleton
    fun provideAppDao(database: AppDatabase) = database.issuesDao()

    @Provides
    @Singleton
    fun provideNetworkHandler(application: Application) = NetworkHandler(application)

    @Provides
    @Singleton
    fun provideAppRepository(dao: AppDao, api: ApiService, networkHandler: NetworkHandler) = AppRepository(dao, api, networkHandler)
}
