package pt.ipg.trabalhofinal
import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable
import java.util.*

data class Funcionario(
    var nome: String,
    var nif: String,
    var contacto: String,
    var data_de_nascimento: Long,
    var id: Long = -1

) : Serializable {

    fun toContentValues():ContentValues{

        val valores = ContentValues()
        valores.put(TabelaFuncionarios.NOME_FUNCIONARIO,nome)
        valores.put(TabelaFuncionarios.NIF_FUNCIONARIO,nif)
        valores.put(TabelaFuncionarios.CONTACTO_FUNCIONARIO,contacto)
        valores.put(TabelaFuncionarios.DATA_DE_NASCIMENTO_FUNCIONARIO,data_de_nascimento)
        return valores
    }


    companion object {
        fun fromCursor(cursor: Cursor): Funcionario {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaFuncionarios.NOME_FUNCIONARIO)
            val posNif = cursor.getColumnIndex(TabelaFuncionarios.NIF_FUNCIONARIO)
            val posContacto = cursor.getColumnIndex(TabelaFuncionarios.CONTACTO_FUNCIONARIO)
            val posDataNascimento = cursor.getColumnIndex(TabelaFuncionarios.DATA_DE_NASCIMENTO_FUNCIONARIO)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)
            val nif = cursor.getString(posNif)
            val contacto = cursor.getString(posContacto)
            val dataNascimento = cursor.getLong(posDataNascimento)

            return Funcionario(nome,nif,contacto,dataNascimento, id)
        }
    }



}