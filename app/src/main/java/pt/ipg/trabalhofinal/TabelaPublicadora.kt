package pt.ipg.trabalhofinal

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaPublicadora( db: SQLiteDatabase): TabelaBD(db, TabelaPlataformas.NOME)  {

    override fun cria (){

        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $NOME_PUBLICADORA TEXT NOT NULL) ")
    }

    companion object{
        const val NOME = "Publicadora"
        const val NOME_PUBLICADORA = "Nome"
        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, NOME_PUBLICADORA)

    }

}