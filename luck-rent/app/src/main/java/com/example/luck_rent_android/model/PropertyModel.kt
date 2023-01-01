package com.example.luck_rent_android.model

import java.io.Serializable

data class PropertyModel
    (
    var propertyAddress: String? = null,
    var propertyUnit: String? = null,
    var propertyImage: Int? = null,
    var propertyNumber: String? = null,
) : Serializable