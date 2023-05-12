package com.azevedo.noticias

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Categoria(var nome: String, var descricao: String, var id: Long = -1) {

    fun toContentVaules(): ContentValues {
        val valores = ContentValues()

        valores.put(Tabela_Categorias.CAMPO_NOME, nome )
        valores.put(Tabela_Categorias.CAMPO_DESCRICAO, descricao)

        return valores
    }

    companion object{
        fun formCursor(cursor: Cursor): Categoria{
            val posid = cursor.getColumnIndex(BaseColumns._ID)
            val posicaoNome = cursor.getColumnIndex(Tabela_Categorias.CAMPO_NOME)
            val posicaoDescricao = cursor.getColumnIndex(Tabela_Categorias.CAMPO_DESCRICAO)


            val id = cursor.getLong(posid)
            val nome = cursor.getString(posicaoNome)
            val descricao = cursor.getString(posicaoDescricao)

            return Categoria(nome,descricao ,id)
        }
    }

}