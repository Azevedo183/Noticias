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
import java.util.Calendar


@RunWith(AndroidJUnit4::class)
class BdTest {

    private fun getAppContex(): Context =
        InstrumentationRegistry.getInstrumentation().targetContext


    @Before
    fun apagaBD(){
        //getAppContex().deleteDatabase(BdNoticiasOpenHelper.NOME_BASE_DADOS)
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

        val data1 = Calendar.getInstance()
        data1.set(2021, 12, 5)
        val noticia1 = Noticias("Marcelo dissolve assembleia",categoria,data1)
        insereNoticia(bd, noticia1)

        val data2 = Calendar.getInstance()
        data2.set(2023, 5, 12)
        val noticia2 = Noticias("Ana Gomes:Este governo também está a fabricar populismos",categoria,data2)
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
        val cursor = tabelaCategorias.consulta(Tabela_Categorias.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(categGuerra.id.toString()),
            null,null,null)

        assert(cursor.moveToNext())

        val categBD = Categoria.formCursor(cursor)

        assertEquals(categGuerra, categBD)

        val cursorTodasCategorias = tabelaCategorias.consulta(
            Tabela_Categorias. CAMPOS,
            null, null,null,null,
            Tabela_Categorias.CAMPO_NOME
        )
            assert(cursorTodasCategorias.count > 1)

    }

    @Test
    fun consegueLerNoticia(){
        val bd = getWritableDatabase()

        val categoria = Categoria("Notícias Locais","Notícias sobre eventos que ocorrem em uma cidade, estado ou região específica")
        inserirCategoria(bd, categoria)

        val data1 = Calendar.getInstance()
        data1.set(2022, 10, 19)
        val noticia1 = Noticias("Câmara de Lamego reforça programa de incentivo à natalidade",categoria,data1)
        insereNoticia(bd, noticia1)

        val data2 = Calendar.getInstance()
        data2.set(2022, 10, 12)
        val noticia2 = Noticias("Lamego vai ter residência universitaria com 46 camas",categoria,data2)
        insereNoticia(bd, noticia2)

        val tabelaNoticias = Tabela_Noticias(bd)

        val cursor = tabelaNoticias.consulta(
            Tabela_Noticias.CAMPOS,
            "${Tabela_Noticias.CAMPO_ID}=?",
            arrayOf(noticia1.id.toString()),
            null,null,null)

        assert(cursor.moveToNext())

        val noticiaBD = Noticias.formCursor(cursor)

        assertEquals(noticia1, noticiaBD)

        val cursorTodasNoticias = tabelaNoticias.consulta(
            Tabela_Noticias.CAMPOS,
            null,null, null, null,
            Tabela_Noticias.CAMPO_TITULO
        )

        assert(cursorTodasNoticias.count > 1)
    }

    @Test
    fun consegueAlterarCategorias(){
        val bd = getWritableDatabase()

        val categoria = Categoria("Desporto","Notícias sobre futebol, basquete, tênis, automobilismo, esportes olímpicos e outros eventos esportivos relevantes.")
        inserirCategoria(bd, categoria)

        categoria.nome = "Saúde"
        categoria.descricao = "Notícias sobre saúde pública, descobertas científicas, avanços médicos, doenças, cuidados de saúde e bem-estar"

        val registoAlterados = Tabela_Categorias(bd).altera(
            categoria.toContentVaules(),
            "${BaseColumns._ID}=?",
            arrayOf(categoria.id.toString()),
        )

        assertEquals(1, registoAlterados)
    }

    @Test
    fun consegueAlterarNoticia(){
        val bd = getWritableDatabase()

        val categTec = Categoria("Tecnologia","Notícias sobre inovações tecnológicas, lançamentos de produtos, empresas de tecnologia, aplicativos, gadgets e tendências digitais")
        inserirCategoria(bd, categTec)

        val categCen = Categoria("Ciência","Notícias sobre avanços científicos, pesquisas, descobertas espaciais, meio ambiente, biologia e outros campos científicos")
        inserirCategoria(bd, categCen)

        val data = Calendar.getInstance()
        data.set(2023, 5, 19)
        val noticia = Noticias("Wall Street fecha em alta graças à tecnologia e inteligência artifical",categTec,data)
        insereNoticia(bd, noticia)

        noticia.categoria = categCen
        noticia.titulo = "Português quer ajudar China a procurar vida em Marte a partir de salinas lusófonas"
        val datanew = Calendar.getInstance()
        data.set(2023, 5, 14)
        noticia.data = datanew

        val registoAlterados = Tabela_Noticias(bd).altera(
            noticia.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(noticia.id.toString()),
        )

        assertEquals(1, registoAlterados)

    }

