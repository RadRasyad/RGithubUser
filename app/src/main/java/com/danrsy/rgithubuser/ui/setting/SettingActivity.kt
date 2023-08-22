package com.danrsy.rgithubuser.ui.setting

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import com.danrsy.rgithubuser.R
import com.danrsy.rgithubuser.databinding.ActivitySettingBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingActivity : AppCompatActivity() {

    private var checkedItem: Int = 2
    private lateinit var binding: ActivitySettingBinding
    private val settingViewModel: SettingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = "Setting"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initTheme()
        binding.themeContainer.setOnClickListener {
            themeDialog()
        }

        @SuppressLint("SwitchIntDef")
        when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> {
                binding.themeName.text = resources.getText(R.string.theme_mode_default)
            }
            AppCompatDelegate.MODE_NIGHT_NO -> {
                binding.themeName.text = resources.getText(R.string.theme_mode_light)
            }
            AppCompatDelegate.MODE_NIGHT_YES -> {
                binding.themeName.text = resources.getText(R.string.theme_mode_dark)
            }
        }
    }

    private fun initTheme() {
        settingViewModel.getThemeSettings().observe(this) { theme: Int ->
            checkedItem = when (theme) {
                0 -> {
                    0
                }
                1 -> {
                    1
                }
                else -> {
                    2
                }
            }
        }
    }

    private fun themeDialog() {

        val singleItems = arrayOf("Light", "Dark", "Default")

        settingViewModel.getThemeSettings().observe(this) { theme: Int ->
            checkedItem = when (theme) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    0
                }

                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    1
                }

                else -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    2
                }
            }
        }

        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.theme_title))
            .setSingleChoiceItems(singleItems, checkedItem) { _, which ->
                when(which) {
                    0 ->{
                        checkedItem = 0
                    }
                    1 ->{
                        checkedItem = 1
                    }
                    2 ->{
                        checkedItem = 2
                    }
                }
            }
            .setNegativeButton("Cancel") { dialog,_ ->
                dialog.cancel()
            }
            .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
                when(checkedItem) {
                    0 ->{
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        settingViewModel.saveThemeSetting(0)
                    }
                    1 ->{
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        settingViewModel.saveThemeSetting(1)
                    }
                    2 ->{
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                        settingViewModel.saveThemeSetting(2)
                    }
                }
            }
            .show()
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