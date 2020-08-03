package com.arezoo.offline.presentation.base

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider

interface BaseViewGroup<V : BaseViewModel, B : ViewDataBinding> {

    val layoutId : Int

    var binding : B

    val viewModel: V

    var viewModelFactory: ViewModelProvider.Factory
}