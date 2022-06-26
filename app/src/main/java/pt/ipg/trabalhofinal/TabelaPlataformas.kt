package pt.ipg.trabalhofinal

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaPlataformas( db: SQLiteDatabase): TabelaBD(db, NOME) {

   override fun cria (){

        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $NOME_PLATAFORMA TEXT NOT NULL) ")
        db.execSQL("INSERT INTO $NOME ($NOME_PLATAFORMA) VALUES ('Playstation 4')")
        db.execSQL("INSERT INTO $NOME ($NOME_PLATAFORMA) VALUES ('Xbox 360')")
        db.execSQL("INSERT INTO $NOME ($NOME_PLATAFORMA) VALUES ('Playstation 5')")
    }

    companion object{
        const val NOME = "Plataformas"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val NOME_PLATAFORMA = "Nome"
        val TODAS_COLUNAS = arrayOf(CAMPO_ID, NOME_PLATAFORMA)

    }



}