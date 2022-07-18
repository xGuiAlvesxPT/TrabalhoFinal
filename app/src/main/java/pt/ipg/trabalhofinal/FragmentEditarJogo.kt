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
import pt.ipg.trabalhofinal.databinding.FragmentEditarJogoBinding


class FragmentEditarJogo : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentEditarJogoBinding? = null

    private var jogo: Jogo? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarJogoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoaderManager.getInstance(this).initLoader(ID_LOADER_PLATAFORMAS, null, this)

        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_editar

        if (arguments != null) {
            jogo = FragmentEditarJogoArgs.fromBundle(arguments!!).jogo
            if (jogo != null) {
                binding.EditTextNomeJogo.setText(jogo!!.nome)
                binding.EditTextPreco.setText(jogo!!.preco.toString())
                binding.EditTextGenero.setText(jogo!!.genero)
                binding.EditTextPublicadora.setText(jogo!!.publicadora)
                binding.EditTextDataLancamento.setText(jogo!!.data_de_lancamento)
            }
        }

        LoaderManager.getInstance(this).initLoader(ID_LOADER_PLATAFORMAS, null, this)
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_eliminar -> {
                guardar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaJogos()
                true
            }
            else -> false
        }
    }

    private fun voltaListaJogos() {
        findNavController().navigate(R.id.action_fragmentEditarJogo_to_fragmentVerJogos)
    }

    private fun guardar() {
        val nomeJogo = binding.EditTextNomeJogo.text.toString()
        if (nomeJogo.isBlank()) {
            binding.EditTextNomeJogo.error = getString(R.string.nomeObrigatorio)
            binding.EditTextNomeJogo.requestFocus()
            return
        }

        val preco = binding.EditTextPreco.text.toString()
        if (preco.isBlank()) {
            binding.EditTextPreco.error = getString(R.string.precoObrigatorio)
            binding.EditTextPreco.requestFocus()
            return
        }

        val genero = binding.EditTextGenero.text.toString()
        if (genero.isBlank()) {
            binding.EditTextGenero.error = getString(R.string.generoObrigatorio)
            binding.EditTextGenero.requestFocus()
            return
        }

        val publicadora = binding.EditTextPublicadora.text.toString()
        if (publicadora.isBlank()) {
            binding.EditTextPublicadora.error = getString(R.string.publicadoraObrigatoria)
            binding.EditTextPublicadora.requestFocus()
            return
        }

        val dataLancamento = binding.EditTextDataLancamento.text.toString()
        if (dataLancamento.isBlank()) {
            binding.EditTextDataLancamento.error = getString(R.string.DataLancObrigatoria)
            binding.EditTextDataLancamento.requestFocus()
            return
        }

        val idPlataforma = binding.spinnerPlataformas.selectedItemId
        if (idPlataforma == Spinner.INVALID_ROW_ID) {
            binding.textViewPlataforma.error = getString(R.string.plataformaObrigatoria)
            binding.spinnerPlataformas.requestFocus()
            return
        }
        if (jogo == null) {
            insereJogo(nomeJogo, preco,genero,publicadora,dataLancamento, idPlataforma)
        } else {
            alteraJogo(nomeJogo, preco,genero,publicadora,dataLancamento, idPlataforma)
        }
    }

    private fun alteraJogo(nomeJogo: String, preco: String,genero:String,publicadora:String,dataLancamento:String, idPlataforma: Long) {

        val enderecoJogo = Uri.withAppendedPath(ContentProviderLojaJogos.ENDERECO_JOGOS, "${jogo!!.id}")


        val jogo = Jogo(
            nomeJogo,
            preco.toDouble()  ,
            genero,
            publicadora,
            dataLancamento,
            Plataforma("", idPlataforma)
        )

        val registosAlterados = requireActivity().contentResolver.update(
            enderecoJogo,
            jogo.toContentValues(),
            null,
            null
        )

        if (registosAlterados == 1) {
            Toast.makeText(requireContext(), getString(R.string.jogoAtualizadoSucesso), Toast.LENGTH_LONG).show()
            voltaListaJogos()
        } else {
            Snackbar.make(
                binding.EditTextNomeJogo,
                "Erro ao atualizar jogo",
                Snackbar.LENGTH_INDEFINITE
            ).show()
        }
    }

    private fun insereJogo(nomeJogo: String, preco: String,genero:String,publicadora:String,dataLancamento:String, idPlataforma: Long){
        val jogo = Jogo(
            nomeJogo,
            preco.toDouble()  ,
            genero,
            publicadora,
            dataLancamento,
            Plataforma("", idPlataforma)
        )

        val endereco = requireActivity().contentResolver.insert(
            ContentProviderLojaJogos.ENDERECO_JOGOS,
            jogo.toContentValues()
        )

        if (endereco != null) {
            Toast.makeText(requireContext(), "Jogo inserido com sucesso", Toast.LENGTH_LONG).show()
            voltaListaJogos()
        } else {
            Snackbar.make(
                binding.EditTextNomeJogo,
                "Erro ao inserir jogo",
                Snackbar.LENGTH_INDEFINITE
            ).show()
        }
    }

    companion object {
        const val ID_LOADER_PLATAFORMAS = 0
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
            ContentProviderLojaJogos.ENDERECO_PLATAFORMAS,
            TabelaPlataformas.TODAS_COLUNAS,
            null,
            null,
            TabelaPlataformas.NOME_PLATAFORMA
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
        binding.spinnerPlataformas.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaPlataformas.NOME_PLATAFORMA),
            intArrayOf(android.R.id.text1),
            0
        )
        mostraPlataformaSelecionadaSpinner()
    }

    private fun mostraPlataformaSelecionadaSpinner() {
        if (jogo == null) return

        val idPlataforma = jogo!!.plataforma.id

        val ultimaPlataforma = binding.spinnerPlataformas.count - 1
        for (i in 0..ultimaPlataforma) {
            if (idPlataforma == binding.spinnerPlataformas.getItemIdAtPosition(i)) {
                binding.spinnerPlataformas.setSelection(i)
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
        binding.spinnerPlataformas.adapter = null
    }
}