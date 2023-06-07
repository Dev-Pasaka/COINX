package online.pascarl.coinx.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(RoomUser::class)], version = 1, exportSchema = false)
abstract class UserDatabase:RoomDatabase() {
    abstract fun userDao():UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null
        fun getInstance(context: Context): UserDatabase {
            val temInstance = INSTANCE
            if (temInstance != null) {
                return temInstance
            }
              synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "User"
                    ).build()
                    INSTANCE = instance
                    return instance
                }
        }
    }
}
