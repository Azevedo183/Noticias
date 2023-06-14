package com.azevedo.noticias


import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.azevedo.noticias.databinding.FragmentNovaNoticiaBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import android.text.format.DateFormat


private const val ID_LOADER_CATEGORIAS = 0

class EditarNoticia_Fragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var noticias: Noticias?= null
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


        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_CATEGORIAS,null, this)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_guardar_cancelar

        val noticia = EditarNoticia_FragmentArgs.fromBundle(requireArguments()).noticias

        if (noticia != null){
            binding.insertTextTitulo.setText(noticia.titulo)
            binding.editTextData.setText(DateFormat.format("dd-MM-yyyy", noticia.data))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun voltarlistaNoticias(){
        findNavController().navigate(R.id.action_EditarNoticia_Fragment_to_ListaNoticias_Fragment)
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true
            }
            R.id.action_cancelar -> {
                voltarlistaNoticias()
                true
            }
            else -> false
        }
    }

    private fun guardar() {
        val titulo = binding.insertTextTitulo.text.toString()
        if (titulo.isBlank()) {
            binding.insertTextTitulo.error = getString(R.string.titulo_obrigatorio)
            binding.insertTextTitulo.requestFocus()
            return
        }

        val categoria = binding.SpinnerCategorias.selectedItemId
        if (categoria == Spinner.INVALID_ROW_ID) {
            binding.textViewCategoria.error = getString(R.string.categoria_obrigatoria)
            binding.SpinnerCategorias.requestFocus()
            return
        }

        val data: Date
        val df = SimpleDateFormat("dd-MM-yyyy")
        try {
            data = df.parse(binding.editTextData.text.toString())
        } catch (e: Exception) {
            binding.editTextData.error = getString(R.string.data_invalida)
            binding.editTextData.requestFocus()
            return
        }

        val calendario = Calendar.getInstance()
        calendario.time = data

        if (noticias == null) {
            val noticia = Noticias(
                titulo,
                Categoria("?", "?", categoria),
                calendario
            )
            insereNoticia(noticia)
        }else{
            val noticia = noticias!!
            noticia.titulo = titulo
            noticia.categoria = Categoria("?","?",categoria)
            noticia.data = calendario

            alteraNoticia(noticia)
        }
    }

    private fun alteraNoticia(
        noticia: Noticias
    ) {
        val enderecoNoticia = Uri.withAppendedPath(NoticiasContentProvider.ENDERECO_NOTICIAS, noticia.id.toString())
        val noticiasAlteradas = requireActivity().contentResolver.update(enderecoNoticia, noticia.toContentValues(), null,null)

        if(noticiasAlteradas == 1){
            Toast.makeText(requireContext(), R.string.noticia_guardada_com_sucesso, Toast.LENGTH_LONG).show()
            voltarlistaNoticias()
        }else{
            binding.insertTextTitulo.error = getString(R.string.n_o_foi_possivel_guardar_a_noticia)
        }
    }

    private fun insereNoticia(
        noticia: Noticias
    ){


           val id = requireActivity().contentResolver.insert(
                NoticiasContentProvider.ENDERECO_NOTICIAS,
                noticia.toContentValues()
            )

            if (id == null) {
                binding.insertTextTitulo.error =
                    getString(R.string.n_o_foi_possivel_guardar_a_noticia)
                return
            }


            Toast.makeText(
                requireContext(),
                getString(R.string.noticia_guardada_com_sucesso),
                Toast.LENGTH_LONG
            ).show()
            voltarlistaNoticias()
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
        binding.SpinnerCategorias.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(Tabela_Categorias.CAMPO_NOME),
            intArrayOf(android.R.id.text1),
            0
        )

        mostraCategoriaSelecionadaSpinner()
    }

    private fun mostraCategoriaSelecionadaSpinner() {
        if (noticias == null) return

        val idCategoria = noticias!!.categoria.id

        val ultimaCategoria = binding.SpinnerCategorias.count - 1
        for (i in 0..ultimaCategoria){
            if (idCategoria == binding.SpinnerCategorias.getItemIdAtPosition(i)){
                binding.SpinnerCategorias.setSelection(i)
                return
            }
        }
    }


}