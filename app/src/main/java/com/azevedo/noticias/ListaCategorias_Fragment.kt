package com.azevedo.noticias

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.azevedo.noticias.databinding.FragmentListaCategoriasBinding

private const val ID_LOADER_CATEGORIAS = 0
class ListaCategorias_Fragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentListaCategoriasBinding? = null
    private val binding get() = _binding!!


    var categoriaSelecionado : Categoria? = null
        set(value){
            field = value

            val mostrarEliminarAlterar = (value != null)
            val activity = activity as MainActivity
            activity.mostraBotaoMenu(R.id.action_editar,mostrarEliminarAlterar)
            activity.mostraBotaoMenu(R.id.action_eliminar,mostrarEliminarAlterar)
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListaCategoriasBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private var adapterCategorias: AdapterCategorias? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterCategorias = AdapterCategorias(this)
        binding.RecyclerViewCategorias.adapter = adapterCategorias
        binding.RecyclerViewCategorias.layoutManager = LinearLayoutManager(requireContext())

        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_CATEGORIAS,null, this)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_lista_noticias
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            NoticiasContentProvider.ENDERECO_CATEGORIA,
            Tabela_Categorias.CAMPOS,
            null, null,
            Tabela_Categorias.CAMPO_NOME
        )
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterCategorias!!.cursor = null
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterCategorias!!.cursor = data
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean{
        return when (item.itemId) {
            R.id.action_adicionar -> {
                adicionaCategoria()
                true
            }
            R.id.action_editar -> {
                editarCategoria()
                true
            }
            R.id.action_eliminar -> {
                eliminarCategoria()
                true
            }
            else -> false
        }
    }

    private fun eliminarCategoria() {
        val acao = ListaCategorias_FragmentDirections.actionListaCategoriasFragmentToEliminarCategoriaFragment(categoriaSelecionado!!)
        findNavController().navigate(acao)
    }

    private fun editarCategoria() {
        val acao = ListaCategorias_FragmentDirections.actionListaCategoriasFragmentToEditarCategoriaFragment(categoriaSelecionado!!)
        findNavController().navigate(acao)
    }

    private fun adicionaCategoria() {
        val acao = ListaCategorias_FragmentDirections.actionListaCategoriasFragmentToEditarCategoriaFragment(null)
        findNavController().navigate(acao)
    }



}