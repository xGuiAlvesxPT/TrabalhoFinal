package pt.ipg.trabalhofinal

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Publicadora(
    var nome: String,
    var id: Long = -1
) {

    fun toContentValues(): ContentValues {

        val valores = ContentValues()
        valores.put(TabelaPublicadora.NOME_PUBLICADORA,nome)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Publicadora {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaPublicadora.NOME_PUBLICADORA)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)

            return Publicadora(nome, id)
        }
    }






}