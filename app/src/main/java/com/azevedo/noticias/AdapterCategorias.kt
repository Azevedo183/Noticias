package com.azevedo.noticias

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

class AdapterCategorias(val fragment: ListaCategorias_Fragment):  RecyclerView.Adapter<AdapterCategorias.ViewHolderCategorias>() {
    var cursor: Cursor? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolderCategorias(contentor: View) : RecyclerView.ViewHolder(contentor) {
        private val textViewNomeCategoria = contentor.findViewById<TextView>(R.id.textView_NomeCategoria)
        private val textViewDescricaoCategoria = contentor.findViewById<TextView>(R.id.textView_DescricaoCategoria)

        init {
            contentor.setOnClickListener{
                viewHolderSeleccionado?.desSeleciona()
                seleciona()
            }
        }

        internal var categorias: Categoria? = null
            set(value){
                field = value
                textViewNomeCategoria.text = categorias?.nome ?: ""
                textViewDescricaoCategoria.text = categorias?.descricao ?: ""
            }

        fun seleciona(){
            viewHolderSeleccionado = this
            fragment.categoriaSelecionado = categorias
            itemView.setBackgroundResource(R.color.item_selecionado)
        }

        fun desSeleciona(){
            itemView.setBackgroundResource(android.R.color.white)
        }
    }

    private var viewHolderSeleccionado : AdapterCategorias.ViewHolderCategorias? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCategorias.ViewHolderCategorias {
        return ViewHolderCategorias(
            fragment.layoutInflater.inflate(R.layout.item_categoria, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }

    override fun onBindViewHolder(holder: AdapterCategorias.ViewHolderCategorias, position: Int) {
        cursor!!.moveToPosition(position)
        holder.categorias=Categoria.formCursor(cursor!!)
    }

}