package com.azevedo.noticias

import android.database.Cursor
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import com.azevedo.noticias.databinding.FragmentPesquisaBinding
private const val ID_LOADER_NOTICIAS_PESQUISA = 0

class Pesquisa_Fragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentPesquisaBinding? = null
    private val binding get() = _binding!!
    private var adapterNoticias: AdapterNoticiasPesquisa? = null

    var noticiaSelecionado: Noticias? = null
        set(value) {
            field = value
        }
    var textPesquisa: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPesquisaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterNoticias = AdapterNoticiasPesquisa(this)
        binding.recyclerViewPesquisa.adapter = adapterNoticias
        binding.recyclerViewPesquisa.layoutManager = LinearLayoutManager(requireContext())

        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_NOTICIAS_PESQUISA, null, this)

        textPesquisa = binding.TextInputPesquisa.text.toString()

        binding.TextInputPesquisa.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Nenhuma ação necessária antes da alteração do texto
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Atualizar o texto de pesquisa sempre que houver uma alteração no TextInput
                textPesquisa = s.toString()
                restartLoader()
            }

            override fun afterTextChanged(s: Editable?) {
                // Nenhuma ação necessária após a alteração do texto
            }
        })

    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val selection = "${Tabela_Noticias.CAMPO_TITULO} LIKE ?"
        val selectionArgs = arrayOf("%$textPesquisa%")
        return CursorLoader(
            requireContext(),
            NoticiasContentProvider.ENDERECO_NOTICIAS,
            Tabela_Noticias.CAMPOS,
            selection,
            selectionArgs,
            Tabela_Noticias.CAMPO_TITULO
        )

    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        if (adapterNoticias != null) {
            adapterNoticias!!.cursor = null
        }
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterNoticias!!.cursor = data
    }

    private fun restartLoader() {
        LoaderManager.getInstance(this).restartLoader(ID_LOADER_NOTICIAS_PESQUISA, null, this)
    }


}
