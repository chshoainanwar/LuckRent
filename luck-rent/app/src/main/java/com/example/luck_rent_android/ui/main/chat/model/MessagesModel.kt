package com.example.luck_rent_android.ui.main.chat.model

import java.io.Serializable

class MessagesModel(
    var name: String? = null,
    var description: String? = null,
    var time: String? = null,
    var number: String? = null,
    var image: Int? = null
) : Serializable