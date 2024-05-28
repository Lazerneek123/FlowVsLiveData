package com.example.myapplicationflow.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationflow.MainActivity
import com.example.myapplicationflow.sheet.BottomSheetEditUser
import com.example.myapplicationflow.databinding.ItemUserBinding
import com.example.myapplicationflow.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemAdapter(private val fragmentManager: FragmentManager) :
    ListAdapter<User, ItemAdapter.ItemHolder>(ItemHolder.ItemComparator()) {

    class ItemHolder(
        private val binding: ItemUserBinding,
        private val fragmentManager: FragmentManager
    ) :
        RecyclerView.ViewHolder(binding.root), OnAdapterClick {

        @SuppressLint("SetTextI18n")
        fun bind(user: User) = with(binding) {
            userName.text = user.name
            userStatus.text =
                user.status + " | " + user.phoneNumber + " | " + user.age + " | " + user.onlineStatus

            floatingActionButtonDelete.setOnClickListener {
                val builder = android.app.AlertDialog.Builder(binding.root.context)
                builder.setTitle("Are you sure you want to delete this user?")
                    .setNegativeButton("Yes") { dialog, _ ->
                        (binding.root.context as MainActivity).viewModel.deleteUser(user)
                        dialog.dismiss()
                    }
                    .setPositiveButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }.show()
            }

            floatingActionButtonEdit.setOnClickListener {
                val viewModel = (binding.root.context as MainActivity).viewModel
                viewModel.viewModelScope.launch(Dispatchers.Main) {
                    viewModel._user.emit(user)
                }

                val bottomSheetInformation = BottomSheetEditUser()
                bottomSheetInformation.show(fragmentManager, "TAG")
            }

            onClick()
            onLongClick()
        }

        override fun onClick() {
            // code
        }

        override fun onLongClick() {
            binding.root.setOnLongClickListener {
                // code
                return@setOnLongClickListener true
            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                fragmentManager: FragmentManager
            ): ItemHolder {
                return ItemHolder(
                    ItemUserBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    fragmentManager
                )
            }
        }

        class ItemComparator : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent, fragmentManager)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position))
    }
}