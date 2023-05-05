package com.azevedo.noticias

import android.database.sqlite.SQLiteDatabase

private const val NOME_TABELA = "categorias"

class Tabela_Categorias(db: SQLiteDatabase) : TabelaBD(db, "categorias") {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA , CAMPO_NOME TEXT NOT NULL, CAMPO_DESCRIÇÃO TEXT NOT NULL)")


    }
}