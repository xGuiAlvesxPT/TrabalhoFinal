package pt.ipg.trabalhofinal

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaSexo ( db: SQLiteDatabase): TabelaBD(db, TabelaPlataformas.NOME) {

    override fun cria (){

        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $NOME_SEXO TEXT NOT NULL) ")
        db.execSQL("INSERT INTO $NOME ($NOME_SEXO) VALUES ('Masculino')")
        db.execSQL("INSERT INTO $NOME ($NOME_SEXO) VALUES ('Feminino')")
    }

    companion object{
        const val NOME = "Sexo"
        const val NOME_SEXO = "Nome"
        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, NOME_SEXO)

    }


}