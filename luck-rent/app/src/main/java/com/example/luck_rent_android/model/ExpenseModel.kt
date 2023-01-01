package com.example.luck_rent_android.model

import java.io.Serializable

data class ExpenseModel(
    var building: String? = null,
    var category: String? = null,
    var unit: String? = null,
    var amount: String? = null,
) : Serializable