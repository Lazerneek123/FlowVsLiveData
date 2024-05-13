package com.example.myapplicationflow.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivityVMFactory(private val application: Application, private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityVM(application, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
