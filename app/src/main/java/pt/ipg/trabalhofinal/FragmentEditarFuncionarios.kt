package pt.ipg.trabalhofinal

import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.trabalhofinal.databinding.FragmentEditarFuncionariosBinding
import java.util.*


class FragmentEditarFuncionarios : Fragment() {
    private var _binding: FragmentEditarFuncionariosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var funcionario: Funcionario? = null
    var dataNasc: Long = 0L
    var data: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarFuncionariosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_editar


        if (arguments != null) {
            funcionario = FragmentEditarFuncionariosArgs.fromBundle(arguments!!).funcionario
            if (funcionario != null) {
                binding.textViewNovoFuncionario.setText("ATUALIZAR FUNCIONARIO")
                binding.editTextNomeFuncionario.setText(funcionario!!.nome)
                binding.editTextNifFuncionario.setText(funcionario!!.nif)
                binding.editTextContatoFuncionario.setText(funcionario!!.contacto)
                val getData = funcionario!!.data_de_nascimento
                val dateFormat = SimpleDateFormat("dd-MM-yyy")
                val dataFormatada = dateFormat.format(getData)
                val dateSplit = dataFormatada.split("-")
                val ano = dateSplit[2]
                val mes = dateSplit[1]
                val dia = dateSplit[0]
                val currentDate = Calendar.getInstance()
                binding.datePickerFunc.init(
                    ano.toInt(),
                    mes.toInt() - 1,
                    dia.toInt()
                ) { view, ano, mes, dia ->
                    currentDate.set(ano, mes, dia)
                    dataNasc = currentDate.timeInMillis
                }
            } else {
                val picker = binding.datePickerFunc
                val currentDate = Calendar.getInstance()
                picker.init(
                    currentDate.get(Calendar.YEAR),
                    currentDate.get(Calendar.MONTH),
                    currentDate.get(Calendar.DAY_OF_MONTH)

                ) { view, ano, mes, dia ->
                    //val mes = mes
                    currentDate.set(ano, mes, dia)
                    dataNasc = currentDate.timeInMillis
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
                voltaListaFuncionarios()
                true
            }
            else -> false
        }
    }

    private fun voltaListaFuncionarios() {
        findNavController().navigate(R.id.action_fragmentEditarFuncionarios_to_fragmentverfuncionarios)
    }

    private fun guardar() {

        val nomeFuncionario = binding.editTextNomeFuncionario.text.toString()
        if (nomeFuncionario.isBlank()) {
            binding.editTextNomeFuncionario.error = getString(R.string.nomeObrigatorio)
            binding.editTextNomeFuncionario.requestFocus()
            return
        }

        val nifFuncionario = binding.editTextNifFuncionario.text.toString()
        if (nifFuncionario.isBlank()) {
            binding.editTextNifFuncionario.error = getString(R.string.nifObrigatorio)
            binding.editTextNifFuncionario.requestFocus()
            return
        }

        val contatoFuncionario = binding.editTextContatoFuncionario.text.toString()
        if (contatoFuncionario.isBlank()) {
            binding.editTextContatoFuncionario.error = getString(R.string.contatoObrigatorio)
            binding.editTextContatoFuncionario.requestFocus()
            return
        }



        if (funcionario == null) {
            insereFuncionario(nomeFuncionario, nifFuncionario, contatoFuncionario, dataNasc)
        } else {
            alteraCliente(nomeFuncionario, nifFuncionario, contatoFuncionario, dataNasc)
        }
    }

    private fun alteraCliente(
        nomeFuncionario: String,
        nifFuncionario: String,
        contatoFuncionario: String,
        datanascFuncionario: Long

    ) {

        val enderecoFuncionario =
            Uri.withAppendedPath(ContentProviderLojaJogos.ENDERECO_FUNCIONARIOS, "${funcionario!!.id}")

        val funcionario = Funcionario(
            nomeFuncionario,
            nifFuncionario,
            contatoFuncionario,
            datanascFuncionario,

        )

        val registosAlterados = requireActivity().contentResolver.update(
            enderecoFuncionario,
            funcionario.toContentValues(),
            null,
            null
        )

        if (registosAlterados == 1) {
            Toast.makeText(requireContext(), "Funcionario alterado com sucesso", Toast.LENGTH_LONG)
                .show()
            voltaListaFuncionarios()
        } else {
            Snackbar.make(
                binding.editTextNomeFuncionario,
                "Erro ao atualizar funcionario",
                Snackbar.LENGTH_INDEFINITE
            ).show()
        }
    }

    private fun insereFuncionario(
        nomeFuncionario: String,
        nifFuncionario: String,
        contatoFuncionario: String,
        datanascFuncionario: Long
    ) {
        val funcionario = Funcionario(
            nomeFuncionario,
            nifFuncionario,
            contatoFuncionario,
            datanascFuncionario,

            )

        val endereco = requireActivity().contentResolver.insert(
            ContentProviderLojaJogos.ENDERECO_FUNCIONARIOS,
            funcionario.toContentValues()
        )

        if (endereco != null) {
            Toast.makeText(requireContext(), "Funcionario Inserido com sucesso", Toast.LENGTH_LONG)
                .show()
            voltaListaFuncionarios()
        } else {
            Snackbar.make(
                binding.editTextNomeFuncionario,
                "Erro ao inserir Client",
                Snackbar.LENGTH_INDEFINITE
            ).show()
        }
    }
}




