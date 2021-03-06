package pt.ipg.trabalhofinal
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns


class TabelaVendas( db: SQLiteDatabase): TabelaBD(db, NOME){

   override fun cria (){

        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,$QUANTIDADE INTEGER NOT NULL,$PRECO_VENDA REAL NOT NULL, $DATA_DE_VENDA INTEGER NOT NULL,$CAMPO_FK_CLIENTE INTEGER NOT NULL,$CAMPO_FK_FUNCIONARIO INTEGER NOT NULL,$CAMPO_FK_SEXO INTEGER NOT NULL,$CAMPO_FK_JOGO INTEGER NOT NULL,$CAMPO_FK_PLATAFORMA INTEGER NOT NULL,FOREIGN KEY($CAMPO_FK_CLIENTE) REFERENCES ${TabelaClientes.NOME} (${BaseColumns._ID}) ON DELETE RESTRICT,FOREIGN KEY($CAMPO_FK_FUNCIONARIO) REFERENCES ${TabelaFuncionarios.NOME} (${BaseColumns._ID}) ON DELETE RESTRICT,FOREIGN KEY($CAMPO_FK_SEXO) REFERENCES ${TabelaSexo.NOME} (${BaseColumns._ID}) ON DELETE RESTRICT,FOREIGN KEY($CAMPO_FK_JOGO) REFERENCES ${TabelaJogos.NOME} (${BaseColumns._ID}) ON DELETE RESTRICT,FOREIGN KEY($CAMPO_FK_PLATAFORMA) REFERENCES ${TabelaPlataformas.NOME} (${BaseColumns._ID}) ON DELETE RESTRICT) ")
    }

    override fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor {
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = "$NOME INNER JOIN ${TabelaFuncionarios.NOME} ON ${TabelaFuncionarios.CAMPO_ID} = $CAMPO_FK_FUNCIONARIO INNER JOIN ${TabelaClientes.NOME} ON ${TabelaClientes.CAMPO_ID} = $CAMPO_FK_CLIENTE INNER JOIN ${TabelaSexo.NOME} ON ${TabelaSexo.CAMPO_ID} = $CAMPO_FK_SEXO INNER JOIN ${TabelaJogos.NOME} ON ${TabelaJogos.CAMPO_ID} = $CAMPO_FK_JOGO INNER JOIN ${TabelaPlataformas.NOME} ON ${TabelaPlataformas.CAMPO_ID} = $CAMPO_FK_PLATAFORMA"

        return queryBuilder.query(db, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object{

        const val NOME = "Vendas"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val PRECO_VENDA = "PrecoVenda"
        const val QUANTIDADE = "Quantidade"
        const val DATA_DE_VENDA = "DatadeVenda"
        const val CAMPO_FK_CLIENTE = "idCliente"
        const val CAMPO_FK_FUNCIONARIO = "idFuncionario"
        const val CAMPO_FK_SEXO = "idSexo"
        const val CAMPO_FK_JOGO = "idJogo"
        const val CAMPO_FK_PLATAFORMA = "idPlataforma"
        val TODAS_COLUNAS = arrayOf(CAMPO_ID,
            QUANTIDADE,
            PRECO_VENDA, DATA_DE_VENDA, CAMPO_FK_CLIENTE, CAMPO_FK_FUNCIONARIO,
            CAMPO_FK_JOGO, TabelaClientes.NOME_CLIENTE,TabelaClientes.NIF_CLIENTE,TabelaClientes.CONTACTO_CLIENTE,TabelaClientes.DATA_DE_NASCIMENTO_CLIENTE,TabelaClientes.CAMPO_FK_SEXO,TabelaSexo.NOME_SEXO,TabelaFuncionarios.NOME_FUNCIONARIO,TabelaFuncionarios.NIF_FUNCIONARIO,TabelaFuncionarios.CONTACTO_FUNCIONARIO,TabelaFuncionarios.DATA_DE_NASCIMENTO_FUNCIONARIO,TabelaPlataformas.NOME_PLATAFORMA,TabelaJogos.NOME_JOGO,TabelaJogos.CAMPO_FK_PLATAFORMA,TabelaJogos.GENERO,TabelaJogos.PUBLICADORA,TabelaJogos.DATA_DE_LANCAMENTO,TabelaJogos.PRECO)
    }


}