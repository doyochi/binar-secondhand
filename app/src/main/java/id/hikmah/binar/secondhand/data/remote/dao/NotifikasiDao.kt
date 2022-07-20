package com.and2t2.secondhand.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.hikmah.binar.secondhand.data.remote.model.domain.Notifikasi
import kotlinx.coroutines.flow.Flow

@Dao
interface NotifikasiDao {

    @Query("SELECT * FROM Notifikasi")
    fun getNotifikasi(): Flow<List<Notifikasi>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotifikasi(notifikasi: List<Notifikasi>)

    @Query("DELETE FROM Notifikasi")
    fun deleteNotifikasi()

}