package com.danrsy.rgithubuser.ui.followers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.danrsy.rgithubuser.data.model.User
import com.danrsy.rgithubuser.databinding.FragmentFollowersBinding
import com.danrsy.rgithubuser.ui.common.UsersAdapter


class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding

    private val viewModel by viewModels<FollowersViewModel>()
    private lateinit var adapter: UsersAdapter

    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(KEY_BUNDLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getListFollowers().observe(viewLifecycleOwner) {
            if (it.size>0) {
                populateData(it)
                showEmptyState(false)
            } else {
                showEmptyState(true)
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoadingState(it)
        }

        viewModel.isError.observe(viewLifecycleOwner) { state ->
            viewModel.errorMgs.observe(viewLifecycleOwner) { msg ->
                showErrorMsg(state, msg)
            }
        }

    }

    private fun populateData(data : ArrayList<User>) {
        adapter = UsersAdapter(data)
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
        private const val KEY_BUNDLE = "USERNAME"

        fun getInstance(username: String): Fragment {
            return FollowersFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_BUNDLE, username)
                }
            }
        }
    }
}