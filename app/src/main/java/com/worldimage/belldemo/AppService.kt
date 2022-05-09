package com.worldimage.belldemo

import android.app.Application
import android.content.Context
import com.worldimage.belldemo.db.AppDatabase
import com.worldimage.belldemo.db.CarDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppService {

    @Provides
    @Singleton
    fun getAppDatabase(context: Application): AppDatabase {
        return AppDatabase.getAppDBInstance(context)
    }

    @Provides
    @Singleton
    fun getAppDao(appDatabase: AppDatabase): CarDao {
        return appDatabase.getAppDao()
    }

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

}