package pt.ipg.trabalhofinal

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaJogos( db: SQLiteDatabase): TabelaBD(db, NOME) {

   override fun cria (){

        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $NOME_JOGO TEXT NOT NULL,$PRECO REAL NOT NULL , $GENERO TEXT NOT NULL, $PUBLICADORA TEXT NOT NULL,$DATA_DE_LANCAMENTO INTEGER NOT NULL,$CAMPO_FK_PLATAFORMA INTEGER NOT NULL,FOREIGN KEY($CAMPO_FK_PLATAFORMA) REFERENCES ${TabelaPlataformas.NOME} (${BaseColumns._ID}) ON DELETE RESTRICT) ")
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
        queryBuilder.tables = "$NOME INNER JOIN  ${TabelaPlataformas.NOME} ON ${TabelaPlataformas.CAMPO_ID} = $CAMPO_FK_PLATAFORMA "

        return queryBuilder.query(db, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object{
        const val NOME = "Jogos"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val NOME_JOGO = "NomeJogo"
        const val PRECO= "Preco"
        const val GENERO= "Genero"
        const val PUBLICADORA= "Publicadora"
        const val DATA_DE_LANCAMENTO = "DatadeLancamento"
        const val CAMPO_FK_PLATAFORMA = "idPlataforma"


        val TODAS_COLUNAS = arrayOf(CAMPO_ID, NOME_JOGO, PRECO, GENERO, PUBLICADORA, DATA_DE_LANCAMENTO, CAMPO_FK_PLATAFORMA,TabelaPlataformas.NOME_PLATAFORMA)
    }



}