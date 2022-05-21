package com.intergrupo.gomaps.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.intergrupo.gomaps.domain.model.Capital

/**
 * Created by hans fritz ortega on 20/05/22.
 */
@Entity(tableName = "countries_capital")
data class CapitalEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "capital_name") val capitalName: String,
    @ColumnInfo(name = "region_name") val regioName: String,
    @ColumnInfo(name = "short_name") val shortName: String,
)

fun Capital.toDataBase() = CapitalEntity(
    capitalName = capitalName,
    regioName = regioName,
    shortName = shortName
)
