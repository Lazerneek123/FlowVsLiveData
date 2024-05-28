package com.example.myapplicationflow.viewModel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationflow.database.ItemDatabase
import com.example.myapplicationflow.models.Role
import com.example.myapplicationflow.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainActivityVM(application: Application, private val context: Context) : ViewModel() {
    private val database = ItemDatabase.getInstance(application).itemDatabaseDao

    private var _usersFlow = MutableSharedFlow<ArrayList<User>>(replay = 1)
    val usersFlow: Flow<ArrayList<User>> = _usersFlow.asSharedFlow()

    private var _rolesFlow = MutableSharedFlow<ArrayList<Role>>(replay = 1)
    val rolesFlow: Flow<ArrayList<Role>> = _rolesFlow.asSharedFlow()

    private var _letterOrWord = MutableSharedFlow<Boolean>(replay = 1)
    val letterOrWord: Flow<Boolean> = _letterOrWord.asSharedFlow()

    var _user = MutableSharedFlow<User>(replay = 1)
    val user: Flow<User> = _user.asSharedFlow()

    fun setLetterOrWord(boolean: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            _letterOrWord.emit(boolean)
        }
    }

    fun loadListFlow() {
        updateListFlow()
    }

    /*fun addItemFlow(user: User) {
        viewModelScope.launch(Dispatchers.Main) {
            _usersFlow.emit(_usersFlow.replayCache.firstOrNull()?.apply { add(user) }
                ?: arrayListOf(user))
            database.insert(user)
        }
    }*/

    fun deleteUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                database.delete(user)
                updateListFlow()
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun addUserFlow(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                database.insert(user)
                updateListFlow()
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun searchUsersByNameLetter(searchText: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _usersFlow.emit(database.searchUsersByNameLetter(searchText) as ArrayList<User>)
        }
    }

    fun searchUsersByNameWord(searchText: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val newResult = database.searchUsersByNameWord(searchText)
            if (newResult.isNotEmpty()) {
                _usersFlow.emit(newResult as ArrayList<User>)
            } else {
                _usersFlow.emit(database.getAllUsers() as ArrayList<User>)
            }
        }
    }

    private fun updateListFlow() {
        viewModelScope.launch(Dispatchers.Main) {
            _usersFlow.emit(database.getAllUsers() as ArrayList<User>)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                database.updateUser(user)
                updateListFlow()
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun loadRoles() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                if (database.getAllRoles().isEmpty()) {
                    database.insertRole(Role("Admin", 1, 5, "Admin"))
                    database.insertRole(Role("User", 2, 1, "User"))
                }
                _rolesFlow.emit(database.getAllRoles() as ArrayList<Role>)
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}