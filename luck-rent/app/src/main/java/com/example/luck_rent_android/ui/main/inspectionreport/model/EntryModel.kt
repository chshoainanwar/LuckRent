package com.example.luck_rent_android.ui.main.inspectionreport.model

import com.example.luck_rent_android.ui.main.property.model.DocumentModel
import java.io.File
import java.io.Serializable

class EntryModel(
    val title: String? = null,
    var expandable: Boolean = false,
    var isSelected: Boolean = false,
    var documentModel: ArrayList<File> ?= ArrayList(),
    var endPhotoslist: ArrayList<File> ?= ArrayList()
) : Serializable