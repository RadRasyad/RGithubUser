package com.danrsy.rgithubuser.ui.favorite


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.danrsy.rgithubuser.R
import com.danrsy.rgithubuser.data.model.User
import com.danrsy.rgithubuser.databinding.ActivityFavoriteBinding
import com.danrsy.rgithubuser.ui.common.UsersAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.favorite)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getData()

    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        CoroutineScope(Dispatchers.Main).launch {
            showLoadingState(true)
            viewModel.getAllFavorite()?.observe(this@FavoriteActivity) {
                showLoadingState(false)
                populateData(it)
                if (it.isNotEmpty()) showEmptyState(false) else showEmptyState(true)
            }
        }
    }

    private fun populateData(data : List<User>) {
        adapter = UsersAdapter(data)
        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }
    }

    private fun showLoadingState(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showEmptyState(state: Boolean) {
        binding.apply {
            emptyState.root.visibility = if (state) View.VISIBLE else View.GONE
            rvUser.visibility = if (state) View.GONE else View.VISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


}