package com.micarroaldia.app.data

import androidx.compose.runtime.mutableStateListOf

data class Vehicle(
    val plate: String,
    val brand: String,
    val model: String,
    val pendingObligations: Int = 0
)

object VehicleRepository {
    val vehicles = mutableStateListOf(
        Vehicle(plate = "XYZ987", brand = "Renault", model = "Duster", pendingObligations = 1)
    )

    fun add(vehicle: Vehicle) {
        vehicles.add(0, vehicle)
    }
}
