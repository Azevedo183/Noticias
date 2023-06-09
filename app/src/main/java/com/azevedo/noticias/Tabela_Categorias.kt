package com.azevedo.noticias

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


class Tabela_Categorias(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA , $CAMPO_NOME TEXT NOT NULL, $CAMPO_DESCRICAO TEXT NOT NULL)")
    }

    companion object{
        const val NOME_TABELA = "categorias"

        const val CAMPO_ID = "${NOME_TABELA}.${BaseColumns._ID}"
        const val CAMPO_NOME = "nome"
        const val CAMPO_DESCRICAO = "descricao"

        val CAMPOS = arrayOf(CAMPO_ID, CAMPO_NOME, CAMPO_DESCRICAO)


    }
}