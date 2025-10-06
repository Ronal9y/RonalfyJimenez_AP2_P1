package edu.ucne.ronalfyjimenez_ap2_p1.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.ronalfyjimenez_ap2_p1.data.local.dao.HuacalDao
import edu.ucne.ronalfyjimenez_ap2_p1.data.local.entity.HuacalEntity

@Database(
    entities = [HuacalEntity::class],
    version = 3,
    exportSchema = false
)
abstract class HuacalesDb : RoomDatabase() {
    abstract fun huacalDao(): HuacalDao
}