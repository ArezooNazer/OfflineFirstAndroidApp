package com.arezoo.offline.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<V : BaseViewModel, B : ViewDataBinding> : Fragment(),
    BaseViewGroup<V, B> {
    var rootView: View? = null
    override lateinit var binding: B

    override lateinit var viewModelFactory: ViewModelProvider.Factory

    override val viewModel: V by lazy {
        @Suppress("UNCHECKED_CAST")
        ViewModelProvider(this, viewModelFactory).get(
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<V>
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            binding = DataBindingUtil.inflate(inflater, layoutId, null, false)
            binding.lifecycleOwner = this
            rootView = binding.root
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.errorTextMessage.observe(viewLifecycleOwner, Observer {
            it?.apply {
                showSnackBar(it, 5000)
            }
        })
    }

    open fun showToast(message: String) {
        if (isAdded)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showSnackBar(message: String, duration: Int) {
        if (view == null) return
        view?.let {
            val snackBar: Snackbar = Snackbar.make(it, message, duration).apply {
                ViewCompat.setLayoutDirection(it, ViewCompat.LAYOUT_DIRECTION_RTL)
            }
            snackBar.show()
        }
    }
}