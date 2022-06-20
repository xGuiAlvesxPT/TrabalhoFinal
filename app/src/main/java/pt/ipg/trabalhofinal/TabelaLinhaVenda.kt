package pt.ipg.trabalhofinal
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaLinhaVenda( db: SQLiteDatabase): TabelaBD(db, NOME) {

    override fun cria (){

        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $QUANTIDADE INTEGER NOT NULL,$PRECO REAL NOT NULL,$CAMPO_FK_JOGOS INTEGER NOT NULL,$CAMPO_FK_VENDA INTEGER NOT NULL,FOREIGN KEY ($CAMPO_FK_JOGOS) REFERENCES ${TabelaJogos.NOME} (${BaseColumns._ID}) ON DELETE RESTRICT,FOREIGN KEY($CAMPO_FK_VENDA) REFERENCES ${TabelaVendas.NOME} (${BaseColumns._ID}) ON DELETE RESTRICT) ")
    }

    companion object{
        const val NOME = "LinhaDeVenda"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val QUANTIDADE = "Quantidade"
        const val PRECO = "Preco"
        const val CAMPO_FK_VENDA = "idVenda"
        const val CAMPO_FK_JOGOS = "idJogo"
        val TODAS_COLUNAS = arrayOf(CAMPO_ID, QUANTIDADE, PRECO, CAMPO_FK_VENDA, CAMPO_FK_JOGOS)
    }



}