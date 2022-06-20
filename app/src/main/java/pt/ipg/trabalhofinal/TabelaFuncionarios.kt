package pt.ipg.trabalhofinal


import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaFuncionarios( db: SQLiteDatabase): TabelaBD(db, NOME) {

   override fun cria (){

        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,$NOME_FUNCIONARIO TEXT NOT NULL,$NIF_FUNCIONARIO TEXT NOT NULL ,$CONTACTO TEXT NOT NULL ,$DATA_DE_NASCIMENTO INTEGER NOT NULL) ")
    }

    override fun query(
        columns: Array<String>,
        selection: String,
        selectionArgs: Array<String>,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor? {
        val queryBuilder = SQLiteQueryBuilder()


        return queryBuilder.query(db, columns, selection, selectionArgs, groupBy, having, orderBy)
    }




    companion object{
        const val NOME = "Funcionarios"
        const val NOME_FUNCIONARIO = "Nome"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val NIF_FUNCIONARIO = "Nif"
        const val CONTACTO= "Contacto"
        const val DATA_DE_NASCIMENTO = "DatadeNascimento"
        val TODAS_COLUNAS = arrayOf(
            CAMPO_ID, NIF_FUNCIONARIO, NIF_FUNCIONARIO, CONTACTO,
            DATA_DE_NASCIMENTO)
    }



}