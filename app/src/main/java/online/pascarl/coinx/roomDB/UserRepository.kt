package online.pascarl.coinx.roomDB

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository(private val userDao: UserDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    suspend fun addUser(user: RoomUser) {
        coroutineScope.launch {
            userDao.addUser(user)
        }
    }

    suspend fun getUser(id: String): RoomUser? {
        return userDao.getUser(id)
    }

    suspend fun updateUser(user: RoomUser) {
        coroutineScope.launch {
            userDao.updateUser(user)
        }
    }
    suspend fun deleteUser(id: String): Int{
        return userDao.delete(id)
    }
}
