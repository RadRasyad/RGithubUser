package com.danrsy.rgithubuser.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.danrsy.rgithubuser.R
import com.danrsy.rgithubuser.data.model.User
import com.danrsy.rgithubuser.databinding.ActivityMainBinding
import com.danrsy.rgithubuser.ui.common.UsersAdapter
import com.danrsy.rgithubuser.ui.favorite.FavoriteActivity
import com.danrsy.rgithubuser.ui.setting.SettingActivity
import com.danrsy.rgithubuser.ui.setting.SettingViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private val settingViewModel: SettingViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        initTheme()
        mainViewModel = ViewModelProvider(this@MainActivity, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        mainViewModel.userList.observe(this) { userList ->
            if (userList!=null) {
                if (userList.size>0) {
                    populateData(userList)
                    showEmptyState(false)
                } else {
                    showEmptyState(true)
                }
            }
        }

        mainViewModel.isLoading.observe(this) {
            showLoadingState(it)
        }

        mainViewModel.isError.observe(this) { state ->
            mainViewModel.errorMgs.observe(this) { msg ->
                showErrorMsg(state, msg)
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
                mainViewModel.getSearchUsers(query)
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
                val mFavorite = Intent(this, FavoriteActivity::class.java)
                startActivity(mFavorite)
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

    private fun populateData(data : List<User>) {
        adapter = UsersAdapter(data)
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