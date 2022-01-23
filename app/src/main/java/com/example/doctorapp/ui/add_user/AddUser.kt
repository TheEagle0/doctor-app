package com.example.doctorapp.ui.add_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.doctorapp.databinding.FragmentAddUserBinding
import com.example.doctorapp.models.Chat
import com.example.doctorapp.models.RemoteResult
import com.example.doctorapp.models.User

class AddUser : Fragment() {

    private val addUserViewModel: AddUserViewModel by lazy {
        ViewModelProvider(this).get(
            AddUserViewModel::class.java
        )
    }
    private var _binding: FragmentAddUserBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onAddUserButtonClick()
    }

    private fun addUser(email: String, password: String, phone: String, name: String) {
        addUserViewModel.addUser(email, password, phone)
            .observe(viewLifecycleOwner,
                {
                    when (it) {
                        is RemoteResult.Loading -> addUserViewModel.setLoading(true)
                        is RemoteResult.Success -> {
                            createUserDoc(email, name, phone)
                        }
                        is RemoteResult.Fail -> println(it.message)
                    }
                })
    }

    private fun createUserDoc(email: String, name: String, phone: String) {
        val user = User(name, email, phone)
        addUserViewModel.createUserDoc(user).observe(viewLifecycleOwner, {
            when (it) {
                is RemoteResult.Success -> createChatDoc(email, name, phone)
                is RemoteResult.Fail -> Toast.makeText(
                    context,
                    "Faild to create user",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun createChatDoc(email: String, name: String, phone: String) {
        val chat = Chat(patientEmail = email)
        addUserViewModel.createChatDoc(chat).observe(viewLifecycleOwner, {
            when (it) {
                is RemoteResult.Success -> Toast.makeText(
                    context,
                    "User created successfully",
                    Toast.LENGTH_SHORT
                ).show()
                is RemoteResult.Fail -> Toast.makeText(
                    context,
                    "Faild to create user",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun onAddUserButtonClick() {

        binding.addUserButton.setOnClickListener {
            val email = binding.emailET.text.toString()
            val password = binding.passwordEt.text.toString()
            val phone = binding.phoneEt.text.toString()
            val name = binding.nameEt.text.toString()
            validateEmailAndPassword(email, password, phone, name)
        }
    }

    private fun validateEmailAndPassword(
        email: String,
        password: String,
        phone: String,
        name: String
    ) {
        if (email.isNotEmpty() && password.isNotEmpty() && phone.isNotEmpty() && name.isNotEmpty()) {
            addUser(email, password, phone, name)

        } else {
            if (email.isEmpty())
                binding.emailET.error = "Please enter Email address"
            if (password.isEmpty() || password.length < 8)
                binding.passwordEt.error = "Please enter password at least 8 chars"
            if (phone.isEmpty() || phone.length < 11)
                binding.passwordEt.error = "Please enter phone number at least 11 chars"
            if (name.isEmpty())
                binding.passwordEt.error = "Please enter a valid name"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}