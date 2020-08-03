package com.arezoo.offline.presentation.photo

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.arezoo.offline.databinding.FragmentPhotoBinding
import com.arezoo.offline.presentation.base.BaseFragment
import com.arezoo.offline.R
import com.arezoo.offline.data.model.Photo
import com.arezoo.offline.presentation.main.MainViewModel
import com.arezoo.offline.presentation.photo.adapter.PhotoAdapterListener
import com.arezoo.offline.presentation.photo.adapter.PhotoItemAdapter

class PhotoFragment : BaseFragment<MainViewModel, FragmentPhotoBinding>() {
    override val layoutId: Int = R.layout.fragment_photo

    private val adapter: PhotoItemAdapter by lazy {
        PhotoItemAdapter(object : PhotoAdapterListener {
            override fun onPhotoItemClick(photo: Photo) {
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLayout()
    }

    private fun setupLayout() {
        with(binding) {
            viewModel = this@PhotoFragment.viewModel

            photoRv.adapter = adapter
            context?.let {
                val decoration = DividerItemDecoration(it, 1).apply {
                    ContextCompat.getDrawable(it, R.drawable.divider)?.apply { setDrawable(this) }
                }
                photoRv.addItemDecoration(decoration)
            }
        }
    }
}