    @Test
    fun consegueEliminarCategoria(){
        val bd = getWritableDatabase()

        val categoria = Categoria("...","...")
        inserirCategoria(bd, categoria)

        val registoEliminado = Tabela_Categorias(bd).elimina(
            "${BaseColumns._ID}=?",
            arrayOf(categoria.id.toString())
        )

        assertEquals(1, registoEliminado)
    }

    @Test
    fun consegueEliminarNoticia(){
        val bd = getWritableDatabase()

        val categTec = Categoria("Educação","Notícias sobre educação, reformas educacionais, instituições de ensino, eventos educacionais e tendências educacionais")
        inserirCategoria(bd, categTec)

        val data = Calendar.getInstance()
        data.set(2023, 5, 16)
        val noticia = Noticias("Ordem para marcação de faltas injustificadas? Não é verdade",categTec,data)
        insereNoticia(bd, noticia)


        val registoApagados = Tabela_Noticias(bd).elimina(
            "${BaseColumns._ID}=?",
            arrayOf(noticia.id.toString()),
        )

        assertEquals(1, registoApagados)

    }

    @Test
    fun consegueInserirDefaultCategorias() {
        val bd = getWritableDatabase()

        val categorias = listOf(
            Categoria("Política", "Notícias relacionadas a governos, partidos políticos, eleições, políticas públicas, diplomacia internacional e tomadas de decisão políticas em geral."),
            Categoria("Economia", "Notícias sobre economia envolvendo mercados financeiros, indicadores econômicos (como taxa de desemprego, inflação, PIB), comércio internacional, políticas econômicas, empresas e empreendedorismo."),
            Categoria("Internacional", "Notícias relacionadas a eventos e desenvolvimentos em outros países, como conflitos, acordos internacionais, crises humanitárias, questões diplomáticas e relações internacionais em geral."),
            Categoria("Ciência e Tecnologia", "Notícias sobre avanços científicos, descobertas, pesquisas, inovações tecnológicas, exploração espacial, saúde, meio ambiente, novas tecnologias e seus impactos na sociedade."),
            Categoria("Esportes", "Notícias sobre diferentes modalidades esportivas, resultados de competições, eventos esportivos, transferências de jogadores, análises de desempenho, entrevistas com atletas e histórias relacionadas ao mundo dos esportes."),
            Categoria("Entretenimento", "Notícias relacionadas ao entretenimento, incluindo cinema, televisão, música, celebridades, premiações, lançamentos de filmes e séries, eventos culturais, moda e outros aspectos da indústria do entretenimento."),
            Categoria("Saúde", "Notícias sobre saúde pública, avanços médicos, pesquisas sobre doenças, tratamentos, políticas de saúde, bem-estar, nutrição e dicas relacionadas à saúde física e mental."),
            Categoria("Educação", "Notícias sobre educação englobando políticas educacionais, reformas, avanços pedagógicos, eventos e tendências na área da educação, educação à distância, bolsas de estudo e oportunidades educacionais."),
            Categoria("Meio Ambiente", "Notícias relacionadas a questões ambientais, mudanças climáticas, conservação, desastres naturais, energia sustentável, políticas ambientais e esforços para preservar o meio ambiente."),
            Categoria("Sociedade", "Notícias sobre a sociedade, abrangendo uma variedade de tópicos como direitos humanos, diversidade, desigualdade social, questões de gênero, imigração, criminalidade, mudanças culturais e outros assuntos que afetam a sociedade como um todo.")
        )

        for (categoria in categorias) {
            inserirCategoria(bd, categoria)
        }
    }

}