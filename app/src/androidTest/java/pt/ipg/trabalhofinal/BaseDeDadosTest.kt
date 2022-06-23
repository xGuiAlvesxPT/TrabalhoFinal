package pt.ipg.trabalhofinal

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BaseDeDadosTest {

    // @Test
    //fun useAppContext() {
    // Context of the app under test.
    //val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    // assertEquals("pt.ipg.trabalhofinal", appContext.packageName)
    // }

    private fun appContext() =
        InstrumentationRegistry.getInstrumentation().targetContext


    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BDLojaOpenHelper(appContext())
        return openHelper.writableDatabase
    }

    private fun insereGenero(db: SQLiteDatabase, Genero: Genero) {
        Genero.id = TabelaGeneros(db).insert(Genero.toContentValues())
        assertNotEquals(-1, Genero.id)
    }

    private fun inserePlataforma(db: SQLiteDatabase, Plataforma: Plataforma) {
        Plataforma.id = TabelaPlataformas(db).insert(Plataforma.toContentValues())
        assertNotEquals(-1, Plataforma.id)
    }

    private fun insereSexo(db: SQLiteDatabase, Sexo: Sexo) {
        Sexo.id = TabelaSexo(db).insert(Sexo.toContentValues())
        assertNotEquals(-1, Sexo.id)
    }

    private fun insereCliente(db: SQLiteDatabase, Cliente: Cliente) {
        Cliente.id = TabelaClientes(db).insert(Cliente.toContentValues())
        assertNotEquals(-1, Cliente.id)
    }

    private fun insereJogo(db: SQLiteDatabase, Jogo: Jogo) {
        Jogo.id = TabelaJogos(db).insert(Jogo.toContentValues())
        assertNotEquals(-1, Jogo.id)
    }

    private fun insereFuncionario(db: SQLiteDatabase, Funcionario: Funcionario) {
        Funcionario.id = TabelaFuncionarios(db).insert(Funcionario.toContentValues())
        assertNotEquals(-1, Funcionario.id)
    }

    private fun insereVenda(db: SQLiteDatabase, Venda: Venda) {
        Venda.id = TabelaVendas(db).insert(Venda.toContentValues())
        assertNotEquals(-1, Venda.id)
    }

    private fun insereLinhaVenda(db: SQLiteDatabase, LinhaVenda: LinhaVenda) {
        LinhaVenda.id = TabelaLinhaVenda(db).insert(LinhaVenda.toContentValues())
        assertNotEquals(-1, LinhaVenda.id)
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Before
    fun apagaBaseDados() {
        //appContext().deleteDatabase(BDLojaOpenHelper.NOME)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val openHelper = BDLojaOpenHelper(appContext())
        val db = openHelper.readableDatabase

        assertTrue(db.isOpen)

        db.close()
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    fun consegueInserirGenero() {
        val db = getWritableDatabase()

        insereGenero(db, Genero("Aventura"))
        insereGenero(db, Genero("Açao"))
        insereGenero(db, Genero("Estrategia"))

        db.close()
    }

    @Test
    fun consegueInserirPlataforma() {
        val db = getWritableDatabase()

        inserePlataforma(db, Plataforma("Playstation 3"))
        inserePlataforma(db, Plataforma("Xbox 360"))
        inserePlataforma(db, Plataforma("Nintendo 3DS"))

        db.close()
    }

    @Test
    fun consegueInserirSexo() {
        val db = getWritableDatabase()

        insereSexo(db, Sexo("Masculino"))
        insereSexo(db, Sexo("Feminino"))

        db.close()
    }

    @Test
    fun consegueInserirCliente() {
        val db = getWritableDatabase()

        val sexoM = Sexo("Masculino")
        insereSexo(db,sexoM)
        val sexoF = Sexo("Feminino")
        insereSexo(db,sexoF)

        insereCliente(db, Cliente("Guilherme Alves","250116278","963355065","10/10/1985",sexoM.id))
        insereCliente(db, Cliente("Maria Almeida","258524687","954798855","15/02/1998",sexoF.id))
        insereCliente(db, Cliente("Joao Pires","254566278","9745354789","01/05/2010",sexoM.id))

        db.close()
    }

    @Test
    fun consegueInserirJogo() {
        val db = getWritableDatabase()

        val jogo1 = Jogo("Grand Theft Auto 5",30.99F,"24/11/2013",1,2,1)
        insereJogo(db, jogo1)

        val jogo2 = Jogo("Halo Wars",39.99F,"06/02/2011",2,3,2)
        insereJogo(db, jogo2)

        val jogo3 = Jogo("Legend Of Zelda",59.99F,"09/03/2015",3,1,3)
        insereJogo(db, jogo3)

        db.close()
    }

    @Test
    fun consegueInserirFuncionario() {
        val db = getWritableDatabase()

        insereFuncionario(db, Funcionario("Jacinto Alves","548625789","150116278","12/12/2005"))
        insereFuncionario(db, Funcionario("André Almeida","247954869","248524687","30/07/1970"))
       insereFuncionario(db, Funcionario("Pedro Pires","789546578","254565278","10/10/1974"))

        db.close()
    }

    @Test
    fun consegueInserirVenda() {
        val db = getWritableDatabase()

        insereVenda(db, Venda("11/07/2022",1,1))
        insereVenda(db, Venda("15/08/2021",2,2))
        insereVenda(db, Venda("16/09/2020",3,3))

        db.close()
    }

    @Test
    fun consegueInserirLinhaVenda() {
        val db = getWritableDatabase()

        insereLinhaVenda(db, LinhaVenda(2,61.98F,1,1))
        insereLinhaVenda(db, LinhaVenda(3,119.97F,2,2))
        insereLinhaVenda(db, LinhaVenda(4,239.96F,3,3))

        db.close()
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Test
    fun consegueAlterarPlataforma() {
        val db = getWritableDatabase()

        val plataforma = Plataforma("Playstation 2")
        inserePlataforma(db, plataforma)

        plataforma.nome = "Xbox One"

        val registosAlterados = TabelaPlataformas(db).update(
            plataforma.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf("${plataforma.id}"))

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test
    fun consegueEliminarPlataforma() {
        val db = getWritableDatabase()

        val plataforma = Plataforma("Xbox 360")
        inserePlataforma(db, plataforma)

        val registosEliminados = TabelaPlataformas(db).delete(
            "${BaseColumns._ID}=?",
            arrayOf("${plataforma.id}"))

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerPlataformas() {
        val db = getWritableDatabase()

        val plataforma = Plataforma("PSP")
        inserePlataforma(db, plataforma)

        val cursor = TabelaPlataformas(db).query(
            TabelaPlataformas.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf("${plataforma.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val plataformaBD = Plataforma.fromCursor(cursor)

        assertEquals(plataforma,plataformaBD)

        db.close()
    }

    @Test
    fun consegueLerFuncionario() {
        val db = getWritableDatabase()

        val funcionario = Funcionario("Pedro Pires","789546578","254565278","01/04/2000")
        insereFuncionario(db, funcionario)

    val cursor = TabelaFuncionarios(db).query(
            TabelaFuncionarios.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf("${funcionario.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val funcionarioBD = Funcionario.fromCursor(cursor)

        assertEquals(funcionario,funcionarioBD)

        db.close()
    }

}