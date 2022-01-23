package com.example.doctorapp.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentHomeBinding
import com.example.doctorapp.models.RemoteResult
import com.example.doctorapp.models.User

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onFabClicked()
        getUser()
        onPhoneClicked()
    }

    private fun getUser() {
        homeViewModel.getUser().observe(viewLifecycleOwner, {
            when (it) {
                is RemoteResult.Loading -> {
                }
                is RemoteResult.Success -> {
                    val user: User = it.data
                    fillUserData(user)
                }
                is RemoteResult.Fail -> Toast.makeText(
                    context,
                    "error occurred",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun fillUserData(user: User) {
        binding.name.text = user.name
        binding.phone.text = user.phone
        binding.email.text = user.email
    }

    private fun onFabClicked() {
        binding.addUser.setOnClickListener {
            findNavController().navigate(R.id.nav_add_user)
        }
    }

    private fun onPhoneClicked() {
        binding.phone.setOnClickListener {
            val phone = binding.phone.text
            if (phone.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${phone}")
                startActivity(intent)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}