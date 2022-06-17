package pt.ipg.trabalhofinal
import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Venda(
    var data_de_venda: Date,
    var idcliente: Long,
    var idfuncionario: Long,
    var id: Long = -1,
) {

    fun toContentValues():ContentValues{

        val valores = ContentValues()
        valores.put(TabelaVendas.DATA_DE_VENDA,data_de_venda.toString())
        valores.put(TabelaVendas.CAMPO_FK_CLIENTE,idcliente)
        valores.put(TabelaVendas.CAMPO_FK_FUNCIONARIO,idfuncionario)
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Venda {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posDataVenda = cursor.getColumnIndex(TabelaVendas.DATA_DE_VENDA)
            val posIdCliente = cursor.getColumnIndex(TabelaVendas.CAMPO_FK_CLIENTE)
            val posIdFuncionario = cursor.getColumnIndex(TabelaVendas.CAMPO_FK_FUNCIONARIO)

            val id = cursor.getLong(posId)
            //erro
            val dataVenda = cursor.getString(posDataVenda)
            val idCliente = cursor.getLong(posIdCliente)
            val idFuncionario = cursor.getLong(posIdFuncionario)

            return Venda(dataVenda,idCliente,idFuncionario, id)
        }
    }


}