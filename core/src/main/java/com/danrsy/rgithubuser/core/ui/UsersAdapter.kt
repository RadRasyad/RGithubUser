package com.danrsy.rgithubuser.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.danrsy.rgithubuser.core.R
import com.danrsy.rgithubuser.core.databinding.UserItemRowBinding
import com.danrsy.rgithubuser.core.domain.model.User

class UsersAdapter(private val listUser: List<User>) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    var onItemClick: ((User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val item = UserItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(item)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }
    override fun getItemCount(): Int = listUser.size

    inner class UserViewHolder(private val binding: UserItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.apply {
                ivUser.load(user.avatarUrl) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.placeholder_img)
                    error(R.drawable.placeholder_img)
                }
                tvUsername.text = user.login
            }

        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(listUser[adapterPosition])
            }
        }
    }
}