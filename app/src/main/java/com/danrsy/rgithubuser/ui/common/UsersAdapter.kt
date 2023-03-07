package com.danrsy.rgithubuser.ui.common

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.danrsy.rgithubuser.R
import com.danrsy.rgithubuser.data.model.User
import com.danrsy.rgithubuser.databinding.UserItemRowBinding
import com.danrsy.rgithubuser.ui.detail.DetailActivity

class UsersAdapter(private val listUser: List<User>) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

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

            itemView.setOnClickListener{
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, user.login)
                itemView.context.startActivity(intent)
            }
        }
    }
}