package pt.ipg.trabalhofinal

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.trabalhofinal.databinding.FragmentEditarFuncionariosBinding


class FragmentEditarFuncionarios : Fragment() {
    private var _binding: FragmentEditarFuncionariosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var funcionario: Funcionario? = null

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
                binding.editTextDataNasFuncionario.setText(funcionario!!.data_de_nascimento)
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
        findNavController().popBackStack()
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

        val datanascFuncionario = binding.editTextDataNasFuncionario.text.toString()
        if (datanascFuncionario.isBlank()) {
            binding.editTextDataNasFuncionario.error = getString(R.string.dataNascObrigatoria)
            binding.editTextDataNasFuncionario.requestFocus()
            return
        }

        if (funcionario == null) {
            insereFuncionario(nomeFuncionario, nifFuncionario, contatoFuncionario, datanascFuncionario)
        } else {
            alteraCliente(nomeFuncionario, nifFuncionario, contatoFuncionario, datanascFuncionario)
        }
    }

    private fun alteraCliente(
        nomeFuncionario: String,
        nifFuncionario: String,
        contatoFuncionario: String,
        datanascFuncionario: String

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
        datanascFuncionario: String
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




