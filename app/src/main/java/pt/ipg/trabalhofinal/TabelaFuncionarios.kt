package pt.ipg.trabalhofinal



import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaFuncionarios( db: SQLiteDatabase): TabelaBD(db, NOME) {

   override fun cria (){

        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,$NOME_FUNCIONARIO TEXT NOT NULL,$NIF_FUNCIONARIO TEXT NOT NULL ,$CONTACTO_FUNCIONARIO TEXT NOT NULL ,$DATA_DE_NASCIMENTO_FUNCIONARIO INTEGER NOT NULL) ")

   }


    companion object{
        const val NOME = "Funcionarios"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val NOME_FUNCIONARIO = "NomeFuncionarios"
        const val NIF_FUNCIONARIO = "NifFuncionario"
        const val CONTACTO_FUNCIONARIO= "ContactoFuncionario"
        const val DATA_DE_NASCIMENTO_FUNCIONARIO = "DatadeNascimentoFuncionario"
        val TODAS_COLUNAS = arrayOf(
            CAMPO_ID, NOME_FUNCIONARIO,NIF_FUNCIONARIO, CONTACTO_FUNCIONARIO,
            DATA_DE_NASCIMENTO_FUNCIONARIO)
    }



}