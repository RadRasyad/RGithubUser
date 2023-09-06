package com.danrsy.rgithubuser.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.danrsy.rgithubuser.R
import com.danrsy.rgithubuser.core.domain.model.User
import com.danrsy.rgithubuser.databinding.ActivityMainBinding
import com.danrsy.rgithubuser.core.ui.UsersAdapter
import com.danrsy.rgithubuser.ui.setting.SettingActivity
import com.danrsy.rgithubuser.ui.setting.SettingViewModel
import com.danrsy.rgithubuser.core.data.Resource
import com.danrsy.rgithubuser.ui.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private val settingViewModel: SettingViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        initTheme()
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        mainViewModel.getUsers().observe(this) { userList ->
            if (userList!=null) {
                when (userList) {
                    is Resource.Success -> {
                        if (userList.data?.isEmpty() == true) {
                            showEmptyState(true)
                        } else {
                            userList.data?.let { populateData(it) }
                            showEmptyState(false)
                        }
                        showLoadingState(false)
                        showErrorMsg(false,"")
                    }
                    is Resource.Loading -> {
                        showErrorMsg(false,"")
                        showLoadingState(true)
                        showEmptyState(false)
                    }
                    is Resource.Error -> {
                        showErrorMsg(true, userList.message)
                        showEmptyState(false)
                        showLoadingState(false)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                getSearchUsers(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.action_favorite -> {
                val uri = Uri.parse("rgithubuser://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
                true
            }

            R.id.action_setting -> {
                val mSetting = Intent(this, SettingActivity::class.java)
                startActivity(mSetting)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getSearchUsers(username: String) {
        mainViewModel.getSearchUsers(username).observe(this) { userList ->
            if (userList!=null) {
                when (userList) {
                    is Resource.Success -> {
                        if (userList.data?.isEmpty() == true) {
                            showEmptyState(true)
                        } else {
                            userList.data?.let { populateData(it) }
                            showEmptyState(false)
                        }
                        showLoadingState(false)
                        showErrorMsg(false,"")
                    }
                    is Resource.Loading -> {
                        showErrorMsg(false,"")
                        showLoadingState(true)
                        showEmptyState(false)
                    }
                    is Resource.Error -> {
                        showErrorMsg(true, userList.message)
                        showEmptyState(false)
                        showLoadingState(false)
                    }
                }
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
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
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

    private fun showErrorMsg(state: Boolean, msg: String?) {
        binding.apply {
            rvUser.visibility = if (state) View.GONE else View.VISIBLE
            errorState.root.visibility = if (state) View.VISIBLE else View.GONE
            emptyState.massage.text = msg
        }
    }

    private fun initTheme() {
        settingViewModel.getThemeSettings().observe(this) { theme: Int ->
            when (theme) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    settingViewModel.saveThemeSetting(0)
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    settingViewModel.saveThemeSetting(1)
                }
                else -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    settingViewModel.saveThemeSetting(2)
                }
            }
        }
    }

}