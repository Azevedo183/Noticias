package com.azevedo.noticias

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


class Tabela_Noticias(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, titulo TEXT NOT NULL, data DATE NOT NULL, id_categoria INTEGER NOT NULL, FOREIGN KEY (id_categoria) REFERENCES ${Tabela_Categorias.NOME_TABELA}(${BaseColumns._ID}))")
    }

    companion object{
        const val NOME_TABELA = "noticias"
    }
}
