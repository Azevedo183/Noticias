package com.azevedo.noticias

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class NoticiasContentProvider : ContentProvider() {
    private var bdOpenHelper : BdNoticiasOpenHelper? = null
    override fun onCreate(): Boolean {
        bdOpenHelper = BdNoticiasOpenHelper(context)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val bd = bdOpenHelper!!.readableDatabase
        val endereco = uriMatcher().match(uri)

        val tabela = when (endereco) {
            URI_CATEGORIAS, URI_CATEGORIA_ID -> Tabela_Categorias(bd)
            URI_NOTICIAS, URI_NOTICIA_ID  -> Tabela_Noticias(bd)
            else -> null
        }

        val id = uri.lastPathSegment

        val (selecao, argsSel) = when (endereco) {
                URI_CATEGORIA_ID, URI_NOTICIA_ID -> Pair("${BaseColumns._ID}=?", arrayOf(id))
            else -> Pair(selection, selectionArgs)
        }

            return tabela?.consulta(
                projection as Array<String>,
                selecao,
                argsSel as Array<String>?,
                null,
                null,
                sortOrder)
    }

    override fun getType(uri: Uri): String? {
        val endereco = uriMatcher().match(uri)

        return when(endereco){
            URI_CATEGORIAS -> "vnd.android.cursor.dir/$CATEGORIAS"
            URI_CATEGORIA_ID -> "vnd.android.item/$CATEGORIAS"
            URI_NOTICIAS -> "vnd.android.cursor.dir/$NOTICIAS"
            URI_NOTICIA_ID -> "vnd.android.item/$NOTICIAS"
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val bd = bdOpenHelper!!.writableDatabase
        val endereco = uriMatcher().match(uri)

        val tabela = when (endereco) {
            URI_CATEGORIAS -> Tabela_Categorias(bd)
            URI_NOTICIAS -> Tabela_Noticias(bd)
            else -> return null
        }

        val id = tabela.insere(values!!)
        if (id == -1L){
            return null
        }
            return Uri.withAppendedPath(uri, id.toString())
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val bd = bdOpenHelper!!.writableDatabase
        val endereco = uriMatcher().match(uri)

        val tabela = when (endereco) {
            URI_CATEGORIA_ID -> Tabela_Categorias(bd)
            URI_NOTICIA_ID -> Tabela_Noticias(bd)
            else -> return 0
        }

        val id = uri.lastPathSegment!!
        return tabela.elimina( "${BaseColumns._ID}=?", arrayOf(id))
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val bd = bdOpenHelper!!.writableDatabase
        val endereco = uriMatcher().match(uri)

        val tabela = when (endereco) {
            URI_CATEGORIA_ID -> Tabela_Categorias(bd)
            URI_NOTICIA_ID -> Tabela_Noticias(bd)
            else -> return 0
        }

        val id = uri.lastPathSegment!!
        return tabela.altera(values!!, "${BaseColumns._ID}=?", arrayOf(id))

    }

    companion object{
        private const val AUTORIDADE = "com.azevedo.noticias"
        private const val CATEGORIAS = "categorias"
        private const val NOTICIAS = "noticias"

        private const val URI_CATEGORIAS = 100
        private const val URI_CATEGORIA_ID = 101
        private const val URI_NOTICIAS = 200
        private const val URI_NOTICIA_ID = 201

        fun uriMatcher() = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTORIDADE, CATEGORIAS, URI_CATEGORIAS)
            addURI(AUTORIDADE, "$CATEGORIAS/#", URI_CATEGORIA_ID)
            addURI(AUTORIDADE, NOTICIAS, URI_NOTICIAS)
            addURI(AUTORIDADE, "$NOTICIAS/#", URI_NOTICIA_ID)
        }
    }
}