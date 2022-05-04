package com.worldimage.belldemo.di

import android.app.Application
import android.content.Context
import com.worldimage.belldemo.data.db.AppDao
import com.worldimage.belldemo.data.db.AppDatabase
import com.worldimage.belldemo.data.remote.repository.CarRepositoryImpl
import com.worldimage.belldemo.domain.repository.CarRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun getAppDatabase(context: Application): AppDatabase {
        return AppDatabase.getAppDBInstance(context)
    }

    @Provides
    @Singleton
    fun getAppDao(appDatabase: AppDatabase): AppDao {
        return appDatabase.getAppDao()
    }

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideCarRepository(appDao: AppDao, context: Context): CarRepository {
        return CarRepositoryImpl(appDao, context)
    }
}