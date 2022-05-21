package com.intergrupo.gomaps.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.intergrupo.gomaps.data.database.dao.*
import com.intergrupo.gomaps.data.database.entities.*

@Database(
    entities = [AuthTokenEntity::class, CountriesEntity::class, StatesEntity::class, CapitalEntity::class,CitiesEntity::class],
    version = 17
)
abstract class GoMapsDataBase : RoomDatabase() {

    abstract fun authTokenDao(): AuthTokenDao
    abstract fun countiresDao(): CountiresDao
    abstract fun statesDao(): StatesDao
    abstract fun capitalDao(): CapitalDao
    abstract fun queryCountrieDao(): QueryCountrieDao
    abstract fun citiesDao(): CitiesDao
}