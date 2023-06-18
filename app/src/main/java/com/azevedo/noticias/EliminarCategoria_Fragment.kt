package com.azevedo.noticias

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.azevedo.noticias.databinding.FragmentEliminarCategoriaBinding
import com.google.android.material.snackbar.Snackbar


class EliminarCategoria_Fragment : Fragment() {
    private lateinit var categoria: Categoria
    private var _binding: FragmentEliminarCategoriaBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEliminarCategoriaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_eleminar

        categoria = EliminarCategoria_FragmentArgs.fromBundle(requireArguments()).categorias

        binding.textViewCategoriaCEliminar.text = categoria.nome
        binding.textViewDescricaoCEliminar.text = categoria.descricao
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
                voltaListaCategorias()
                true
            }
            else -> false
        }
    }

    private fun voltaListaCategorias() {
        findNavController().navigate(R.id.action_eliminarCategoria_Fragment_to_listaCategorias_Fragment)
    }

    private fun eliminar() {
        val enderecoCategorias = Uri.withAppendedPath(NoticiasContentProvider.ENDERECO_CATEGORIA, categoria.id.toString())
        val numCategoriaSelecionadas = requireActivity().contentResolver.delete(enderecoCategorias,null, null)

        if (numCategoriaSelecionadas == 1){
            Toast.makeText(requireContext(), getString(R.string.categoria_eliminada_com_sucesso), Toast.LENGTH_LONG).show()
            voltaListaCategorias()
        } else{
            Snackbar.make(binding.textViewCategoriaCEliminar, getString(R.string.erro_ao_eliminar_categoria), Snackbar.LENGTH_INDEFINITE)

        }
    }
}