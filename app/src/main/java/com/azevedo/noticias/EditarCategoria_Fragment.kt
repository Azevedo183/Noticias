package com.azevedo.noticias

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.azevedo.noticias.databinding.FragmentEditarCategoriaBinding

class EditarCategoria_Fragment : Fragment() {
    private var categorias: Categoria?= null
    private var _binding: FragmentEditarCategoriaBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditarCategoriaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_guardar_cancelar

        val categoria = EditarCategoria_FragmentArgs.fromBundle(requireArguments()).categorias

        if (categoria != null){
            activity.atualizaTitulo(R.string.editar_categoria)

            binding.editTextNomeCategoria.setText(categoria.nome)
            binding.editTextDescricaoCategoria.setText(categoria.descricao)
        }else{
            activity.atualizaTitulo(R.string.NovaCategoria_Lable)
        }

        this.categorias = categoria
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun voltarlistaCategorias(){
        findNavController().navigate(R.id.action_EditarCategoria_Fragment_to_listaCategorias_Fragment)
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



        if (categorias == null){
            val categoria = Categoria(nome, descricao)

            insereCategoria(categoria)
        }else{
            val categoria = categorias!!
            categoria.nome = nome
            categoria.descricao = descricao

            alteraCategoria(categoria)
        }

    }

    private fun alteraCategoria(categoria: Categoria) {
        val enderecoCategoria = Uri.withAppendedPath(NoticiasContentProvider.ENDERECO_CATEGORIA, categoria.id.toString())
        val CategoriasAlteradas = requireActivity().contentResolver.update(enderecoCategoria, categoria.toContentVaules(), null,null)

        if(CategoriasAlteradas == 1){
            Toast.makeText(requireContext(), getString(R.string.categoria_alterada_com_sucesso), Toast.LENGTH_LONG).show()
            voltarlistaCategorias()
        }else{
            binding.editTextNomeCategoria.error = getString(R.string.imposivel_guardar_categoria)
        }
    }

    private fun insereCategoria(categoria: Categoria) {
        val id = requireActivity().contentResolver.insert(
            NoticiasContentProvider.ENDERECO_CATEGORIA,
            categoria.toContentVaules()
        )

        if (id == null) {
            binding.editTextNomeCategoria.error =
                getString(R.string.imposivel_guardar_categoria)
            return
        }


        Toast.makeText(
            requireContext(),
            getString(R.string.categoria_saved),
            Toast.LENGTH_LONG
        ).show()
        voltarlistaCategorias()
    }

}