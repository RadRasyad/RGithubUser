package com.danrsy.favorite.ui


import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.danrsy.favorite.di.favoriteModule
import com.danrsy.rgithubuser.R
import com.danrsy.rgithubuser.core.domain.model.User
import com.danrsy.rgithubuser.core.ui.UsersAdapter
import com.danrsy.rgithubuser.databinding.ActivityFavoriteBinding
import com.danrsy.rgithubuser.ui.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private val viewModel: FavoriteViewModel by viewModel()
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoriteModule)

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
        viewModel.favoriteData.observe(this) { data ->
            showLoadingState(true)
            if (data.isNotEmpty()) {
                populateData(data)
                showLoadingState(false)
            } else {
                showEmptyState(true)
                showLoadingState(false)
            }
        }

    }

    private fun populateData(data : List<User>) {
        adapter = UsersAdapter(data)
        adapter.onItemClick = { selectedData ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, selectedData.login)
            startActivity(intent)
        }
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