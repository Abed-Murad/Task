package com.am.task.photo_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.am.task.R
import com.am.task.databinding.FragmentPhotoDetailsBinding
import com.am.task.compose.Tag
import com.am.task.remote.model.Photo
import com.google.accompanist.flowlayout.FlowRow
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PhotoDetailsFragment : Fragment(R.layout.fragment_photo_details) {
    private lateinit var binding: FragmentPhotoDetailsBinding
    private val params by navArgs<PhotoDetailsFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo_details, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val photo = params.photo
        binding.photo = photo
        populateUI(photo)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.backImageView.setOnClickListener { activity?.onBackPressed() }

    }

    private fun populateUI(photo: Photo) {
        binding.photoImageView.load(photo.largeImageURL) { placeholder(R.drawable.img_placeholder) }
        populateTagsComposeView(photo)
        binding.photo = photo
    }

    private fun populateTagsComposeView(photo: Photo) {
        binding.tagsView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    FlowRow(mainAxisSpacing = 16.dp) {
                        val tags = photo.tags.split(",")
                        tags.forEach {
                            Tag(text = { Text(text = it.uppercase()) })
                        }
                    }
                }
            }
        }
    }
}