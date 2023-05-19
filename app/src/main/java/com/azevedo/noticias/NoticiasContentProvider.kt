package com.azevedo.noticias

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

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
        TODO("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    companion object{
        private const val AUTORIDADE = "com.azevedo.noticias"
        private const val CATEGORIAS = "categorias"
        private const val NOTICIAS = "noticias"

        private const val URI_CATEGORIAS = 100
        private const val URI_NOTICIAS = 200

        fun uriMatcher() = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTORIDADE, CATEGORIAS, URI_CATEGORIAS)
            addURI(AUTORIDADE, NOTICIAS, URI_NOTICIAS)
        }
    }
}