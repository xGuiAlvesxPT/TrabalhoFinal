package pt.ipg.trabalhofinal
import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

class LinhaVenda(
    var quantidade: Int,
    var preco: Float,
    var idjogo: Long,
    var idvenda: Long,
    var id: Long = -1

) {

    fun toContentValues():ContentValues{

        val valores = ContentValues()
        valores.put(TabelaLinhaVenda.QUANTIDADE,quantidade)
        valores.put(TabelaLinhaVenda.PRECO,preco)
        valores.put(TabelaLinhaVenda.CAMPO_FK_JOGOS,idjogo)
        valores.put(TabelaLinhaVenda.CAMPO_FK_VENDA,idvenda)
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): LinhaVenda {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posQuantidade = cursor.getColumnIndex(TabelaLinhaVenda.QUANTIDADE)
            val posPreco = cursor.getColumnIndex(TabelaLinhaVenda.PRECO)
            val posIdJogo = cursor.getColumnIndex(TabelaLinhaVenda.CAMPO_FK_JOGOS)
            val posIdVenda = cursor.getColumnIndex(TabelaLinhaVenda.CAMPO_FK_VENDA)

            val id = cursor.getLong(posId)
            val quantidade = cursor.getInt(posQuantidade)
            val preco = cursor.getFloat(posPreco)
            val idJogo = cursor.getLong(posIdJogo)
            val idVenda = cursor.getLong(posIdVenda)

            return LinhaVenda(quantidade,preco,idJogo,idVenda, id)
        }
    }
}