package online.pascarl.coinx.roomDB

import android.app.Application
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RoomViewModel(
    private val application: Application,
    private var userRepository: UserRepository
) {
    init {
        val userDao = UserDatabase.getInstance(application).userDao()
        userRepository = UserRepository(userDao)
    }

    fun addUser(user: RoomUser):Boolean {
       return try {
           runBlocking {
               userRepository.addUser(user)
           }
           true // user added
       }catch (e: Exception) {
           e.printStackTrace()
           false
           // user not added
       }
    }

    fun getUser(id: String): RoomUser? {
        return runBlocking {
            userRepository.getUser(id)
        }
    }

    fun updateUser(user: RoomUser): Boolean {
        return try {
            runBlocking {
                userRepository.updateUser(user)
            }
            true // user updated
        }catch (e: Exception) {
            e.printStackTrace()
            false // user not updated
        }
    }

    fun deleteUser(id: String): Int {
        return try {
            runBlocking {
                userRepository.deleteUser(id)
            }
            1
        }catch (_:Exception){
            0
        }
    }
}