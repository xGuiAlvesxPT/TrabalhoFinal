package pt.ipg.trabalhofinal

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Sexo(
    var nome: String,
    var id: Long = -1
) {

    fun toContentValues(): ContentValues {

        val valores = ContentValues()
        valores.put(TabelaSexo.NOME_SEXO,nome)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Sexo {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaSexo.NOME_SEXO)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)

            return Sexo(nome, id)
        }
    }

}