package com.danrsy.rgithubuser.ui.followers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.danrsy.rgithubuser.data.model.User
import com.danrsy.rgithubuser.databinding.FragmentFollowersBinding
import com.danrsy.rgithubuser.ui.common.UsersAdapter
import com.danrsy.rgithubuser.ui.following.FollowingViewModel

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding

    private lateinit var viewModel: FollowersViewModel
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

        viewModel = ViewModelProvider(requireActivity())[FollowersViewModel::class.java]
        viewModel.getListFollowers().observe(viewLifecycleOwner) {

            populateData(it)
            if (it.size>0) {
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

}