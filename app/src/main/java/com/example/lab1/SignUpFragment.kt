package com.example.lab1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.lab1.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonUp.setOnClickListener {
            val nameText = binding.editTextTextUp.text.toString()
            val emailText = binding.editTextTextEmailAddressUp.text.toString()
            val passwordText = binding.editTextNumberPasswordUp.text.toString()
            if (nameText.isNotEmpty() && emailText.isNotEmpty() && passwordText.isNotEmpty()) {
                val user = User(nameText, emailText, passwordText)
                val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment(user)
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("SignUpFragment", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("SignUpFragment", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("SignUpFragment", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("SignUpFragment", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("SignUpFragment", "onDestroy")
    }
}