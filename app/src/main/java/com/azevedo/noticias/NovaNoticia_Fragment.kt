package com.azevedo.noticias


import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.SpinnerAdapter
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.azevedo.noticias.databinding.FragmentNovaNoticiaBinding


private const val ID_LOADER_CATEGORIAS = 0

class NovaNoticia_Fragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentNovaNoticiaBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNovaNoticiaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.SpinnerCategorias.adapter = SimpleCursorAdapter()

        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_CATEGORIAS,null, this)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_guardar_cancelar
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true
            }
            R.id.action_cancelar -> {
                cancelar()
                true
            }
            else -> false
        }
    }

    private fun cancelar() {
        findNavController().navigate(R.id.action_novaNoticia_Fragment_to_ListaNoticias_Fragment)
    }

    private fun guardar() {
        TODO("Not yet implemented")
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
        binding.SpinnerCategorias.adapter = null
    }


    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if(data == null){
            binding.SpinnerCategorias.adapter = null
            return
        }
        binding.SpinnerCategorias.adapter = SimpleCursorAdapter{
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(Tabela_Categorias.CAMPO_NOME),
            intArrayOf(android.R.id.text1),
            0
        }
    }
}