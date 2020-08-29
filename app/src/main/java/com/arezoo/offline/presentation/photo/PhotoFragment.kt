package com.arezoo.offline.presentation.photo

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.SimpleItemAnimator
import com.arezoo.offline.databinding.FragmentPhotoBinding
import com.arezoo.offline.presentation.base.BaseFragment
import com.arezoo.offline.R
import com.arezoo.offline.data.model.Photo
import com.arezoo.offline.presentation.main.MainViewModel
import com.arezoo.offline.presentation.photo.adapter.PhotoAdapterListener
import com.arezoo.offline.presentation.photo.adapter.PhotoItemAdapter
import com.arezoo.offline.util.RecyclerViewPaginationScrollListener

class PhotoFragment : BaseFragment<MainViewModel, FragmentPhotoBinding>() {
    override val layoutId: Int = R.layout.fragment_photo

    private val adapter: PhotoItemAdapter by lazy {
        PhotoItemAdapter(object : PhotoAdapterListener {
            override fun onPhotoItemClick(photo: Photo) {
                showToast(photo.title.toString())
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setPhotoAdapter()
        setSwipeLayout()
    }

    private fun setPhotoAdapter() {
        context?.let {

            val decoration = DividerItemDecoration(it, 1).apply {
                ContextCompat.getDrawable(it, R.drawable.divider)?.apply { setDrawable(this) }
            }

            binding.photoRv.apply {
                adapter = this@PhotoFragment.adapter
                addItemDecoration(decoration)
                (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
                addOnScrollListener(object : RecyclerViewPaginationScrollListener(this) {
                    override fun loadMoreItems() {
                        getPhotos()
                    }

                    override val isLastPage: Boolean =
                        viewModel.getIsLastPage()


                    override val isLoading: Boolean =
                        viewModel.getIsLoading()

                })
            }
        }
    }

    private fun getPhotos() {
        viewModel.fetchPhotos(offset = adapter.itemCount)
    }

    private fun setSwipeLayout() {
        binding.swipeLayout.apply {
            setOnRefreshListener {
                refreshList()
                isRefreshing = false
            }
        }
    }

    private fun refreshList() {
        adapter.clearAdapterData()
        getPhotos()
    }
}