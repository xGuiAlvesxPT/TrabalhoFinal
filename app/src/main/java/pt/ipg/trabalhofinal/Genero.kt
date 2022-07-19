package pt.ipg.trabalhofinal
import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Genero(
    var nome: String,
    var id: Long = -1
) {

    fun toContentValues():ContentValues{

        val valores = ContentValues()
        valores.put(TabelaGeneros.NOME_GENERO,nome)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Genero {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaGeneros.NOME_GENERO)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)

            return Genero(nome, id)
        }
    }

}
