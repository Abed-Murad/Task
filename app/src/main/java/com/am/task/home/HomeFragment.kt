package com.am.task.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.am.task.ApiConstants.ARG_PHOTO
import com.am.task.R
import com.am.task.databinding.FragmentHomeBinding
import com.am.task.remote.model.Photo
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
        viewModel.getAllPhotos().observe(viewLifecycleOwner){
            adapter.submitList(it.data?.photos)
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