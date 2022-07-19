package pt.ipg.trabalhofinal
import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Jogo(

    var nome: String,
    var preco: Double,
    var data_de_lancamento: Long,
    var plataforma: Plataforma,
    var genero: Genero,
    var publicadora: Publicadora,

    var id: Long = -1,

    ) : Serializable {

    fun toContentValues():ContentValues{

        val valores = ContentValues()
        valores.put(TabelaJogos.NOME_JOGO,nome)
        valores.put(TabelaJogos.PRECO,preco)
        valores.put(TabelaJogos.DATA_DE_LANCAMENTO,data_de_lancamento)
        valores.put(TabelaJogos.CAMPO_FK_PLATAFORMA,plataforma.id)
        valores.put(TabelaJogos.CAMPO_FK_GENERO,genero.id)
        valores.put(TabelaJogos.CAMPO_FK_PUBLICADORA,publicadora.id)

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

            val posNomeGenero =  cursor.getColumnIndex(TabelaGeneros.NOME_GENERO)
            val posNomePlataformas=  cursor.getColumnIndex(TabelaPlataformas.NOME_PLATAFORMA)
            val posNomePublicadora =  cursor.getColumnIndex(TabelaPublicadora.NOME_PUBLICADORA)


            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)
            val preco = cursor.getDouble(posPreco)
            val dataLancamento = cursor.getLong(posDataLancamento)

            val idPlataforma = cursor.getLong(posIdPlataforma)
            val nomePlataforma = cursor.getString(posNomePlataformas)
            val Plataforma = Plataforma(nomePlataforma, idPlataforma)

            val idGenero = cursor.getLong(posIdGenero)
            val nomeGenero = cursor.getString(posNomeGenero)
            val Genero = Genero(nomeGenero, idGenero)

            val idPublicadora = cursor.getLong(posIdPublicadora)
            val nomePublicadora = cursor.getString(posNomePublicadora)
            val Publicadora = Publicadora(nomePublicadora, idPublicadora)

            return Jogo(nome,preco,dataLancamento,Plataforma,Genero,Publicadora,id)

        }
    }

}