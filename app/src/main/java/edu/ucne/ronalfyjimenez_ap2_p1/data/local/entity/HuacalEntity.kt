package edu.ucne.ronalfyjimenez_ap2_p1.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Huacales")
data class HuacalEntity(
    @PrimaryKey(autoGenerate = true)
    val idEntrada: Int = 0,
    val fecha: String,
    val nombreCliente: String,
    val cantidad: Int,
    val precio: Double
)
