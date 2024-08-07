package com.example.admin_blinkit_clone.auth

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.admin_blinkit_clone.AdminMainActivity
import com.example.admin_blinkit_clone.R
import com.example.admin_blinkit_clone.databinding.FragmentSplashBinding
import com.example.admin_blinkit_clone.viewmodels.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    private val viewmodel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        setStatusBarColor()
        viewLifecycleOwner.lifecycleScope.launch {
            delay(3000) // Coroutine delay instead of Handler
            viewmodel.isCurrentUser.collect { isUserCurrent ->
                if (isUserCurrent) {
                    startActivity(Intent(requireContext(), AdminMainActivity::class.java))
                    requireActivity().finish()
                } else {
                    findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
                }
            }
        }
        return binding.root
    }

    private fun setStatusBarColor() {
        activity?.window?.apply {
            val statusBarColors = ContextCompat.getColor(requireContext(), R.color.yellow)
            statusBarColor = statusBarColors
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }
}
