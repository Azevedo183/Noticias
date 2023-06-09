package com.azevedo.noticias

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.azevedo.noticias.databinding.FragmentNovaCategoriaBinding
import com.azevedo.noticias.databinding.FragmentSobreBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NovaCategoria_Fragment : Fragment() {

    private var _binding: FragmentNovaCategoriaBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNovaCategoriaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_main
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}