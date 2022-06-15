package pt.ipg.trabalhofinal
import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Jogo(

    var nome: String,
    var publicadora: String,
    var preco: Float,
    var data_de_lancamento: String ,
    var idplataforma: Long,
    var idgenero: Long,
    var id: Long = -1,

    ) {

    fun toContentValues():ContentValues{

        val valores = ContentValues()
        valores.put(TabelaJogos.NOME_JOGO,nome)
        valores.put(TabelaJogos.PUBLICADORA,publicadora)
        valores.put(TabelaJogos.PRECO,preco)
        valores.put(TabelaJogos.DATA_DE_LANCAMENTO,data_de_lancamento)
        valores.put(TabelaJogos.CAMPO_FK_PLATAFORMA,idplataforma)
        valores.put(TabelaJogos.CAMPO_FK_GENERO,idgenero)
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Jogo {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaJogos.NOME_JOGO)
            val posPublicadora = cursor.getColumnIndex(TabelaJogos.PUBLICADORA)
            val posPreco = cursor.getColumnIndex(TabelaJogos.PRECO)
            val posDataLancamento = cursor.getColumnIndex(TabelaJogos.DATA_DE_LANCAMENTO)
            val posIdPlataforma = cursor.getColumnIndex(TabelaJogos.CAMPO_FK_PLATAFORMA)
            val posIdGenero = cursor.getColumnIndex(TabelaJogos.CAMPO_FK_GENERO)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)
            val publicadora = cursor.getString(posPublicadora)
            val preco = cursor.getFloat(posPreco)
            val dataLancamento = cursor.getString(posDataLancamento)
            val idPlataforma = cursor.getLong(posIdPlataforma)
            val idGenero = cursor.getLong(posIdGenero)

            return Jogo(nome, publicadora, preco,dataLancamento,idPlataforma,idGenero, id)
        }
    }

}