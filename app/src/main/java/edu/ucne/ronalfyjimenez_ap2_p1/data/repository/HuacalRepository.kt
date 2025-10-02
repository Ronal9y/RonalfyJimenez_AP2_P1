package edu.ucne.ronalfyjimenez_ap2_p1.data.repository

import edu.ucne.ronalfyjimenez_ap2_p1.data.local.dao.HuacalDao
import edu.ucne.ronalfyjimenez_ap2_p1.data.local.entity.HuacalEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class HuacalRepository @Inject constructor(private val dao: HuacalDao) {

    fun observeAll(): Flow<List<HuacalEntity>> = dao.observeAll()

    suspend fun getById(id: Int): HuacalEntity? = dao.getById(id)

    suspend fun save(h: HuacalEntity) = dao.save(h)

    suspend fun delete(h: HuacalEntity) = dao.delete(h)

    suspend fun deleteById(id: Int) = dao.deleteById(id)

    fun observeFiltered(cliente: String? = null, fecha: String? = null, minCant: Int? = null, maxCant: Int? = null): Flow<List<HuacalEntity>> =
        dao.observeFiltered(cliente, fecha, minCant, maxCant)
}