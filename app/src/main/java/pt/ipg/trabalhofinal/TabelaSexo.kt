package pt.ipg.trabalhofinal

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaSexo ( db: SQLiteDatabase): TabelaBD(db, NOME) {

    override fun cria (){

        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $NOME_SEXO TEXT NOT NULL) ")

    }

    companion object{
        const val NOME = "Sexo"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val NOME_SEXO = "NomeSexo"
        val TODAS_COLUNAS = arrayOf(CAMPO_ID, NOME_SEXO)

    }


}