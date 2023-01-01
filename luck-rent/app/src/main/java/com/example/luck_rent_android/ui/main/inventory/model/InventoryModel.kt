package com.example.luck_rent_android.ui.main.inventory.model

import java.io.Serializable

class InventoryModel (
var category: String? = null,
var brand: String? = null,
var model: String? = null,
var lastMaintenance: String? = null,
var warrantyUntil: String? = null,
var nextMaintenance: String? = null,
var notes: String? = null
        ): Serializable