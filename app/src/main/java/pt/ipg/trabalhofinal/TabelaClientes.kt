package pt.ipg.trabalhofinal

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaClientes( db: SQLiteDatabase): TabelaBD(db, NOME) {

   override fun cria (){

        db.execSQL("CREATE TABLE $NOME(${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $NOME_CLIENTE TEXT NOT NULL,$NIF_CLIENTE TEXT NOT NULL ,$CONTACTO TEXT NOT NULL ,$DATA_DE_NASCIMENTO TEXT NOT NULL,$CAMPO_FK_SEXO INTEGER NOT NULL,FOREIGN KEY($CAMPO_FK_SEXO) REFERENCES ${TabelaSexo.NOME} (${BaseColumns._ID}) ON DELETE RESTRICT) ")
    }

    override fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor {
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = "$NOME INNER JOIN ${TabelaSexo.NOME} ON ${TabelaSexo.CAMPO_ID} = $CAMPO_FK_SEXO"

        return queryBuilder.query(db, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object{
        const val NOME = "Clientes"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val NOME_CLIENTE = "NomeCliente"
        const val NIF_CLIENTE = "Nif"
        const val CONTACTO= "Contacto"
        const val DATA_DE_NASCIMENTO = "DatadeNascimento"
        const val CAMPO_FK_SEXO = "sexoID"
        val TODAS_COLUNAS = arrayOf(
            CAMPO_ID, NOME_CLIENTE, NIF_CLIENTE, CONTACTO,
            DATA_DE_NASCIMENTO, CAMPO_FK_SEXO,TabelaSexo.NOME)
    }



}