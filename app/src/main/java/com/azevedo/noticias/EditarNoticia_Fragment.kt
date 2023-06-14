package com.azevedo.noticias


import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import java.util.Calendar
import com.azevedo.noticias.databinding.FragmentEditarNoticiaBinding


private const val ID_LOADER_CATEGORIAS = 0

class EditarNoticia_Fragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var noticias: Noticias?= null
    private var _binding: FragmentEditarNoticiaBinding? = null
    private var dataNoticia : Calendar? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEditarNoticiaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendarViewDataNoticia.setOnDateChangeListener { calendarView,dayOfMonth , month,year  ->
            if (dataNoticia == null) dataNoticia = Calendar.getInstance()
            dataNoticia!!.set(dayOfMonth , month,year)
        }

        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_CATEGORIAS,null, this)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_guardar_cancelar

        val noticia = EditarNoticia_FragmentArgs.fromBundle(requireArguments()).noticias

        if (noticia != null){
            activity.atualizaTitulo(R.string.editar_label)

            binding.insertTextTitulo.setText(noticia.titulo)
            if (noticia.data != null) {
                dataNoticia = noticia.data
                binding.calendarViewDataNoticia.date = dataNoticia!!.timeInMillis
            }
        }else{
            activity.atualizaTitulo(R.string.nova_noticia_lable)
        }

        this.noticias = noticia
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
                voltarlistaNoticias()
                true
            }
            else -> false
        }
    }

    private fun voltarlistaNoticias(){
        findNavController().navigate(R.id.action_EditarNoticia_Fragment_to_ListaNoticias_Fragment)
    }
    private fun guardar() {
        val titulo = binding.insertTextTitulo.text.toString()
        if (titulo.isBlank()) {
            binding.insertTextTitulo.error = getString(R.string.titulo_obrigatorio)
            binding.insertTextTitulo.requestFocus()
            return
        }

        val categoria = binding.SpinnerCategorias.selectedItemId


        if (noticias == null) {
            val noticia = Noticias(
                titulo,
                Categoria("?", "?", categoria),
                dataNoticia
            )
            insereNoticia(noticia)
        }else{
            val noticia = noticias!!
            noticia.titulo = titulo
            noticia.categoria = Categoria("?","?",categoria)
            noticia.data = dataNoticia

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
        if (_binding != null) {
            binding.SpinnerCategorias.adapter = null
        }
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