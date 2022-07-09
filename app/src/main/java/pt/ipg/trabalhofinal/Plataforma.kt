package pt.ipg.trabalhofinal
import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable
import java.util.*

data class Plataforma(
    var nome: String,
    var id: Long = -1
) :Serializable {

    fun toContentValues():ContentValues{

        val valores = ContentValues()
        valores.put(TabelaPlataformas.NOME_PLATAFORMA,nome)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Plataforma {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaPlataformas.NOME_PLATAFORMA)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)

            return Plataforma(nome, id)
        }
    }


}