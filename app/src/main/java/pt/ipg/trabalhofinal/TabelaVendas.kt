package pt.ipg.trabalhofinal
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


class TabelaVendas( db: SQLiteDatabase): TabelaBD(db, NOME){

   override fun cria (){

        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $DATA_DE_VENDA TEXT NOT NULL,$CAMPO_FK_CLIENTE INTEGER NOT NULL,$CAMPO_FK_FUNCIONARIO INTEGER NOT NULL,FOREIGN KEY($CAMPO_FK_CLIENTE) REFERENCES ${TabelaClientes.NOME} (${BaseColumns._ID}) ON DELETE RESTRICT,FOREIGN KEY($CAMPO_FK_FUNCIONARIO) REFERENCES ${TabelaFuncionarios.NOME} (${BaseColumns._ID}) ON DELETE RESTRICT) ")
    }

    companion object{

        const val NOME = "Vendas"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val DATA_DE_VENDA = "DatadeVenda"
        const val CAMPO_FK_CLIENTE = "idCliente"
        const val CAMPO_FK_FUNCIONARIO = "idFuncionario"
        val TODAS_COLUNAS = arrayOf(CAMPO_ID, DATA_DE_VENDA, CAMPO_FK_CLIENTE, CAMPO_FK_FUNCIONARIO)
    }


}