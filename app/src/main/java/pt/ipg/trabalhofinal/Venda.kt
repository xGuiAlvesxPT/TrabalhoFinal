package pt.ipg.trabalhofinal
import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Venda(
    var data_de_venda: String,
    var cliente: Cliente,
    var funcionario: Funcionario,
    var id: Long = -1,
) {

    fun toContentValues():ContentValues{

        val valores = ContentValues()
        valores.put(TabelaVendas.DATA_DE_VENDA,data_de_venda)
        valores.put(TabelaVendas.CAMPO_FK_CLIENTE,cliente.id)
        valores.put(TabelaVendas.CAMPO_FK_FUNCIONARIO,funcionario.id)
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Venda {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posDataVenda = cursor.getColumnIndex(TabelaVendas.DATA_DE_VENDA)
            val posIdCliente = cursor.getColumnIndex(TabelaVendas.CAMPO_FK_CLIENTE)
            val posIdFuncionario = cursor.getColumnIndex(TabelaVendas.CAMPO_FK_FUNCIONARIO)



            val posNomeCliente =  cursor.getColumnIndex(TabelaClientes.NOME_CLIENTE)
            val posNifCliente =  cursor.getColumnIndex(TabelaClientes.NIF_CLIENTE)
            val posContatoCliente =  cursor.getColumnIndex(TabelaClientes.CONTACTO_CLIENTE)
            val posDataNascCliente =  cursor.getColumnIndex(TabelaClientes.DATA_DE_NASCIMENTO_CLIENTE)
            val posSexo = cursor.getColumnIndex(TabelaClientes.CAMPO_FK_SEXO)
            val posNomeSexo =  cursor.getColumnIndex(TabelaSexo.NOME_SEXO)

            val posNomeFuncionario=  cursor.getColumnIndex(TabelaFuncionarios.NOME_FUNCIONARIO)
            val posNifFuncionario =  cursor.getColumnIndex(TabelaFuncionarios.NIF_FUNCIONARIO)
            val posContatoFuncionario =  cursor.getColumnIndex(TabelaFuncionarios.CONTACTO_FUNCIONARIO)
            val posDataNascFuncionario =  cursor.getColumnIndex(TabelaFuncionarios.DATA_DE_NASCIMENTO_FUNCIONARIO)


            val idCliente = cursor.getLong(posIdCliente)
            val nomeCliente = cursor.getString(posNomeCliente)
            val NifCliente =  cursor.getString(posNifCliente)
            val ContatoCliente =  cursor.getString(posContatoCliente)
            val DataNascCliente =  cursor.getString(posDataNascCliente)
            val sexoid = cursor.getLong(posSexo)
            val nomeSexo = cursor.getString(posNomeSexo)
            val Sexo = Sexo(nomeSexo, sexoid)

            val Cliente = Cliente(nomeCliente,NifCliente,ContatoCliente,DataNascCliente,Sexo,idCliente)

            val idFuncionario = cursor.getLong(posIdFuncionario)
            val nomeFuncionario = cursor.getString(posNomeFuncionario)
            val NifFuncionario =  cursor.getString(posNifFuncionario)
            val ContatoFuncionario =  cursor.getString(posContatoFuncionario)
            val DataNascFuncionario =  cursor.getString(posDataNascFuncionario)
            val Funcionario = Funcionario(nomeFuncionario,NifFuncionario,ContatoFuncionario,DataNascFuncionario,idFuncionario)



            val id = cursor.getLong(posId)
            val dataVenda = cursor.getString(posDataVenda)



            return Venda(dataVenda,Cliente,Funcionario, id)
        }
    }


}