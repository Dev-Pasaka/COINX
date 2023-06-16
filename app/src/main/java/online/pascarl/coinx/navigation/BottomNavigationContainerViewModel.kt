package online.pascarl.coinx.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BottomNavigationContainerViewModel: ViewModel() {

    private var _isLogOutDialogVisible by mutableStateOf(false)
    val isLogOutDialogVisible get() =  _isLogOutDialogVisible

    fun showOrHideLogOutDialog(){
        _isLogOutDialogVisible = !_isLogOutDialogVisible
    }

}