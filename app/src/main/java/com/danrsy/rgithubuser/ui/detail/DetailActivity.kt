package com.danrsy.rgithubuser.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import coil.load
import coil.transform.CircleCropTransformation
import com.danrsy.rgithubuser.R
import com.danrsy.rgithubuser.core.domain.model.User
import com.danrsy.rgithubuser.ui.common.SectionPagerAdapter
import com.danrsy.rgithubuser.databinding.ActivityDetailBinding
import com.danrsy.rgithubuser.ui.followers.FollowersViewModel
import com.danrsy.rgithubuser.core.data.Resource
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModel()
    private val followersViewModel: FollowersViewModel by viewModel()
    private lateinit var binding: ActivityDetailBinding
    private lateinit var user: User
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra(EXTRA_DATA)

        if (username != null) {

            initPageAdapter(username)

            detailViewModel.detailUsers(username).observe(this@DetailActivity) { value ->
                when (value) {
                    is Resource.Success -> {
                        binding.fabFavorite.show()
                        showLoadingState(false)
                        showErrorMsg(false, "")
                        value.data?.let { populateData(it) }
                        user = value.data!!
                        detailViewModel.getDetailState(username)?.observe(this) {
                            isFavorite = it.isFavorite == true
                            favoriteState(isFavorite)
                        }
                    }

                    is Resource.Loading -> {
                        binding.fabFavorite.hide()
                        showLoadingState(true)
                        showErrorMsg(false, "")
                    }

                    is Resource.Error -> {
                        binding.fabFavorite.hide()
                        showLoadingState(false)
                        showErrorMsg(true, value.message.toString())
                    }
                }
                favoriteState(isFavorite)
                binding.fabFavorite.setOnClickListener {
                    insertOrDeleteFav()
                }
            }
        }


    }

    private fun initPageAdapter(username: String) {
        val sectionsPagerAdapter = SectionPagerAdapter(this, username)
        val viewPager: ViewPager2 = binding.viewpager2
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.detailtab)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun populateData(user: User) {
        val emptyData = "-"
        binding.apply {
            tvFname.text = user.name ?: emptyData
            tvUsername.text = user.login ?: emptyData
            tvCompany.text = user.company ?: emptyData
            tvLocation.text = user.location ?: emptyData
            tvRepository.text = (user.publicRepos ?: emptyData).toString()
            tvFollower.text = (user.followers ?: emptyData).toString()
            tvFollowing.text = (user.following ?: emptyData).toString()

            ivUser.load(user.avatarUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
                placeholder(R.drawable.placeholder_img)
                error(R.drawable.placeholder_img)
            }
        }
    }

    private fun showLoadingState(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showErrorMsg(state: Boolean, msg: String) {
        if (state) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun favoriteState(isFavorite: Boolean) {
        binding.fabFavorite.apply {
            if (isFavorite) load(R.drawable.ic_favorite_fill)
            else load(R.drawable.ic_favorite_outline)
        }
    }

    private fun insertOrDeleteFav() {
        if (!isFavorite) {
            user.isFavorite = !isFavorite
            detailViewModel.insertFavorite(user)
            Toast.makeText(
                this, R.string.favorite_add, Toast.LENGTH_SHORT
            ).show()
            isFavorite = !isFavorite
        } else {
            user.isFavorite = !isFavorite
            detailViewModel.deleteFavorite(user)
            Toast.makeText(
                this, R.string.favorite_remove, Toast.LENGTH_SHORT
            ).show()
            isFavorite = !isFavorite
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

    companion object {
        const val EXTRA_DATA = "extra_data"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_follower,
            R.string.tab_following,
        )
    }
}