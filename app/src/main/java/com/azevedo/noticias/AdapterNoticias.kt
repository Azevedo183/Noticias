package com.azevedo.noticias

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class AdapterNoticias(val fragment: ListaNoticias_Fragment) : RecyclerView.Adapter<AdapterNoticias.ViewHolderNoticias>() {

    var cursor: Cursor? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    inner class ViewHolderNoticias(contentor: View) : ViewHolder(contentor) {
            internal var noticias: Noticias?=null
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
        cursor!!.move(position)
        holder.noticias=Noticias.formCursor(cursor!!)
    }
}