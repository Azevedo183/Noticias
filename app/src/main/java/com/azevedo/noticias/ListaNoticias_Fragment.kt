package com.azevedo.noticias

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import com.azevedo.noticias.databinding.FragmentListaNoticiasBinding
import com.azevedo.noticias.databinding.FragmentSobreBinding

private const val ID_LOADER_NOTICIAS = 0

private val adapterNoticias1: AdapterNoticias
    get(){
        val adapterNoticias = AdapterNoticias()
        return adapterNoticias
    }

class ListaNoticias_Fragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentListaNoticiasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaNoticiasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val adapterNoticias = AdapterNoticias()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.RecyclerViewNoticias.adapter = adapterNoticias
        binding.RecyclerViewNoticias.layoutManager = LinearLayoutManager(requireContext())

        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_NOTICIAS,null, this)
    }
    companion object {

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
        adapterNoticias.cursor = null
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterNoticias.cursor = data
    }
}