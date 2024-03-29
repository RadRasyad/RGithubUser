package com.danrsy.rgithubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import coil.load
import coil.transform.CircleCropTransformation
import com.danrsy.rgithubuser.R
import com.danrsy.rgithubuser.data.model.User
import com.danrsy.rgithubuser.databinding.ActivityDetailBinding
import com.danrsy.rgithubuser.ui.common.SectionPagerAdapter
import com.danrsy.rgithubuser.ui.followers.FollowersViewModel
import com.danrsy.rgithubuser.ui.following.FollowingViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var followersViewModel: FollowersViewModel
    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra(EXTRA_DATA)

        initPageAdapter()

        followersViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollowersViewModel::class.java]
        followingViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollowingViewModel::class.java]

        if (username != null) {
            CoroutineScope(Dispatchers.Main).launch {
                detailViewModel.getUserData(username).observe(this@DetailActivity) {user ->
                    populateData(user)

                    var isFavorite = false
                    CoroutineScope(Dispatchers.Main).launch {
                        val count = detailViewModel.checkUser(user.id)
                        isFavorite = if (count > 0) {
                            favoriteState(true)
                            true
                        } else {
                            favoriteState(false)
                            false
                        }
                    }

                    binding.fabFavorite.setOnClickListener {
                        isFavorite = !isFavorite

                        if (isFavorite) {
                            detailViewModel.addToFavorite(user)
                            Toast.makeText(
                                this@DetailActivity, "Favorited",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            detailViewModel.removeFromFavorite(user)
                            Toast.makeText(
                                this@DetailActivity, "Unfavorited",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        favoriteState(isFavorite)
                    }

                }

            }

            followingViewModel.setListFollowing(username)
            followersViewModel.setListFollowers(username)

            detailViewModel.isLoading.observe(this) {
                showLoadingState(it)
            }

            detailViewModel.isError.observe(this) { state ->
                detailViewModel.errorMgs.observe(this) { msg ->
                    showErrorMsg(state, msg)
                }
            }
        }


    }

    private fun initPageAdapter() {
        val sectionsPagerAdapter = SectionPagerAdapter(this)
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
            if(isFavorite) load(R.drawable.ic_favorite_fill)
            else load(R.drawable.ic_favorite_outline)
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
            R.string.tab_folowing,
        )
    }
}