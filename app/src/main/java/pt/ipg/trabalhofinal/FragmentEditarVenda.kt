package pt.ipg.trabalhofinal

import android.database.Cursor
import android.icu.text.SimpleDateFormat
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
import pt.ipg.trabalhofinal.databinding.FragmentEditarVendaBinding
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [FragmentEditarVenda.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentEditarVenda : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var loader: CursorLoader? = null
    private var _binding: FragmentEditarVendaBinding? = null
    private var venda: Venda? = null
    var dataVenda: Long = 0L
    var data: String = ""

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarVendaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_editar

        LoaderManager.getInstance(this).initLoader(ID_LOADER_JOGO, null, this)
        LoaderManager.getInstance(this).initLoader(ID_LOADER_CLIENTE, null, this)
        LoaderManager.getInstance(this).initLoader(ID_LOADER_FUNCIONARIO, null, this)

        if (arguments != null) {
          //  venda = FragmentEditarVendaArgs.fromBundle(arguments!!).venda
            if (venda != null) {
                binding.textViewNovaVenda.setText("ATUALIZAR JOGO")
                binding.EditTextQuantidade.setText(venda!!.quantidade.toString())
                binding.EditTextPrecoVenda.setText(venda!!.precoVenda.toString())

                val getData = venda!!.data_de_venda
                val dateFormat = SimpleDateFormat("dd-MM-yyy")
                val dataFormatada = dateFormat.format(getData)
                val dateSplit = dataFormatada.split("-")
                val ano = dateSplit[2]
                val mes = dateSplit[1]
                val dia = dateSplit[0]
                val currentDate = Calendar.getInstance()
                binding.datePickerDataVenda.init(
                    ano.toInt(),
                    mes.toInt() - 1,
                    dia.toInt()
                ) { view, ano, mes, dia ->
                    currentDate.set(ano, mes, dia)
                    dataVenda = currentDate.timeInMillis
                }
            } else {
                val picker = binding.datePickerDataVenda
                val currentDate = Calendar.getInstance()
                picker.init(
                    currentDate.get(Calendar.YEAR),
                    currentDate.get(Calendar.MONTH),
                    currentDate.get(Calendar.DAY_OF_MONTH)

                ) { view, ano, mes, dia ->
                    //val mes = mes
                    currentDate.set(ano, mes, dia)
                    dataVenda = currentDate.timeInMillis
                }
            }

        }

    }


    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_eliminar -> {
                guardar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaVendas()
                true
            }
            else -> false
        }
    }

    companion object {
        const val ID_LOADER_JOGO = 0
        const val ID_LOADER_CLIENTE = 1
        const val ID_LOADER_FUNCIONARIO = 2
    }

    private fun voltaListaVendas() {
        findNavController().navigate(R.id.action_fragmentEditarVenda_to_fragmentVerVendas)
    }

    private fun guardar() {
        val quantidade = binding.EditTextQuantidade.text.toString()
        if (quantidade.isBlank()) {
            binding.EditTextQuantidade.error = getString(R.string.quantidadeObrigatoria)
            binding.EditTextQuantidade.requestFocus()
            return
        }

        val preco = binding.EditTextPrecoVenda.text.toString()
        if (preco.isBlank()) {
            binding.EditTextPrecoVenda.error = getString(R.string.precoObrigatorioo)
            binding.EditTextPrecoVenda.requestFocus()
            return
        }

        val idFuncionarios = binding.spinnerVendaFuncionario.selectedItemId
        if (idFuncionarios == Spinner.INVALID_ROW_ID) {
            binding.textViewfun.error = getString(R.string.funObrigatorio)
            binding.spinnerVendaFuncionario.requestFocus()
            return
        }

        val idCliente = binding.spinnerVendaCliente.selectedItemId
        if (idCliente == Spinner.INVALID_ROW_ID) {
            binding.textViewVendaClientee.error = getString(R.string.clienteObrigatorio)
            binding.spinnerVendaCliente.requestFocus()
            return
        }

        val idJogo= binding.spinnerNomeJogo.selectedItemId
        if (idJogo == Spinner.INVALID_ROW_ID) {
            binding.textViewJogo.error = getString(R.string.jogoObrigatorio)
            binding.spinnerNomeJogo.requestFocus()
            return
        }

        val venda = Venda(
            quantidade.toLong(),preco.toDouble(), dataVenda,Cliente("","","",0,Sexo("",idCliente))
            , Funcionario("",",","",0,idFuncionarios),Sexo(""),Jogo("",0.0,"","",0,
                Plataforma(""),idJogo
            ), Plataforma(""))


        val endereco = requireActivity().contentResolver.insert(
            ContentProviderLojaJogos.ENDERECO_VENDAS,
            venda.toContentValues()
        )

        if (endereco != null) {
            Toast.makeText(requireContext(), "Venda inserida com sucesso", Toast.LENGTH_LONG).show()
            voltaListaVendas()
        } else {
            Snackbar.make(binding.EditTextPrecoVenda, "Erro ao inserir venda", Snackbar.LENGTH_INDEFINITE).show()
        }
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
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        if (id == ID_LOADER_JOGO) {
        loader =    CursorLoader(
                requireContext(),
                ContentProviderLojaJogos.ENDERECO_JOGOS,
                TabelaJogos.TODAS_COLUNAS,
                null,
                null,
                TabelaJogos.NOME_JOGO
            )
        }else if (id == ID_LOADER_CLIENTE) {
         loader =    CursorLoader(
                requireContext(),
                ContentProviderLojaJogos.ENDERECO_CLIENTES,
                TabelaClientes.TODAS_COLUNAS,
                null,
                null,
                TabelaClientes.NOME_CLIENTE
            )
        }else if (id == ID_LOADER_FUNCIONARIO) {
        loader = CursorLoader(
            requireContext(),
            ContentProviderLojaJogos.ENDERECO_FUNCIONARIOS,
            TabelaFuncionarios.TODAS_COLUNAS,
            null,
            null,
            TabelaFuncionarios.NOME_FUNCIONARIO
        )
    }
        return loader as CursorLoader

    }
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

        if (loader.id == ID_LOADER_CLIENTE) {
            binding.spinnerVendaCliente.adapter = SimpleCursorAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                data, arrayOf(TabelaClientes.NOME_CLIENTE),
                intArrayOf(android.R.id.text1), 0
            )
        } else if (loader.id == ID_LOADER_FUNCIONARIO) {
            binding.spinnerVendaFuncionario.adapter = SimpleCursorAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                data, arrayOf(TabelaFuncionarios.NOME_FUNCIONARIO),
                intArrayOf(android.R.id.text1), 0
            )
        }else if (loader.id == ID_LOADER_JOGO) {
            binding.spinnerNomeJogo.adapter = SimpleCursorAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                data, arrayOf(TabelaJogos.NOME_JOGO),
                intArrayOf(android.R.id.text1), 0
            )
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
        binding.spinnerVendaCliente.adapter = null
        binding.spinnerVendaFuncionario.adapter = null
        binding.spinnerNomeJogo.adapter = null
    }
}


