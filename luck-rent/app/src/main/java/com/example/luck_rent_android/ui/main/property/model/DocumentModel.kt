package com.example.luck_rent_android.ui.main.property.model

import java.io.File
import java.io.Serializable

class DocumentModel(
        var name: String? = null,
        var category: String? = null,
        var file: ArrayList<File>? = null
        ):Serializable