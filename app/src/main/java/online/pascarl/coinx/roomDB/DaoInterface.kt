package online.pascarl.coinx.roomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: RoomUser)

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUser(id: String): RoomUser?

    @Update
    suspend fun updateUser(user: RoomUser)
}