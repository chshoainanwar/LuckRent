package com.example.luck_rent_android.ui.main.order.model

import java.io.Serializable

class OrderModel (
    var id: String? = null,
    val text : String? = null,
    val image : Int? = null,
    var isSelected: Boolean = false
        ): Serializable