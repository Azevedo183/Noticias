package com.azevedo.noticias

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


private const val VERSAO_BADE_DADOS = 1

class BdNoticiasOpenHelper(
    context: Context?,
) : SQLiteOpenHelper(context, NOME_BASE_DADOS, null, VERSAO_BADE_DADOS) {
    override fun onCreate(db: SQLiteDatabase?) {
        requireNotNull(db)
        Tabela_Categorias(db).cria()
        Tabela_Noticias(db).cria()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion : Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    companion object{
        const val NOME_BASE_DADOS = "Noticias.db"
    }
}