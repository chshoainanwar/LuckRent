package com.example.luck_rent_android.ui.main.contractormodule.model

import java.io.Serializable

class ContractorInvoiceModel(
    var id: String? = null,
    var name: String? = null,
    var date: String? = null,
    var status: String? = null,
    var amount: String? = null,
    var isSelected: Boolean = false
) : Serializable