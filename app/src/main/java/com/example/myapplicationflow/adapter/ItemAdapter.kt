package com.example.myapplicationflow.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationflow.models.User
import com.example.myapplicationflow.R
import com.example.myapplicationflow.databinding.ItemUserBinding

class ItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = ArrayList<User>()

    class ItemUserHolder(
        private val binding: ItemUserBinding
    ) :
        RecyclerView.ViewHolder(binding.root), OnMessageClick {

        @SuppressLint("SetTextI18n")
        fun bind(user: User) = with(binding) {
            userName.text = user.name
            userStatus.text = user.status + " | " + user.phoneNumber + " | " + user.onlineStatus
            imageUser.setImageResource(R.drawable.ic_launcher_foreground)

            onClick()
            onLongClick()
        }

        override fun onClick() {
            binding.root.setOnClickListener {

            }
        }

        override fun onLongClick() {
            binding.root.setOnLongClickListener {
                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemUserHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemUserHolder).bind(list[position])
    }

    override fun getItemCount() = list.size

    fun setList(listUser: List<User>) {
        list = listUser as ArrayList<User>
    }
}