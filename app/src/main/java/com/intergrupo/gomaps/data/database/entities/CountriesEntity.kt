package com.intergrupo.gomaps.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.intergrupo.gomaps.domain.model.Countries

/**
 * Created by hans fritz ortega on 20/05/22.
 */
@Entity(tableName = "countries")
data class CountriesEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int=0,
    @ColumnInfo(name = "country_name") val countryName: String,
    @ColumnInfo(name = "country_short_name") val countryShortName: String,
    @ColumnInfo(name = "country_phone_code") val countryPhoneCode: Int,
)

fun Countries.toDataBase() = CountriesEntity(
    countryName = countryName,
    countryShortName=countryShortName,
    countryPhoneCode=countryPhoneCode
)
