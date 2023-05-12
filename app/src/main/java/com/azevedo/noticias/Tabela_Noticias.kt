package com.azevedo.noticias

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


class Tabela_Noticias(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_TITULO TEXT NOT NULL, $CAMPO_DATA DATE NOT NULL, $CAMPO_FK_CATEGORIA INTEGER NOT NULL, FOREIGN KEY ($CAMPO_FK_CATEGORIA) REFERENCES ${Tabela_Categorias.NOME_TABELA}(${BaseColumns._ID}))")
    }

    companion object{
        const val NOME_TABELA = "noticias"
        const val CAMPO_TITULO = "titulo"
        const val CAMPO_DATA = "data"
        const val CAMPO_FK_CATEGORIA = "id_categoria"
    }
}
