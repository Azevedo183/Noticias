package com.azevedo.noticias

import android.content.ContentValues

data class Noticias(var titulo: String, var idCategoria: Long, var data: String, var id: Long = -1) {

    fun toContentValues() : ContentValues{
        val valores = ContentValues()

        valores.put(Tabela_Noticias.CAMPO_TITULO, titulo)
        valores.put(Tabela_Noticias.CAMPO_DATA, data)
        valores.put(Tabela_Noticias.CAMPO_FK_CATEGORIA, idCategoria)

        return valores
    }
}