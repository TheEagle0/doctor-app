package com.example.doctorapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentLoginBinding
import com.example.doctorapp.models.RemoteResult

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onLogInButtonClick()
    }

    private fun signIn(email: String, password: String) {
        loginViewModel.login(email, password)
            .observe(viewLifecycleOwner,
                {
                    when (it) {
                        is RemoteResult.Loading -> loginViewModel.setLoading(true)
                        is RemoteResult.Success -> {
                            findNavController().navigate(R.id.nav_home)
                           loginViewModel.saveEmail(it.data?.user?.email?:"")
                        }
                        is RemoteResult.Fail -> println(it.message)
                    }
                })
    }

    private fun onLogInButtonClick() {

        binding.loginButton.setOnClickListener {
            val email = binding.emailET.text.toString()
            val password = binding.passwordEt.text.toString()
            validateEmailAndPassword(email, password)
        }
    }

    private fun validateEmailAndPassword(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            signIn(email, password)
        } else {
            if (email.isEmpty())
                binding.emailET.error = "Please enter Email address"
            if (password.isEmpty() || password.length < 8)
                binding.passwordEt.error = "Please enter password at least 8 chars"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}