package com.intergrupo.gomaps.di

import android.content.Context
import androidx.room.Room
import com.intergrupo.gomaps.data.database.GoMapsDataBase
import com.intergrupo.gomaps.util.FlagConstants.VRT_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by hans fritz ortega on 20/05/22.
 */
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, GoMapsDataBase::class.java, VRT_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun provideAuthTokenDao(db: GoMapsDataBase) = db.authTokenDao()

    @Singleton
    @Provides
    fun provideCountiresDao(db: GoMapsDataBase) = db.countiresDao()

    @Singleton
    @Provides
    fun provideStatesDao(db: GoMapsDataBase) = db.statesDao()

    @Singleton
    @Provides
    fun provideCapitalDao(db: GoMapsDataBase) = db.capitalDao()

    @Singleton
    @Provides
    fun provideQueryCountrieDao(db: GoMapsDataBase) = db.queryCountrieDao()

    @Singleton
    @Provides
    fun provideCitiesDao(db: GoMapsDataBase) = db.citiesDao()

}