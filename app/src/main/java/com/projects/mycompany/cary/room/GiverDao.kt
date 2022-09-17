package com.projects.mycompany.cary.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.projects.mycompany.cary.models.CareGiver

@Dao
interface GiverDao {

    @Query("SELECT * FROM caregiver WHERE uid = :id")
    fun getGiver(id: String): LiveData<CareGiver>

    @Query("SELECT * FROM caregiver WHERE uid = :id")
    suspend fun getSyncGiver(id: String): CareGiver

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(careGiver: CareGiver)

    @Update
    suspend fun update(careGiver: CareGiver)

    @Query("DELETE FROM caregiver WHERE uid= :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM caregiver")
    suspend fun getGivers(): List<CareGiver>

    @Query("DELETE FROM caregiver")
    suspend fun deleteAllCaregivers()
}