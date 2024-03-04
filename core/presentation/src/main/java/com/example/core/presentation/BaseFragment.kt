package com.example.core.presentation

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment()