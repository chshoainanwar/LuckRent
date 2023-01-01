package com.example.luck_rent_android.ui.main.contractormodule.model

import java.io.Serializable

class JobCompleteModel (
    var id: String? = null,
    var title : String ?=null,
    var status: String? = null,
    var isSelected: Boolean = false,
    var isFor : String ?= null
        ) : Serializable