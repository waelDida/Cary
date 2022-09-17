package com.projects.mycompany.cary.room


import androidx.lifecycle.LiveData
import androidx.room.*
import com.projects.mycompany.cary.models.CareSeeker


@Dao
interface SeekerDao {

    @Query("SELECT * FROM CareSeeker WHERE uid = :id")
    fun getSeeker(id: String): LiveData<CareSeeker>

    @Query("SELECT * FROM CareSeeker WHERE uid = :id")
    suspend fun getSyncSeeker(id: String): CareSeeker

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(careSeeker: CareSeeker)

    @Update
    suspend fun update(careSeeker: CareSeeker)

    @Query("DELETE FROM CareSeeker WHERE uid = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM CareSeeker")
    suspend fun getSeekers(): List<CareSeeker>

    @Query("DELETE FROM CareSeeker")
    suspend fun deleteAllCareSeekers()
}