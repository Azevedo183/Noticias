package com.azevedo.noticias

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.azevedo.noticias.databinding.FragmentNovaCategoriaBinding
import com.azevedo.noticias.databinding.FragmentSobreBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

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
        activity.idMenuAtual = R.menu.menu_guardar_cancelar
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun voltarlistaCategorias(){
        findNavController().navigate(R.id.action_novaCategoria_Fragment_to_listaCategorias_Fragment)
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_guardar -> {
                guardarCategoria()
                true
            }
            R.id.action_cancelar -> {
                voltarlistaCategorias()
                true
            }
            else -> false
        }
    }

    private fun guardarCategoria() {
        val nome = binding.editTextNomeCategoria.text.toString()
        if (nome.isBlank()) {
            binding.editTextNomeCategoria.error = getString(R.string.nomeC_obrigatorio)
            binding.editTextNomeCategoria.requestFocus()
            return
        }

        val descricao = binding.editTextDescricaoCategoria.text.toString()
        if (descricao.isBlank()) {
            binding.editTextDescricaoCategoria.error = getString(R.string.descricao_obrigatoria)
            binding.editTextDescricaoCategoria.requestFocus()
            return
        }

        val categoria = Categoria(nome, descricao)

        requireActivity().contentResolver.insert(NoticiasContentProvider.ENDERECO_CATEGORIA, categoria.toContentVaules())

        if (id == null){
            binding.editTextNomeCategoria.error = getString(R.string.imposivel_guardar_categoria)
            return
        }


        Toast.makeText(requireContext(), getString(R.string.categoria_saved), Toast.LENGTH_LONG).show()
        voltarlistaCategorias()
    }

}