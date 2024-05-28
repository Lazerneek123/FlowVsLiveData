package com.example.myapplicationflow.sheet

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import com.example.myapplicationflow.MainActivity
import com.example.myapplicationflow.database.ItemDatabase
import com.example.myapplicationflow.databinding.PopUpEditUserBinding
import com.example.myapplicationflow.models.User
import com.example.myapplicationflow.viewModel.MainActivityVM
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BottomSheetEditUser : BottomSheetDialogFragment() {
    private var binding: PopUpEditUserBinding? = null

    //private lateinit var mainActivityViewModel: MainActivityVM
    private val mainActivityViewModel: MainActivityVM by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //mainActivityViewModel = (context as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PopUpEditUserBinding.inflate(layoutInflater)

        with(binding!!) {
            mainActivityViewModel.viewModelScope.launch(Dispatchers.Main) {
                mainActivityViewModel.user.collect { user ->
                    userNameModel.setText(user.name)
                    userStatusModel.setText(user.status)
                    userPhoneModel.setText(user.phoneNumber)
                    userAgeModel.setText(user.age.toString())

                    saveBtn.setOnClickListener {
                        user.name = userNameModel.text.toString()
                        user.status = userStatusModel.text.toString()
                        user.phoneNumber = userPhoneModel.text.toString()
                        user.age = userAgeModel.text.toString().toIntOrNull() ?: 0
                        mainActivityViewModel.updateUser(user)
                        dismiss()
                    }
                }
            }
        }


        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}