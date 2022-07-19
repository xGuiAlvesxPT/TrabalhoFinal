package pt.ipg.trabalhofinal

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class ContentProviderLojaJogos : ContentProvider() {
    var dbOpenHelper : BDLojaOpenHelper? = null

    /**
     * Implement this to initialize your content provider on startup.
     * This method is called for all registered content providers on the
     * application main thread at application launch time.  It must not perform
     * lengthy operations, or application startup will be delayed.
     *
     *
     * You should defer nontrivial initialization (such as opening,
     * upgrading, and scanning databases) until the content provider is used
     * (via [.query], [.insert], etc).  Deferred initialization
     * keeps application startup fast, avoids unnecessary work if the provider
     * turns out not to be needed, and stops database errors (such as a full
     * disk) from halting application launch.
     *
     *
     * If you use SQLite, [android.database.sqlite.SQLiteOpenHelper]
     * is a helpful utility class that makes it easy to manage databases,
     * and will automatically defer opening until first use.  If you do use
     * SQLiteOpenHelper, make sure to avoid calling
     * [android.database.sqlite.SQLiteOpenHelper.getReadableDatabase] or
     * [android.database.sqlite.SQLiteOpenHelper.getWritableDatabase]
     * from this method.  (Instead, override
     * [android.database.sqlite.SQLiteOpenHelper.onOpen] to initialize the
     * database when it is first opened.)
     *
     * @return true if the provider was successfully loaded, false otherwise
     */
    override fun onCreate(): Boolean {
        dbOpenHelper = BDLojaOpenHelper(context)

        return true
    }

    /**
     * Implement this to handle query requests from clients.
     *
     *
     * Apps targeting [android.os.Build.VERSION_CODES.O] or higher should override
     * [.query] and provide a stub
     * implementation of this method.
     *
     *
     * This method can be called from multiple threads, as described in
     * [Processes
 * and Threads]({@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads).
     *
     *
     * Example client call:
     *
     *
     * <pre>// Request a specific record.
     * Cursor managedCursor = managedQuery(
     * ContentUris.withAppendedId(Contacts.People.CONTENT_URI, 2),
     * projection,    // Which columns to return.
     * null,          // WHERE clause.
     * null,          // WHERE clause value substitution
     * People.NAME + " ASC");   // Sort order.</pre>
     * Example implementation:
     *
     *
     * <pre>// SQLiteQueryBuilder is a helper class that creates the
     * // proper SQL syntax for us.
     * SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();
     *
     * // Set the table we're querying.
     * qBuilder.setTables(DATABASE_TABLE_NAME);
     *
     * // If the query ends in a specific record number, we're
     * // being asked for a specific record, so set the
     * // WHERE clause in our query.
     * if((URI_MATCHER.match(uri)) == SPECIFIC_MESSAGE){
     * qBuilder.appendWhere("_id=" + uri.getPathLeafId());
     * }
     *
     * // Make the query.
     * Cursor c = qBuilder.query(mDb,
     * projection,
     * selection,
     * selectionArgs,
     * groupBy,
     * having,
     * sortOrder);
     * c.setNotificationUri(getContext().getContentResolver(), uri);
     * return c;</pre>
     *
     * @param uri The URI to query. This will be the full URI sent by the client;
     * if the client is requesting a specific record, the URI will end in a record number
     * that the implementation should parse and add to a WHERE or HAVING clause, specifying
     * that _id value.
     * @param projection The list of columns to put into the cursor. If
     * `null` all columns are included.
     * @param selection A selection criteria to apply when filtering rows.
     * If `null` then all rows are included.
     * @param selectionArgs You may include ?s in selection, which will be replaced by
     * the values from selectionArgs, in order that they appear in the selection.
     * The values will be bound as Strings.
     * @param sortOrder How the rows in the cursor should be sorted.
     * If `null` then the provider is free to define the sort order.
     * @return a Cursor or `null`.
     */
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val db = dbOpenHelper!!.readableDatabase

        requireNotNull(projection)
        val colunas = projection as Array<String>

        val argsSeleccao = selectionArgs as Array<String>?

        val id = uri.lastPathSegment

        val cursor = when (getUriMatcher().match(uri)) {
            URI_CLIENTE -> TabelaClientes(db).query(colunas, selection, argsSeleccao, null, null, sortOrder)
            URI_FUNCIONARIO -> TabelaFuncionarios(db).query(colunas, selection, argsSeleccao, null, null, sortOrder)
            URI_PLATAFORMA -> TabelaPlataformas(db).query(colunas, selection, argsSeleccao, null, null, sortOrder)
            URI_SEXO -> TabelaSexo(db).query(colunas, selection, argsSeleccao, null, null, sortOrder)
            URI_LINHA_VENDA -> TabelaLinhaVenda(db).query(colunas, selection, argsSeleccao, null, null, sortOrder)
            URI_VENDA -> TabelaVendas(db).query(colunas, selection, argsSeleccao, null, null, sortOrder)
            URI_JOGO -> TabelaJogos(db).query(colunas, selection, argsSeleccao, null, null, sortOrder)



            URI_CLIENTE_ESPECIFICO -> TabelaClientes(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("${id}"), null, null, null)
            URI_FUNCIONARIO_ESPECIFICO -> TabelaFuncionarios(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("${id}"), null, null, null)
            URI_PLATAFORMA_ESPECIFICA -> TabelaPlataformas(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("${id}"), null, null, null)
            URI_SEXO_ESPECIFICO -> TabelaSexo(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("${id}"), null, null, null)
            URI_LINHA_VENDA_ESPECIFICO -> TabelaLinhaVenda(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("${id}"), null, null, null)
            URI_VENDA_ESPECIFICA -> TabelaVendas(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("${id}"), null, null, null)
            URI_JOGO_ESPECIFICO -> TabelaJogos(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("${id}"), null, null, null)

            else -> null
        }

        return cursor
    }



    /**
     * Implement this to handle requests for the MIME type of the data at the
     * given URI.  The returned MIME type should start with
     * `vnd.android.cursor.item` for a single record,
     * or `vnd.android.cursor.dir/` for multiple items.
     * This method can be called from multiple threads, as described in
     * [Processes
 * and Threads]({@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads).
     *
     *
     * Note that there are no permissions needed for an application to
     * access this information; if your content provider requires read and/or
     * write permissions, or is not exported, all applications can still call
     * this method regardless of their access permissions.  This allows them
     * to retrieve the MIME type for a URI when dispatching intents.
     *
     * @param uri the URI to query.
     * @return a MIME type string, or `null` if there is no type.
     */
    override fun getType(uri: Uri): String? =
        when (getUriMatcher().match(uri)) {

            URI_CLIENTE -> "$MULTIPLOS_REGISTOS/${TabelaClientes.NOME}"
            URI_FUNCIONARIO -> "$MULTIPLOS_REGISTOS/${TabelaFuncionarios.NOME}"
            URI_PLATAFORMA -> "$MULTIPLOS_REGISTOS/${TabelaPlataformas.NOME}"
            URI_JOGO -> "$MULTIPLOS_REGISTOS/${TabelaJogos.NOME}"
            URI_SEXO -> "$MULTIPLOS_REGISTOS/${TabelaSexo.NOME}"
            URI_VENDA -> "$MULTIPLOS_REGISTOS/${TabelaVendas.NOME}"
            URI_LINHA_VENDA -> "$MULTIPLOS_REGISTOS/${TabelaLinhaVenda.NOME}"

            URI_CLIENTE_ESPECIFICO -> "$UNICO_REGISTO/${TabelaClientes.NOME}"
            URI_FUNCIONARIO_ESPECIFICO -> "$UNICO_REGISTO/${TabelaFuncionarios.NOME}"
            URI_PLATAFORMA_ESPECIFICA -> "$UNICO_REGISTO/${TabelaPlataformas.NOME}"
            URI_JOGO_ESPECIFICO -> "$UNICO_REGISTO/${TabelaJogos.NOME}"
            URI_SEXO_ESPECIFICO -> "$UNICO_REGISTO/${TabelaSexo.NOME}"
            URI_VENDA_ESPECIFICA -> "$UNICO_REGISTO/${TabelaVendas.NOME}"
            URI_LINHA_VENDA_ESPECIFICO -> "$UNICO_REGISTO/${TabelaLinhaVenda.NOME}"

            else -> null
        }
    /**
     * Implement this to handle requests to insert a new row. As a courtesy,
     * call
     * [ notifyChange()][ContentResolver.notifyChange] after inserting. This method can be called from multiple
     * threads, as described in [Processes
 * and Threads](
      {@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads).
     *
     * @param uri The content:// URI of the insertion request.
     * @param values A set of column_name/value pairs to add to the database.
     * @return The URI for the newly inserted item.
     */
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbOpenHelper!!.writableDatabase

        requireNotNull(values)

        val id = when (getUriMatcher().match(uri)) {
            URI_CLIENTE -> TabelaClientes(db).insert(values)
            URI_FUNCIONARIO -> TabelaFuncionarios(db).insert(values)
            URI_PLATAFORMA -> TabelaPlataformas(db).insert(values)
            URI_JOGO -> TabelaJogos(db).insert(values)
            URI_VENDA -> TabelaVendas(db).insert(values)
            URI_LINHA_VENDA -> TabelaLinhaVenda(db).insert(values)
            URI_SEXO -> TabelaSexo(db).insert(values)

            else -> -1
        }

        db.close()

        if (id == -1L) return null

        return Uri.withAppendedPath(uri, "$id")
    }

    /**
     * Implement this to handle requests to delete one or more rows. The
     * implementation should apply the selection clause when performing
     * deletion, allowing the operation to affect multiple rows in a directory.
     * As a courtesy, call
     * [ notifyChange()][ContentResolver.notifyChange] after deleting. This method can be called from multiple
     * threads, as described in [Processes
 * and Threads](
      {@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads).
     *
     *
     * The implementation is responsible for parsing out a row ID at the end of
     * the URI, if a specific row is being deleted. That is, the client would
     * pass in `content://contacts/people/22` and the implementation
     * is responsible for parsing the record number (22) when creating a SQL
     * statement.
     *
     * @param uri The full URI to query, including a row ID (if a specific
     * record is requested).
     * @param selection An optional restriction to apply to rows when deleting.
     * @return The number of rows affected.
     * @throws SQLException
     */
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbOpenHelper!!.writableDatabase

        val id = uri.lastPathSegment

        val registosApagados = when (getUriMatcher().match(uri)) {
            URI_CLIENTE_ESPECIFICO -> TabelaClientes(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_FUNCIONARIO_ESPECIFICO -> TabelaFuncionarios(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_PLATAFORMA_ESPECIFICA -> TabelaPlataformas(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_SEXO_ESPECIFICO -> TabelaSexo(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_LINHA_VENDA_ESPECIFICO -> TabelaLinhaVenda(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_VENDA_ESPECIFICA -> TabelaVendas(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_JOGO_ESPECIFICO -> TabelaJogos(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            else -> 0
        }

        db.close()

        return registosApagados
    }

    /**
     * Implement this to handle requests to update one or more rows. The
     * implementation should update all rows matching the selection to set the
     * columns according to the provided values map. As a courtesy, call
     * [ notifyChange()][ContentResolver.notifyChange] after updating. This method can be called from multiple
     * threads, as described in [Processes
 * and Threads](
      {@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads).
     *
     * @param uri The URI to query. This can potentially have a record ID if
     * this is an update request for a specific record.
     * @param values A set of column_name/value pairs to update in the database.
     * @param selection An optional filter to match rows to update.
     * @return the number of rows affected.
     */
    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        requireNotNull(values)

        val db = dbOpenHelper!!.writableDatabase

        val id = uri.lastPathSegment

        val registosAlterados = when (getUriMatcher().match(uri)) {
            URI_CLIENTE_ESPECIFICO -> TabelaClientes(db).update(values,"${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_FUNCIONARIO_ESPECIFICO -> TabelaFuncionarios(db).update(values,"${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_PLATAFORMA_ESPECIFICA -> TabelaPlataformas(db).update(values,"${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_SEXO_ESPECIFICO -> TabelaSexo(db).update(values,"${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_LINHA_VENDA_ESPECIFICO -> TabelaLinhaVenda(db).update(values,"${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_VENDA_ESPECIFICA -> TabelaVendas(db).update(values,"${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_JOGO_ESPECIFICO -> TabelaJogos(db).update(values,"${BaseColumns._ID}=?", arrayOf("${id}"))
            else -> 0
        }

        db.close()

        return registosAlterados
    }

    companion object {
        private const val AUTHORITY = "pt.ipg.trabalhofinal"

        private const val URI_SEXO = 100
        private const val URI_SEXO_ESPECIFICO = 101

        private const val URI_CLIENTE = 200
        private const val URI_CLIENTE_ESPECIFICO = 201

        private const val URI_FUNCIONARIO = 300
        private const val URI_FUNCIONARIO_ESPECIFICO = 301

        private const val URI_GENERO = 400
        private const val URI_GENERO_ESPECIFICO = 401

        private const val URI_JOGO = 500
        private const val URI_JOGO_ESPECIFICO = 501

        private const val URI_LINHA_VENDA = 600
        private const val URI_LINHA_VENDA_ESPECIFICO = 601

        private  const val URI_PLATAFORMA = 700
        private const val URI_PLATAFORMA_ESPECIFICA = 701

        private const val URI_VENDA = 800
        private const val URI_VENDA_ESPECIFICA = 801

        private const val URI_PUBLICADORA = 900
        private const val URI_PUBLICADORA_ESPECIFICA = 901

        private const val UNICO_REGISTO = "vnd.android.cursor.item"
        private const val MULTIPLOS_REGISTOS = "vnd.android.cursor.dir"

        private val ENDERECO_BASE = Uri.parse("content://$AUTHORITY")
        val ENDERECO_FUNCIONARIOS = Uri.withAppendedPath(ENDERECO_BASE, TabelaFuncionarios.NOME)
        val ENDERECO_CLIENTES = Uri.withAppendedPath(ENDERECO_BASE, TabelaClientes.NOME)
        val ENDERECO_JOGOS = Uri.withAppendedPath(ENDERECO_BASE, TabelaJogos.NOME)
        val ENDERECO_VENDAS = Uri.withAppendedPath(ENDERECO_BASE, TabelaVendas.NOME)
        val ENDERECO_SEXO = Uri.withAppendedPath(ENDERECO_BASE, TabelaSexo.NOME)
        val ENDERECO_PLATAFORMAS = Uri.withAppendedPath(ENDERECO_BASE, TabelaPlataformas.NOME)

        fun getUriMatcher() : UriMatcher {
            var uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher.addURI(AUTHORITY, TabelaSexo.NOME, URI_SEXO)
            uriMatcher.addURI(AUTHORITY, "${TabelaSexo.NOME}/#", URI_SEXO_ESPECIFICO)

            uriMatcher.addURI(AUTHORITY, TabelaClientes.NOME, URI_CLIENTE)
            uriMatcher.addURI(AUTHORITY, "${TabelaClientes.NOME}/#", URI_CLIENTE_ESPECIFICO)

            uriMatcher.addURI(AUTHORITY, TabelaFuncionarios.NOME, URI_FUNCIONARIO)
            uriMatcher.addURI(AUTHORITY, "${TabelaFuncionarios.NOME}/#", URI_FUNCIONARIO_ESPECIFICO)


            uriMatcher.addURI(AUTHORITY, TabelaJogos.NOME, URI_JOGO)
            uriMatcher.addURI(AUTHORITY, "${TabelaJogos.NOME}/#", URI_JOGO_ESPECIFICO)

            uriMatcher.addURI(AUTHORITY, TabelaLinhaVenda.NOME, URI_LINHA_VENDA)
            uriMatcher.addURI(AUTHORITY, "${TabelaLinhaVenda.NOME}/#", URI_LINHA_VENDA_ESPECIFICO)

            uriMatcher.addURI(AUTHORITY, TabelaPlataformas.NOME, URI_PLATAFORMA)
            uriMatcher.addURI(AUTHORITY, "${TabelaPlataformas.NOME}/#", URI_PLATAFORMA_ESPECIFICA)

            uriMatcher.addURI(AUTHORITY, TabelaVendas.NOME, URI_VENDA)
            uriMatcher.addURI(AUTHORITY, "${TabelaVendas.NOME}/#", URI_VENDA_ESPECIFICA)



            return uriMatcher
        }
    }
}