package com.azevedo.noticias

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable
import java.util.Calendar

data class Noticias(
    var titulo: String,
    var categoria: Categoria,
    var data: Calendar,
    var id: Long = -1) : Serializable {

    fun toContentValues() : ContentValues{
        val valores = ContentValues()

        valores.put(Tabela_Noticias.CAMPO_TITULO, titulo)
        valores.put(Tabela_Noticias.CAMPO_DATA, data.timeInMillis)
        valores.put(Tabela_Noticias.CAMPO_FK_CATEGORIA, categoria.id)

        return valores
    }

    companion object{
        fun formCursor(cursor: Cursor): Noticias{
            val posicaoid = cursor.getColumnIndex(BaseColumns._ID)
            val posicaoTitulo = cursor.getColumnIndex(Tabela_Noticias.CAMPO_TITULO)
            val posicaoData = cursor.getColumnIndex(Tabela_Noticias.CAMPO_DATA)
            val posCategoriaFK = cursor.getColumnIndex(Tabela_Noticias.CAMPO_FK_CATEGORIA)
            val posNomeCateg = cursor.getColumnIndex(Tabela_Noticias.CAMPO_NOME_CATEGORIA)
            val posDescCateg = cursor.getColumnIndex(Tabela_Noticias.CAMPO_DESC_CATEGORIA)


            val id = cursor.getLong(posicaoid)
            val titulo = cursor.getString(posicaoTitulo)
            val data: Calendar = Calendar.getInstance()
            data.timeInMillis = cursor.getLong(posicaoData)
            val categoriaID = cursor.getLong(posCategoriaFK)
            val nomeCategoria = cursor.getString(posNomeCateg)
            val descCategoria = cursor.getString(posDescCateg)

            return Noticias(titulo, Categoria(nomeCategoria, descCategoria,categoriaID), data ,id)
        }
    }
}