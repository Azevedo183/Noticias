package com.azevedo.noticias

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns


class Tabela_Noticias(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_TITULO TEXT NOT NULL, $CAMPO_DATA INTEGER NOT NULL, $CAMPO_FK_CATEGORIA INTEGER NOT NULL, FOREIGN KEY ($CAMPO_FK_CATEGORIA) REFERENCES ${Tabela_Categorias.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    override fun consulta(
        colunas: Array<String>,
        selecao: String?,
        argsSelecao: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor {
        val sql = SQLiteQueryBuilder()
        sql.tables = "$NOME_TABELA INNER JOIN ${Tabela_Categorias.NOME_TABELA} ON ${Tabela_Categorias.CAMPO_ID} = $CAMPO_FK_CATEGORIA"

        return sql.query(db, colunas, selecao, argsSelecao, groupBy, having, orderBy)
    }

    companion object{
        const val NOME_TABELA = "noticias"

        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"
        const val CAMPO_TITULO = "titulo"
        const val CAMPO_DATA = "data"
        const val CAMPO_FK_CATEGORIA = "id_categoria"
        const val CAMPO_NOME_CATEGORIA = Tabela_Categorias.CAMPO_NOME
        const val CAMPO_DESC_CATEGORIA = Tabela_Categorias.CAMPO_DESCRICAO

        val CAMPOS = arrayOf(CAMPO_ID, CAMPO_TITULO, CAMPO_DATA, CAMPO_FK_CATEGORIA, CAMPO_NOME_CATEGORIA, CAMPO_DESC_CATEGORIA)
    }
}
