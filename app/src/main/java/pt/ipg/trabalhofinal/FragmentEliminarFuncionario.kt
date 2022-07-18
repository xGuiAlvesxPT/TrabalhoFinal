package pt.ipg.trabalhofinal

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.trabalhofinal.databinding.FragmentEliminarFuncionarioBinding
import java.text.SimpleDateFormat


class FragmentEliminarFuncionario : Fragment() {
    private var _binding: FragmentEliminarFuncionarioBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var funcionario: Funcionario

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarFuncionarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_eliminar

        funcionario = FragmentEliminarFuncionarioArgs.fromBundle(arguments!!).funcionario

        binding.textViewNomeFuncionarioEli.text = funcionario.nome
        binding.textViewNifFuncionarioEli.text = funcionario.nif
        binding.textViewContatoFuncionarioEli.text = funcionario.contacto
        val dateFormat = SimpleDateFormat("dd-MM-yyy")
        val dataNasc = funcionario.data_de_nascimento
        val data = dateFormat.format(dataNasc)
        binding.textViewDataNascFuncionarioEli.text = data
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_eliminar -> {
                confirmaEliminaFuncionario()
                true
            }
            R.id.action_cancelar -> {
                voltaListaFuncionarios()
                true
            }
            else -> false
        }
    }

    private fun confirmaEliminaFuncionario() {
        val alert = AlertDialog.Builder(requireContext())
        alert.setTitle("Apagar Funcionario")
        alert.setMessage("Deseja apagar o funcionario?")
        alert.setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialog, which ->  })
        alert.setPositiveButton(R.string.eliminar, DialogInterface.OnClickListener { dialog, which -> eliminaFuncionario() })
        alert.show()
    }

    private fun eliminaFuncionario() {
        val enderecoFuncionarioApagar = Uri.withAppendedPath(ContentProviderLojaJogos.ENDERECO_FUNCIONARIOS, "${funcionario.id}")

        val registosEliminados = requireActivity().contentResolver.delete(enderecoFuncionarioApagar, null, null)

        if (registosEliminados == 1) {
            Toast.makeText(requireContext(), "Funcionario eliminado com sucesso", Toast.LENGTH_LONG).show()
            voltaListaFuncionarios()
        } else {
            Snackbar.make(binding.textViewNomeFuncionarioEli, "Erro ao eliminar funcionario", Snackbar.LENGTH_INDEFINITE).show()
        }
    }

    private fun voltaListaFuncionarios() {
        val acao =
            FragmentEliminarFuncionarioDirections.actionFragmentEliminarFuncionarioToFragmentverfuncionarios()
        findNavController().navigate(acao)
    }
}
