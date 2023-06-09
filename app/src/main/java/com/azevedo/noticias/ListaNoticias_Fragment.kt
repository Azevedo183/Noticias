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
import com.azevedo.noticias.databinding.FragmentListaNoticiasBinding

private const val ID_LOADER_NOTICIAS = 0


class ListaNoticias_Fragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentListaNoticiasBinding? = null
    private val binding get() = _binding!!


    var noticiaSelecionado : Noticias? = null
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
    ): View {
        _binding = FragmentListaNoticiasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private var adapterNoticias: AdapterNoticias? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterNoticias = AdapterNoticias(this)
        binding.RecyclerViewNoticias.adapter = adapterNoticias
        binding.RecyclerViewNoticias.layoutManager = LinearLayoutManager(requireContext())

        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_NOTICIAS,null, this)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_lista_noticias

    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            NoticiasContentProvider.ENDERECO_NOTICIAS,
            Tabela_Noticias.CAMPOS,
            null, null,
            Tabela_Noticias.CAMPO_TITULO
        )
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        if (adapterNoticias != null){
            adapterNoticias!!.cursor = null
        }
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterNoticias!!.cursor = data
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean{
        return when (item.itemId) {
            R.id.action_adicionar -> {
                adicionaNoticia()
                true
            }
            R.id.action_editar -> {
                editarNoticia()
                true
            }
            R.id.action_eliminar -> {
                eliminarNoticia()
                true
            }
            else -> false
        }
    }



    private fun adicionaNoticia() {
        val acao = ListaNoticias_FragmentDirections.actionListaNoticiasFragmentToEditarNoticiaFragment(null)
        findNavController().navigate(acao)

    }
    private fun editarNoticia() {
        val acao = ListaNoticias_FragmentDirections.actionListaNoticiasFragmentToEditarNoticiaFragment(noticiaSelecionado!!)
        findNavController().navigate(acao)
    }

    private fun eliminarNoticia() {
        val acao = ListaNoticias_FragmentDirections.actionListaNoticiasFragmentToEliminarNoticiaFragmento(noticiaSelecionado!!)
        findNavController().navigate(acao)
    }


}