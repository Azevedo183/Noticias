package com.azevedo.noticias

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.Calendar

data class Noticias(var titulo: String, var idCategoria: Long, var data: Calendar, var id: Long = -1) {

    fun toContentValues() : ContentValues{
        val valores = ContentValues()

        valores.put(Tabela_Noticias.CAMPO_TITULO, titulo)
        valores.put(Tabela_Noticias.CAMPO_DATA, data.timeInMillis)
        valores.put(Tabela_Noticias.CAMPO_FK_CATEGORIA, idCategoria)

        return valores
    }

    companion object{
        fun formCursor(cursor: Cursor): Noticias{
            val posicaoid = cursor.getColumnIndex(BaseColumns._ID)
            val posicaoTitulo = cursor.getColumnIndex(Tabela_Noticias.CAMPO_TITULO)
            val posicaoData = cursor.getColumnIndex(Tabela_Noticias.CAMPO_DATA)
            val posCategoriaFK = cursor.getColumnIndex(Tabela_Noticias.CAMPO_FK_CATEGORIA)


            val id = cursor.getLong(posicaoid)
            val titulo = cursor.getString(posicaoTitulo)
            val data: Calendar = Calendar.getInstance()
            data.timeInMillis = cursor.getLong(posicaoData)
            val categoriaID = cursor.getLong(posCategoriaFK)

            return Noticias(titulo, categoriaID, data ,id)
        }
    }
}