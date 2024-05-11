package com.example.myapplicationflow.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivityVMFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityVM(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
