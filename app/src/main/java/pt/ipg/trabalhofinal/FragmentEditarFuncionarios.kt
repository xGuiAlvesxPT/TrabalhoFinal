package pt.ipg.trabalhofinal

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
        findNavController().navigate(R.id.action_fragmentEditarFuncionarios_to_fragmentverfuncionarios)
    }

    private fun guardar() {

        val nomeFuncionario = binding.editTextNomeFuncionario.text.toString()
        if (nomeFuncionario.isBlank()) {
            binding.editTextNomeFuncionario.error = "Nome Obrigatorio"
            binding.editTextNomeFuncionario.requestFocus()
            return
        }

        val nifFuncionario = binding.editTextNifFuncionario.text.toString()
        if (nifFuncionario.isBlank()) {
            binding.editTextNifFuncionario.error = "Nif Obrigatorio"
            binding.editTextNifFuncionario.requestFocus()
            return
        }

        val contatoFuncionario = binding.editTextContatoFuncionario.text.toString()
        if (contatoFuncionario.isBlank()) {
            binding.editTextContatoFuncionario.error = "Contato Obrigatorio"
            binding.editTextContatoFuncionario.requestFocus()
            return
        }

        val datanascFuncionario = binding.editTextDataNasFuncionario.text.toString()
        if (datanascFuncionario.isBlank()) {
            binding.editTextDataNasFuncionario.error = "Data de Nascimento Obrigatoria"
            binding.editTextDataNasFuncionario.requestFocus()
            return
        }

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
                "Erro ao inserir Funcionario",
                Snackbar.LENGTH_INDEFINITE
            ).show()
        }
    }




}