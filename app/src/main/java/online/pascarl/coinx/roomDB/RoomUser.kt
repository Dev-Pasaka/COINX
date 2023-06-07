package online.pascarl.coinx.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "user")
data class RoomUser(
    @PrimaryKey val id: String = "12345678",
    val email: String = "",
    val token: String = ""
)
