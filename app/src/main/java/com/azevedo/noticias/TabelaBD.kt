package com.azevedo.noticias

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

abstract class TabelaBD(val db: SQLiteDatabase, val nome: String) {
    abstract fun cria()

    fun insere(valores: ContentValues) =
        db.insert(nome, null, valores)

    fun consulta(colunas: Array<String>, selecao: String?, argsSelecao: Array<String>?, groupBy: String?, having: String?, orderBy: String?)
        : Cursor = db.query(nome, colunas, selecao, argsSelecao, groupBy, having, orderBy)


    fun altera(valores: ContentValues, where: String, argsWhere: Array<String>) =
        db.update(nome, valores, where, argsWhere)

    fun apaga(where: String, argsWhere: Array<String>) =
        db.delete(nome, where, argsWhere)

}