package pt.ipg.trabalhofinal

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaGeneros( db: SQLiteDatabase): TabelaBD(db, NOME) {

    override fun cria (){

        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $NOME_GENERO TEXT NOT NULL) ")
    }

    companion object{
        const val NOME = "Genero"
        const val NOME_GENERO = "Nome"
        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, NOME_GENERO)

    }


}