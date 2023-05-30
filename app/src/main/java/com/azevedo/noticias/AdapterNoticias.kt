package com.azevedo.noticias

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

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

        internal var noticias: Noticias?=null
            set(value){
                field = value
                textViewTitulo = noticias?.titulo ?: ""
                textViewData = noticias?.data.toString() ?: ""
                textViewCategoria = noticias.idCategoria.toString() ?: ""
            }
    }

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