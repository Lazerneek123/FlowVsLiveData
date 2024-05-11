package com.example.myapplicationflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationflow.adapter.ItemAdapter
import com.example.myapplicationflow.models.User
import com.example.myapplicationflow.viewModel.MainActivityVM
import com.example.myapplicationflow.viewModel.MainActivityVMFactory
import com.example.myapplicationflow.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityVM
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = MainActivityVMFactory(application)
        viewModel = ViewModelProvider(this, factory)[MainActivityVM::class.java]
        recyclerView = binding.userListRecyclerView
        itemAdapter = ItemAdapter()

        realizationFlow()
        initRcView()
    }

    private fun realizationFlow() {
        onChangeListenerFlow()
        addFlow()
    }

    private fun addFlow() {
        binding.addBtn.setOnClickListener {
            viewModel.addItemFlow(User("Roman", "11000"))
        }
        viewModel.viewModelScope.launch(Dispatchers.Main) {
            viewModel.usersFlow.collect {
                binding.countItem.text = "List: " + it.size
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onChangeListenerFlow() {
        viewModel.viewModelScope.launch(Dispatchers.Main) {
            viewModel.loadListFlow()
            viewModel.usersFlow
                .collect { filteredList ->
                    itemAdapter.setList(filteredList)
                    binding.countItem.text = "List: ${filteredList.size}"
                }
        }
    }

    private fun initRcView() = with(binding) {
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        recyclerView.adapter = itemAdapter
    }
}