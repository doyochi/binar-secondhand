package id.hikmah.binar.secondhand.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.hikmah.binar.secondhand.data.local.entity.Notifikasi
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