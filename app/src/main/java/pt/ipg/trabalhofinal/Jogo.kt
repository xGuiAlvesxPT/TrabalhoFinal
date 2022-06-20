package pt.ipg.trabalhofinal
import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Jogo(

    var nome: String,
    var preco: Float,
    var data_de_lancamento: Date ,
    var idplataforma: Long,
    var idgenero: Long,
    var idpublicadora: Long,
    var id: Long = -1,

    ) {

    fun toContentValues():ContentValues{

        val valores = ContentValues()
        valores.put(TabelaJogos.NOME_JOGO,nome)
        valores.put(TabelaJogos.PRECO,preco)
        valores.put(TabelaJogos.DATA_DE_LANCAMENTO,data_de_lancamento.toString())
        valores.put(TabelaJogos.CAMPO_FK_PLATAFORMA,idplataforma)
        valores.put(TabelaJogos.CAMPO_FK_GENERO,idgenero)
        valores.put(TabelaJogos.CAMPO_FK_PUBLICADORA,idpublicadora)
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Jogo {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaJogos.NOME_JOGO)
            val posPreco = cursor.getColumnIndex(TabelaJogos.PRECO)
            val posDataLancamento = cursor.getColumnIndex(TabelaJogos.DATA_DE_LANCAMENTO)
            val posIdPlataforma = cursor.getColumnIndex(TabelaJogos.CAMPO_FK_PLATAFORMA)
            val posIdGenero = cursor.getColumnIndex(TabelaJogos.CAMPO_FK_GENERO)
            val posIdPublicadora = cursor.getColumnIndex(TabelaJogos.CAMPO_FK_PUBLICADORA)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)
            val preco = cursor.getFloat(posPreco)
            val dataLancamento = cursor.getLong(posDataLancamento)
            val idPlataforma = cursor.getLong(posIdPlataforma)
            val idGenero = cursor.getLong(posIdGenero)
            val idPublicadora = cursor.getLong(posIdPublicadora)

            return Jogo(nome,preco,Date(dataLancamento),idPlataforma,idGenero,idPublicadora,id)
        }
    }

}