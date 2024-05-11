package com.example.myapplicationflow.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationflow.database.ItemDatabase
import com.example.myapplicationflow.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainActivityVM(application: Application) : ViewModel() {
    private val database = ItemDatabase.getInstance(application).itemDatabaseDao

    private var _usersFlow = MutableSharedFlow<ArrayList<User>>(replay = 1)
    val usersFlow: Flow<ArrayList<User>> = _usersFlow.asSharedFlow()

    fun loadListFlow() {
        viewModelScope.launch(Dispatchers.Main) {
            // Оновлення значення _usersFlow за допомогою методу emit
            _usersFlow.emit(database.getAllUsers() as ArrayList<User>) // Використовуємо first() для отримання першого елемента потоку
        }
    }

    fun addItemFlow(user: User) {
        viewModelScope.launch(Dispatchers.Main) {
            _usersFlow.emit(_usersFlow.replayCache.firstOrNull()?.apply { add(user) }
                ?: arrayListOf(user))
            database.insert(user)
        }
    }

    private fun deleteUser(item: User) {
        viewModelScope.launch(Dispatchers.Main) {
            database.delete(item)
        }
    }
}