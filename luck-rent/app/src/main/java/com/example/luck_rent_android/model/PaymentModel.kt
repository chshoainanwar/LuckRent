package com.example.luck_rent_android.model

import java.io.Serializable

data class PaymentModel(
    var incomeMethod: String? = null,
    var payor: String? = null,
    var amount: String? = null,
    var dateReceived: String? = null,
    var dateFrom: String? = null,
    var taxStatus: String? = null,
    var notes: String? = null,
) : Serializable