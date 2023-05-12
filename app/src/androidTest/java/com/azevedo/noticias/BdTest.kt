package com.azevedo.noticias

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BdTest {

    private fun getAppContex(): Context =
        InstrumentationRegistry.getInstrumentation().targetContext


    @Before
    fun apagaBD(){
        getAppContex().deleteDatabase(BdNoticiasOpenHelper.NOME_BASE_DADOS)
    }

    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BdNoticiasOpenHelper(getAppContex())
        return openHelper.writableDatabase
    }
    private fun inserirCategoria(
        bd: SQLiteDatabase,
        categoria: Categoria
    ) {
        categoria.id = Tabela_Categorias(bd).insere(categoria.toContentVaules())
        assertNotEquals(-1, categoria.id)
    }

    private fun insereNoticia(
        bd: SQLiteDatabase,
        noticia: Noticias
    ) {
        noticia.id = Tabela_Noticias(bd).insere(noticia.toContentValues())
        assertNotEquals(-1, noticia.id)
    }


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = getAppContex()
        assertEquals("com.azevedo.noticias", appContext.packageName)
    }

    @Test
    fun consegueAbrirBD(){
        val openHelper = BdNoticiasOpenHelper(getAppContex())
        val bd = openHelper.readableDatabase
            assert((bd.isOpen))
    }

    @Test
    fun consegueInserirCategorias(){
        val bd = getWritableDatabase()

        val categoria = Categoria("Economia","As noticias de economia")
        inserirCategoria(bd, categoria)
    }



    @Test
    fun consegueInserirNoticia(){
        val bd = getWritableDatabase()

        val categoria = Categoria("Politica","As noticias de politica")
        inserirCategoria(bd, categoria)

        val noticia1 = Noticias("Marcelo dissolve assembleia",categoria.id,"12/05/2022")
        insereNoticia(bd, noticia1)

        val noticia2 = Noticias("Ana Gomes:Este governo também está a fabricar populismos",categoria.id,"11/05/2023")
        insereNoticia(bd, noticia2)
    }

    @Test
    fun consegueLerCategoria(){
        val bd = getWritableDatabase()

        val categAtualidade = Categoria("Atualidade","As noticias da atualidade")
        inserirCategoria(bd, categAtualidade)

        val categGuerra = Categoria("Guerra","As noticias da Guerra")
        inserirCategoria(bd, categGuerra)

        val tabelaCategorias = Tabela_Categorias(bd)
        val cursor = tabelaCategorias.consulta(Tabela_Categorias.CAMPOS, "${BaseColumns._ID}=?", arrayOf(categGuerra.id.toString()),null,null,null)

        assert(cursor.moveToNext())

        val categBD = Categoria.formCursor(cursor)

        assertEquals(categGuerra, categBD)

        val cursorTodasCategorias = tabelaCategorias.consulta(
            Tabela_Categorias.CAMPOS,
            null, null,null,null,
            Tabela_Categorias.CAMPO_NOME
        )
            assert(cursorTodasCategorias.count > 1)

    }



}