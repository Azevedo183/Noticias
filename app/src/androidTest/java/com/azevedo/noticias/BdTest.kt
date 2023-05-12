package com.azevedo.noticias

import android.content.Context
import android.database.sqlite.SQLiteDatabase
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

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = getAppContex()
        assertEquals("com.azevedo.noticias", appContext.packageName)
    }

    fun consegueAbrirBD(){
        val openHelper = BdNoticiasOpenHelper(getAppContex())
        val bd = openHelper.readableDatabase
            assert((bd.isOpen))
    }

    @Test
    fun consegueInserirCategorias(){
        val openHelper = BdNoticiasOpenHelper(getAppContex())
        val bd = openHelper.writableDatabase

        val categoria = Categoria("Economia")
        Tabela_Categorias(bd).insere(categoria.toContentVaules())
        assertNotEquals(-1, id)
    }

}