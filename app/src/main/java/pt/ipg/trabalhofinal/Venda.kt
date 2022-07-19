package pt.ipg.trabalhofinal
import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable
import java.util.*

data class Venda(
    var quantidade: Long,
    var precoVenda: Double,
    var data_de_venda: Long,
    var cliente: Cliente,
    var funcionario: Funcionario,
    var sexo : Sexo,
    var jogo: Jogo,
    var plataforma: Plataforma,

    var id: Long = -1,
): Serializable {

    fun toContentValues():ContentValues{

        val valores = ContentValues()
        valores.put(TabelaVendas.QUANTIDADE,quantidade)
        valores.put(TabelaVendas.PRECO_VENDA,precoVenda)
        valores.put(TabelaVendas.DATA_DE_VENDA,data_de_venda)
        valores.put(TabelaVendas.CAMPO_FK_CLIENTE,cliente.id)
        valores.put(TabelaVendas.CAMPO_FK_FUNCIONARIO,funcionario.id)
        valores.put(TabelaVendas.CAMPO_FK_SEXO,sexo.id)
        valores.put(TabelaVendas.CAMPO_FK_JOGO,jogo.id)
        valores.put(TabelaVendas.CAMPO_FK_PLATAFORMA,plataforma.id)
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Venda {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posDataVenda = cursor.getColumnIndex(TabelaVendas.DATA_DE_VENDA)
            val posQuantidade = cursor.getColumnIndex(TabelaVendas.QUANTIDADE)
            val posPrecoVenda = cursor.getColumnIndex(TabelaVendas.PRECO_VENDA)
            val posIdCliente = cursor.getColumnIndex(TabelaVendas.CAMPO_FK_CLIENTE)
            val posIdFuncionario = cursor.getColumnIndex(TabelaVendas.CAMPO_FK_FUNCIONARIO)
            val posIdJogo = cursor.getColumnIndex(TabelaVendas.CAMPO_FK_JOGO)



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

            val posNomeJogo =  cursor.getColumnIndex(TabelaJogos.NOME_JOGO)
            val posGeneroJogo =  cursor.getColumnIndex(TabelaJogos.GENERO)
            val posPublicadoraJogo =  cursor.getColumnIndex(TabelaJogos.PUBLICADORA)
            val posPrecoJogo =  cursor.getColumnIndex(TabelaJogos.PRECO)
            val posDataLancJogo =  cursor.getColumnIndex(TabelaJogos.DATA_DE_LANCAMENTO)
            val posPlataformaJogo =  cursor.getColumnIndex(TabelaJogos.CAMPO_FK_PLATAFORMA)
            val posNomePlataforma =  cursor.getColumnIndex(TabelaPlataformas.NOME_PLATAFORMA)



            val idCliente = cursor.getLong(posIdCliente)
            val nomeCliente = cursor.getString(posNomeCliente)
            val NifCliente =  cursor.getString(posNifCliente)
            val ContatoCliente =  cursor.getString(posContatoCliente)
            val DataNascCliente =  cursor.getLong(posDataNascCliente)
            val sexoid = cursor.getLong(posSexo)
            val nomeSexo = cursor.getString(posNomeSexo)
            val Sexo = Sexo(nomeSexo, sexoid)

            val Cliente = Cliente(nomeCliente,NifCliente,ContatoCliente,DataNascCliente,Sexo,idCliente)

            val idFuncionario = cursor.getLong(posIdFuncionario)
            val nomeFuncionario = cursor.getString(posNomeFuncionario)
            val NifFuncionario =  cursor.getString(posNifFuncionario)
            val ContatoFuncionario =  cursor.getString(posContatoFuncionario)
            val DataNascFuncionario =  cursor.getLong(posDataNascFuncionario)

            val Funcionario = Funcionario(nomeFuncionario,NifFuncionario,ContatoFuncionario,DataNascFuncionario,idFuncionario)

            val idJogo = cursor.getLong(posIdJogo)
            val nomeJogo = cursor.getString(posNomeJogo)
            val precoJogo = cursor.getDouble(posPrecoJogo)
            val genero = cursor.getString(posGeneroJogo)
            val publicadora = cursor.getString(posPublicadoraJogo)
            val dataLancamento = cursor.getLong(posDataLancJogo)

            val idPlataforma = cursor.getLong(posPlataformaJogo)
            val nomePlataforma = cursor.getString(posNomePlataforma)
            val Plataforma = Plataforma(nomePlataforma, idPlataforma)

            val Jogo = Jogo(nomeJogo,precoJogo,genero,publicadora,dataLancamento,Plataforma,idJogo)

            val id = cursor.getLong(posId)
            val dataVenda = cursor.getLong(posDataVenda)
            val quantidade = cursor.getLong(posQuantidade)
            val precoVenda = cursor.getDouble(posPrecoVenda)



            return Venda(quantidade,precoVenda,dataVenda,Cliente,Funcionario,Sexo,Jogo,Plataforma, id)
        }
    }


}