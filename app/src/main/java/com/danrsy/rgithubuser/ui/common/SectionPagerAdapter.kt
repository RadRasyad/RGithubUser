package com.danrsy.rgithubuser.ui.common

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.danrsy.rgithubuser.ui.detail.DetailActivity
import com.danrsy.rgithubuser.ui.followers.FollowersFragment
import com.danrsy.rgithubuser.ui.following.FollowingFragment

class SectionPagerAdapter(activity: DetailActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        return fragment as Fragment
    }
}