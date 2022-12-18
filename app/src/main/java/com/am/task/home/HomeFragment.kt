package com.am.task.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.am.task.ApiConstants.ARG_PHOTO
import com.am.task.R
import com.am.task.databinding.FragmentHomeBinding
import com.am.task.remote.model.Photo
import com.am.task.remote.network.Status
import com.am.task.remote.network.coroutines.toResult
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), PhotosAdapter.Listener {

    private val viewModel :HomeViewModel by viewModels()
    private val adapter = PhotosAdapter(this)
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.photosRecyclerView.adapter = adapter
        binding.viewmodel = viewModel
        setupObservers()

    }

    private fun setupObservers() {
        lifecycleScope.launchWhenResumed {
            loadPhotos()
        }
    }

    private suspend fun loadPhotos() {
        viewModel.showProgress.set(true)
        val result = viewModel.getAllPhotos().toResult(viewLifecycleOwner)
        viewModel.showProgress.set(false)
        if (result.status == Status.SUCCESS) {
            adapter.submitList(result.data?.photos)
        } else if (result.status == Status.ERROR) {
            Snackbar.make(binding.root, getString(R.string.something_went_wrong_please_try_again_later), Snackbar.LENGTH_LONG).show()

        }
    }

    override fun onItemClicked(photo: Photo) {
        navigateToPhotoDetailsFragment(photo)
    }

    private fun navigateToPhotoDetailsFragment(photo: Photo) {
        val bundle = bundleOf(ARG_PHOTO to photo)
        findNavController().navigate(R.id.action_homeFragment_to_photoDetailsFragment, bundle)
    }

}