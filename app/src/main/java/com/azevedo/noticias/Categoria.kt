package com.azevedo.noticias

import android.content.ContentValues

data class Categoria(var nome: String, var descricao: String, var id: Long = -1) {

    fun toContentVaules(): ContentValues {
        val valores = ContentValues()

        valores.put(Tabela_Categorias.CAMPO_NOME, nome )
        valores.put(Tabela_Categorias.CAMPO_DESCRICAO, descricao)

        return valores
    }

}