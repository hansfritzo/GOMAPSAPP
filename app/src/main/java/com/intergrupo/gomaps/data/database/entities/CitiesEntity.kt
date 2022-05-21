package com.intergrupo.gomaps.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.intergrupo.gomaps.domain.model.Cities

/**
 * Created by hans fritz ortega on 20/05/22.
 */
@Entity(tableName = "cities")
data class CitiesEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "city_name") val cityName: String,
)

fun Cities.toDataBase() = CitiesEntity(
    cityName = cityName
)
