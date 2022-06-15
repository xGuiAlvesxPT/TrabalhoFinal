package pt.ipg.trabalhofinal

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaPlataformas( db: SQLiteDatabase): TabelaBD(db, NOME) {

   override fun cria (){

        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $NOME_PLATAFORMA TEXT NOT NULL) ")
    }

    companion object{
        const val NOME = "Plataformas"
        const val NOME_PLATAFORMA = "Nome"
        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, NOME_PLATAFORMA)

    }



}