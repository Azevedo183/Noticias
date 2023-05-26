package com.azevedo.noticias

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.azevedo.noticias.databinding.FragmentMenuprincipalBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MenuPrincipal_Fargment : Fragment() {

    private var _binding: FragmentMenuprincipalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMenuprincipalBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSobre.setOnClickListener {
            findNavController().navigate(R.id.action_MenuPrincipal_Fragment_to_Sobre_Fragment)
        }

        binding.buttonNoticias.setOnClickListener {
            findNavController().navigate(R.id.action_MenuPrincipal_Fragment_to_ListaNoticias_Fragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}