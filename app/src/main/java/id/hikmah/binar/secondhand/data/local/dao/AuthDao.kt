package id.hikmah.binar.secondhand.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.hikmah.binar.secondhand.data.local.entity.AuthUser
import kotlinx.coroutines.flow.Flow

@Dao
interface AuthDao {
    @Query("SELECT * FROM AuthUser")
    fun getUserDetail(): Flow<AuthUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserDetail(authUser: AuthUser)

    @Query("DELETE FROM AuthUser")
    fun deleteUserDetail()
}