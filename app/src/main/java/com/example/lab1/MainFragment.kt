package com.example.lab1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.lab1.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToSignInFragment(null)
            findNavController().navigate(action)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainFragment", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainFragment", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainFragment", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainFragment", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
