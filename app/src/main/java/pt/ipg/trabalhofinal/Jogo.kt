package pt.ipg.trabalhofinal
import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Jogo(

    var nome: String,
    var preco: String,
    var genero: String,
    var publicadora: String,
    var data_de_lancamento: String,
    var plataforma: Plataforma,

    var id: Long = -1,

    ) : Serializable {

    fun toContentValues():ContentValues{

        val valores = ContentValues()
        valores.put(TabelaJogos.NOME_JOGO,nome)
        valores.put(TabelaJogos.PRECO,preco)
        valores.put(TabelaJogos.GENERO,genero)
        valores.put(TabelaJogos.PUBLICADORA,publicadora)
        valores.put(TabelaJogos.DATA_DE_LANCAMENTO,data_de_lancamento)
        valores.put(TabelaJogos.CAMPO_FK_PLATAFORMA,plataforma.id)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Jogo {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaJogos.NOME_JOGO)
            val posPreco = cursor.getColumnIndex(TabelaJogos.PRECO)
            val posGenero = cursor.getColumnIndex(TabelaJogos.GENERO)
            val posPublicadora = cursor.getColumnIndex(TabelaJogos.PUBLICADORA)
            val posDataLancamento = cursor.getColumnIndex(TabelaJogos.DATA_DE_LANCAMENTO)
            val posIdPlataforma = cursor.getColumnIndex(TabelaJogos.CAMPO_FK_PLATAFORMA)


            val posNomePlataformas=  cursor.getColumnIndex(TabelaPlataformas.NOME_PLATAFORMA)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)
            val preco = cursor.getString(posPreco)
            val genero = cursor.getString(posGenero)
            val publicadora = cursor.getString(posPublicadora)
            val dataLancamento = cursor.getString(posDataLancamento)

            val idPlataforma = cursor.getLong(posIdPlataforma)
            val nomePlataforma = cursor.getString(posNomePlataformas)
            val Plataforma = Plataforma(nomePlataforma, idPlataforma)



            return Jogo(nome,preco,genero,publicadora,dataLancamento,Plataforma,id)
        }
    }

}