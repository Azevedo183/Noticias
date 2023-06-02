package com.azevedo.noticias

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.text.SimpleDateFormat

class AdapterNoticias(val fragment: ListaNoticias_Fragment) : RecyclerView.Adapter<AdapterNoticias.ViewHolderNoticias>() {

    var cursor: Cursor? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    inner class ViewHolderNoticias(contentor: View) : ViewHolder(contentor) {
        private val textViewTitulo = contentor.findViewById<TextView>(R.id.textView_Titulo)
        private val textViewData = contentor.findViewById<TextView>(R.id.textView_Data)
        private val textViewCategoria = contentor.findViewById<TextView>(R.id.textView_Categoria)

        init {
            contentor.setOnClickListener{
                viewHolderSeleccionado?.desSeleciona()
                seleciona()
            }
        }

        internal var noticias: Noticias? = null
            set(value){
                field = value
                textViewTitulo.text = noticias?.titulo ?: ""
                val formatoData = SimpleDateFormat("dd/MM/yyyy")
                val dataFormatada = noticias?.data?.let { formatoData.format(it.time) } ?: ""
                textViewData.text = dataFormatada
                //textViewData.text = noticias?.data.toString() ?: ""
                textViewCategoria.text = noticias?.categoria?.nome ?: ""
            }

        fun seleciona(){
            viewHolderSeleccionado = this
            itemView.setBackgroundResource(R.color.item_selecionado)
        }

        fun desSeleciona(){
            itemView.setBackgroundResource(android.R.color.white)
        }
    }

    private var viewHolderSeleccionado : ViewHolderNoticias? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderNoticias {
        return ViewHolderNoticias(
            fragment.layoutInflater.inflate(R.layout.item_noticia, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolderNoticias, position: Int) {
        cursor!!.moveToPosition(position)
        holder.noticias=Noticias.formCursor(cursor!!)
    }
}