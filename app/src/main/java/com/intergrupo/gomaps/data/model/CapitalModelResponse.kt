package com.intergrupo.gomaps.data.model

import com.google.gson.annotations.SerializedName
import com.intergrupo.gomaps.domain.model.GoMapsApiFailed

/**
 * Created by hans fritz ortega on 20/05/22.
 */
data class CapitalModelResponse(

    @SerializedName("countriescapital")
    var capitals: List<CapitalModel>,
    var goMapsApiFailed: GoMapsApiFailed

    )