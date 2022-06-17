package pt.ipg.trabalhofinal

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaClientes( db: SQLiteDatabase): TabelaBD(db, NOME) {

   override fun cria (){

        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $NOME_CLIENTE TEXT NOT NULL,$NIF_CLIENTE TEXT NOT NULL ,$CONTACTO TEXT NOT NULL ,$DATA_DE_NASCIMENTO INTEGER NOT NULL,$CAMPO_FK_SEXO INTEGER NOT NULL,FOREIGN KEY($CAMPO_FK_SEXO) REFERENCES ${TabelaSexo.NOME} (${BaseColumns._ID}) ON DELETE RESTRICT) ")
    }

    companion object{
        const val NOME = "Clientes"
        const val NOME_CLIENTE = "Nome"
        const val NIF_CLIENTE = "Nif"
        const val CONTACTO= "Contacto"
        const val DATA_DE_NASCIMENTO = "DatadeNascimento"
        const val CAMPO_FK_SEXO = "idSexo"
        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, NOME_CLIENTE, NIF_CLIENTE, CONTACTO,
            DATA_DE_NASCIMENTO, CAMPO_FK_SEXO)
    }



}