package com.azevedo.noticias

import android.content.ContentValues

data class Categoria(var nome: String, var descricao: String, var id: Long = -1) {

    fun toContentVaules(): ContentValues {
        val valores = ContentValues()

        valores.put("nome", nome)
        valores.put("descricao", descricao)

        return valores
    }

}