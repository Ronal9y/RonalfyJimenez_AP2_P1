package edu.ucne.ronalfyjimenez_ap2_p1.data.local.dao

import androidx.room.*
import edu.ucne.ronalfyjimenez_ap2_p1.data.local.entity.HuacalEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface HuacalDao {

    @Upsert
    suspend fun save(huacal: HuacalEntity)

    @Query("SELECT * FROM Huacales ORDER BY idEntrada DESC")
    fun observeAll(): Flow<List<HuacalEntity>>

    @Query("SELECT * FROM Huacales WHERE idEntrada = :id LIMIT 1")
    suspend fun getById(id: Int): HuacalEntity?

    @Delete
    suspend fun delete(huacal: HuacalEntity)

    @Query("DELETE FROM Huacales WHERE idEntrada = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM Huacales WHERE (:cliente IS NULL OR nombreCliente LIKE '%' || :cliente || '%') AND (:fecha IS NULL OR fecha = :fecha) AND (:minCant IS NULL OR cantidad >= :minCant) AND (:maxCant IS NULL OR cantidad <= :maxCant) ORDER BY idEntrada DESC")
    fun observeFiltered(cliente: String?, fecha: String?, minCant: Int?, maxCant: Int?): Flow<List<HuacalEntity>>
}