package com.example.myapplicationflow.sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplicationflow.MainActivity
import com.example.myapplicationflow.databinding.PopUpUserBinding
import com.example.myapplicationflow.models.User
import com.example.myapplicationflow.viewModel.MainActivityVM
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetUser : BottomSheetDialogFragment() {
    private var binding: PopUpUserBinding? = null
    private lateinit var mainActivityViewModel: MainActivityVM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivityViewModel = (requireActivity() as MainActivity).viewModel
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PopUpUserBinding.inflate(layoutInflater)

        binding!!.addBtn.setOnClickListener {
            with(binding!!) {
                val name = userNameModel.text.toString()
                val status = userStatusModel.text.toString()
                val phone = userPhoneModel.text.toString()
                val age = userAgeModel.text.toString().toIntOrNull() ?: 0

                val user = User(name, status, phone, true, age)
                mainActivityViewModel.addUserFlow(user)
                dismiss()
            }
        }


        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}