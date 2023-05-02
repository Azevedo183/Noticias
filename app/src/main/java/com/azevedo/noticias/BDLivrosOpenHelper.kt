package com.azevedo.noticias

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val NOME_BASE_DADOS = "Noticias.db"
private const val VERSAO_BADE_DADOS = 1

class BDLivrosOpenHelper(
    context: Context?,
) : SQLiteOpenHelper(context, NOME_BASE_DADOS, null, VERSAO_BADE_DADOS) {
    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, oldVersion : Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}