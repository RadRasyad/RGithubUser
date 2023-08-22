package com.danrsy.rgithubuser.ui.followers

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.danrsy.rgithubuser.core.domain.model.User
import com.danrsy.rgithubuser.databinding.FragmentFollowersBinding
import com.danrsy.rgithubuser.core.ui.UsersAdapter
import com.danrsy.rgithubuser.ui.following.FollowingFragment
import com.danrsy.rgithubuser.core.data.Resource
import com.danrsy.rgithubuser.ui.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding

    private val followersViewModel: FollowersViewModel by viewModel()
    private lateinit var adapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            val username = arguments?.getString(FollowingFragment.EXTRA_USERNAME)
            if (username != null) {
                followersViewModel.followers(username).observe(viewLifecycleOwner) { user ->
                    when (user) {
                        is Resource.Success -> {
                            if (user.data?.isEmpty() == true) {
                                showEmptyState(true)
                            } else {
                                Log.d("followers", user.data?.size.toString())
                                user.data?.let { populateData(it) }
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
                            showErrorMsg(true, user.message)
                            showEmptyState(false)
                            showLoadingState(false)
                        }
                    }
                }
            }
        }

    }

    private fun populateData(data : List<User>) {

        adapter = UsersAdapter(data)
        adapter.onItemClick = { selectedData ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, selectedData.login)
            startActivity(intent)
        }
        binding?.apply {
            rvUser.layoutManager = LinearLayoutManager(requireContext())
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }
    }

    private fun showLoadingState(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showEmptyState(state: Boolean) {
        binding?.emptyState?.root?.visibility  = if (state) View.VISIBLE else View.GONE
        binding?.rvUser?.visibility  = if (state) View.GONE else View.VISIBLE
    }

    private fun showErrorMsg(state: Boolean, msg: String?) {
        if (state) {
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        const val EXTRA_USERNAME = "username"
        fun newInstance(username: String): FollowersFragment {
            val fragment = FollowersFragment()

            val bundle = Bundle().apply {
                putString(EXTRA_USERNAME, username)
            }

            fragment.arguments = bundle

            return fragment
        }
    }
}