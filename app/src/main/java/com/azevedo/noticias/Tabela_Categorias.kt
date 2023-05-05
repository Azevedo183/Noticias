package com.azevedo.noticias

import android.database.sqlite.SQLiteDatabase


class Tabela_Categorias(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA , nome TEXT NOT NULL, descricao TEXT NOT NULL)")
    }

    companion object{
        const val NOME_TABELA = "categorias"
    }
}