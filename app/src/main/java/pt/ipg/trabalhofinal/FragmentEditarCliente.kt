package pt.ipg.trabalhofinal

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.trabalhofinal.databinding.FragmentEditarClienteBinding


class FragmentEditarCliente : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentEditarClienteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var cliente: Cliente? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarClienteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_editar

        if (arguments != null) {
            cliente = FragmentEditarClienteArgs.fromBundle(arguments!!).cliente
            if (cliente != null) {
                binding.editTextNomeCliente.setText(cliente!!.nome)
                binding.editTextNifCliente.setText(cliente!!.nif)
                binding.editTextContatoCliente.setText(cliente!!.contacto)
                binding.editTextDataNasCliente.setText(cliente!!.data_de_nascimento)
            }
        }

        LoaderManager.getInstance(this).initLoader(ID_LOADER_SEXO, null, this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ID_LOADER_SEXO = 0
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(
            requireContext(),
            ContentProviderLojaJogos.ENDERECO_SEXO,
            TabelaSexo.TODAS_COLUNAS,
            null,
            null,
            TabelaSexo.NOME_SEXO
        )

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is *not* allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
     *
     *
     * This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     *
     *  *
     *
     *The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a [android.database.Cursor]
     * and you place it in a [android.widget.CursorAdapter], use
     * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
     * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
     * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     *  *  The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a [android.database.Cursor] from a [android.content.CursorLoader],
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * [android.widget.CursorAdapter], you should use the
     * [android.widget.CursorAdapter.swapCursor]
     * method so that the old Cursor is not closed.
     *
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        binding.spinnerSexo.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaSexo.NOME_SEXO),
            intArrayOf(android.R.id.text1),
            0
        )

        mostraSexoSelecionadoSpinner()
    }

    private fun mostraSexoSelecionadoSpinner() {
        if (cliente == null) return

        val idSexo = cliente!!.sexo.id

        val ultimoSexo = binding.spinnerSexo.count - 1
        for (i in 0..ultimoSexo) {
            if (idSexo == binding.spinnerSexo.getItemIdAtPosition(i)) {
                binding.spinnerSexo.setSelection(i)
                return
            }
        }
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    override fun onLoaderReset(loader: Loader<Cursor>) {
        if (_binding == null) return
        binding.spinnerSexo.adapter = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.action_eliminar -> {
                guardar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaClientes()
                true
            }
            else -> false
        }

    private fun voltaListaClientes() {
        findNavController().navigate(R.id.action_fragment_editar_cliente_to_fragmentVerClientes)
    }

    private fun guardar() {
        val nomeCliente = binding.editTextNomeCliente.text.toString()
        if (nomeCliente.isBlank()) {
            binding.editTextNomeCliente.error = "Nome Obrigatorio"
            binding.editTextNomeCliente.requestFocus()
            return
        }

        val nifCliente = binding.editTextNifCliente.text.toString()
        if (nifCliente.isBlank()) {
            binding.editTextNifCliente.error = "Nif Obrigatorio"
            binding.editTextNifCliente.requestFocus()
            return
        }

        val contatoCliente = binding.editTextContatoCliente.text.toString()
        if (contatoCliente.isBlank()) {
            binding.editTextContatoCliente.error = "Contato Obrigatorio"
            binding.editTextContatoCliente.requestFocus()
            return
        }

        val datanascCliente = binding.editTextDataNasCliente.text.toString()
        if (datanascCliente.isBlank()) {
            binding.editTextDataNasCliente.error = "Data de Nascimento Obrigatoria"
            binding.editTextDataNasCliente.requestFocus()
            return
        }

        val idSexo = binding.spinnerSexo.selectedItemId
        if (idSexo == Spinner.INVALID_ROW_ID) {
            binding.textViewSexo.error = "Sexo Obrigatorio"
            binding.spinnerSexo.requestFocus()
            return
        }

        if (cliente == null) {
            insereCliente(nomeCliente, nifCliente, contatoCliente, datanascCliente, idSexo)
        } else {
            alteraCliente(nomeCliente, nifCliente, contatoCliente, datanascCliente, idSexo)
        }
    }


    private fun alteraCliente(
        nomeCliente: String,
        nifCliente: String,
        contatoCliente: String,
        datanascCliente: String,
        idSexo: Long
    ) {
        val enderecoLivro =
            Uri.withAppendedPath(ContentProviderLojaJogos.ENDERECO_CLIENTES, "${cliente!!.id}")

        val cliente = Cliente(
            nomeCliente,
            nifCliente,
            contatoCliente,
            datanascCliente,
            Sexo("", idSexo)
        )

        val registosAlterados = requireActivity().contentResolver.update(
            enderecoLivro,
            cliente.toContentValues(),
            null,
            null
        )

        if (registosAlterados == 1) {
            Toast.makeText(requireContext(), "Cliente alterado com sucesso", Toast.LENGTH_LONG)
                .show()
            voltaListaClientes()
        } else {
            Snackbar.make(
                binding.editTextNomeCliente,
                "Erro ao atualizar cliente",
                Snackbar.LENGTH_INDEFINITE
            ).show()
        }
    }

    private fun insereCliente(
        nomeCliente: String,
        nifCliente: String,
        contatoCliente: String,
        datanascCliente: String,
        idSexo: Long
    ) {
        val cliente = Cliente(
            nomeCliente,
            nifCliente,
            contatoCliente,
            datanascCliente,
            Sexo("", idSexo)
        )

        val endereco = requireActivity().contentResolver.insert(
            ContentProviderLojaJogos.ENDERECO_CLIENTES,
            cliente.toContentValues()
        )

        if (endereco != null) {
            Toast.makeText(requireContext(), "Cliente Inserido com sucesso", Toast.LENGTH_LONG)
                .show()
            voltaListaClientes()
        } else {
            Snackbar.make(
                binding.editTextNomeCliente,
                "Erro ao inserir Client",
                Snackbar.LENGTH_INDEFINITE
            ).show()
        }
    }
}