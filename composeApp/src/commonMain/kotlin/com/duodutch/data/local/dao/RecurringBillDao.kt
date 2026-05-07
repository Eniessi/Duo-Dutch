package com.duodutch.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.duodutch.data.local.entities.RecurringBillEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecurringBillDao {

    // Retorna um fluxo contínuo de contas recorrentes de um casal específico
    @Query("SELECT * FROM recurring_bills WHERE householdId = :householdId ORDER BY dueDayOfMonth ASC")
    fun getRecurringBillsByHousehold(householdId: String): Flow<List<RecurringBillEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecurringBill(bill: RecurringBillEntity)

    @Query("DELETE FROM recurring_bills WHERE id = :id")
    suspend fun deleteRecurringBillById(id: String)
}