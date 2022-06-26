package pt.ipg.trabalhofinal

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaPublicadora( db: SQLiteDatabase): TabelaBD(db, NOME)  {

    override fun cria (){

        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $NOME_PUBLICADORA TEXT NOT NULL) ")
        db.execSQL("INSERT INTO $NOME ($NOME_PUBLICADORA) VALUES ('Take Two')")
        db.execSQL("INSERT INTO $NOME ($NOME_PUBLICADORA) VALUES ('Ubisoft')")
        db.execSQL("INSERT INTO $NOME ($NOME_PUBLICADORA) VALUES ('Square Enix')")

    }

    companion object{
        const val NOME = "Publicadora"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val NOME_PUBLICADORA = "NomePublicadora"
        val TODAS_COLUNAS = arrayOf(CAMPO_ID, NOME_PUBLICADORA)

    }

}