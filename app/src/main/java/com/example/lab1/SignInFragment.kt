package com.example.lab1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab1.databinding.FragmentSignInBinding
import java.io.Serializable

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private var regEmail: String? = null
    private var regPass: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: SignInFragmentArgs by navArgs()
        val user = args.user
        if (user != null) {
            regEmail = user.email
            regPass = user.password
            binding.editTextTextEmailAddress.setText(user.email)
            binding.editTextNumberPassword.setText(user.password)
        }

        binding.button2.setOnClickListener {
            val emailText = binding.editTextTextEmailAddress.text.toString()
            val passwordText = binding.editTextNumberPassword.text.toString()
            if (emailText == "123" && passwordText == "123" || (emailText == regEmail && passwordText == regPass)) {
                findNavController().navigate(R.id.homeFragment)
            } else {
                Toast.makeText(requireContext(), "Incorrect email or password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.button3.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("SignInFragment", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("SignInFragment", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("SignInFragment", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("SignInFragment", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("SignInFragment", "onDestroy")
    }
}