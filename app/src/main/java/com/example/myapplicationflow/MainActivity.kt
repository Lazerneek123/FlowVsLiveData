package com.example.myapplicationflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationflow.adapter.ItemAdapter
import com.example.myapplicationflow.viewModel.MainActivityVM
import com.example.myapplicationflow.viewModel.MainActivityVMFactory
import com.example.myapplicationflow.databinding.ActivityMainBinding
import com.example.myapplicationflow.sheet.BottomSheetUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainActivityVM
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = MainActivityVMFactory(application, this)
        viewModel = ViewModelProvider(this, factory)[MainActivityVM::class.java]

        viewModel.setLetterOrWord(true) // встановлення пошуку по буквам

        recyclerView = binding.userListRecyclerView
        itemAdapter = ItemAdapter()

        realizationFlow()
        initRcView()
    }

    private fun realizationFlow() {
        onChangeListenerFlow()
        addFlow()
        searchFlow()
    }

    private fun addFlow() {
        binding.addButton.setOnClickListener {
            val bottomSheetInformation = BottomSheetUser()
            bottomSheetInformation.show(supportFragmentManager, "TAG")
        }
        viewModel.viewModelScope.launch(Dispatchers.Main) {
            viewModel.usersFlow.collect {
                binding.countItem.text = "Flow List: " + it.size
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onChangeListenerFlow() {
        viewModel.viewModelScope.launch(Dispatchers.Main) {
            viewModel.loadListFlow()
            viewModel.usersFlow
                .collect { filteredList ->
                    itemAdapter.submitList(filteredList)
                    binding.countItem.text = "List: ${filteredList.size}"
                }
        }
    }

    private fun searchFlow() {
        val searchEdit = binding.userNameTextInputEdit
        searchEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = cs.toString().trim()
                viewModel.viewModelScope.launch {
                    viewModel.letterOrWord.collect { value ->
                        if (value) {
                            viewModel.searchUsersByNameLetter(searchText)
                        } else {
                            viewModel.searchUsersByNameWord(searchText)
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        val btnLetterOrWord = binding.letterOrWordBtn
        viewModel.viewModelScope.launch {
            viewModel.letterOrWord.collect { value ->
                btnLetterOrWord.text = if (value) "Letter" else "Word"
            }
        }

        btnLetterOrWord.setOnClickListener{
            if(btnLetterOrWord.text == "Letter"){
                viewModel.setLetterOrWord(false)
            }
            if(btnLetterOrWord.text == "Word"){
                viewModel.setLetterOrWord(true)
            }
        }
    }

    private fun initRcView() = with(binding) {
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        recyclerView.adapter = itemAdapter
    }
}