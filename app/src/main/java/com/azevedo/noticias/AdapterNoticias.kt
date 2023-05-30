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
    inner class ViewHolderNoticias(itemView: View) : ViewHolder(itemView) {

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
        TODO("Not yet implemented")
    }
}