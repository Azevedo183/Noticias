package com.azevedo.noticias

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.azevedo.noticias.databinding.FragmentEleminarNoticiaFragmentoBinding
import android.text.format.DateFormat

class EliminarNoticia_Fragmento : Fragment() {
    private lateinit var noticias: Noticias
    private var _binding: FragmentEleminarNoticiaFragmentoBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEleminarNoticiaFragmentoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_eleminar

        noticias = EliminarNoticia_FragmentoArgs.fromBundle(requireArguments()).noticias

        binding.textViewTituloEliminar.text = noticias.titulo
        binding.textViewCategoriaEliminar.text = noticias.categoria.nome
        binding.textViewDataEliminar.text = DateFormat.format("dd-MM-yyyy", noticias.data)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_eliminar -> {
                eliminar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaLivros()
                true
            }
            else -> false
        }
    }

    private fun voltaListaLivros() {
        findNavController().navigate(R.id.action_eleminarNoticia_Fragmento_to_ListaNoticias_Fragment)
    }

    private fun eliminar() {
    }
}