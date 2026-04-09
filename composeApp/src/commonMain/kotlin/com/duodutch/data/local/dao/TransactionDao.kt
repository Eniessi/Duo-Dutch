package com.duodutch.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.duodutch.data.local.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    // O Flow garante reatividade.
    // O filtro 'householdId' garante que um casal nunca vê as transações de outro.
    @Query("SELECT * FROM transactions WHERE householdId = :householdId ORDER BY date DESC")
    fun getTransactionsByHousehold(householdId: String): Flow<List<TransactionEntity>>

    // O REPLACE garante que, se tentarmos inserir uma transação com um ID que já existe (ex: edição),
    // o SQL substitui a linha antiga em vez de causar um erro de duplicação de chave.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransactionById(id: String)

    // Função utilitária para limparmos a base de dados de um casal (útil para cenários de logout)
    @Query("DELETE FROM transactions WHERE householdId = :householdId")
    suspend fun deleteAllFromHousehold(householdId: String)
}