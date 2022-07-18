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
import pt.ipg.trabalhofinal.databinding.FragmentEliminarClienteBinding
import java.text.SimpleDateFormat


class FragmentEliminarCliente : Fragment() {
    private var _binding: FragmentEliminarClienteBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var cliente: Cliente

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarClienteBinding.inflate(inflater, container, false)
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

        cliente = FragmentEliminarClienteArgs.fromBundle(arguments!!).cliente

        binding.textViewNomeClienteEli.text = cliente.nome
        binding.textViewSexoClienteEli.text = cliente.sexo.nomeSexo
        binding.textViewNifClienteEli.text = cliente.nif
        binding.textViewContatoClienteEli.text = cliente.contacto
        val dateFormat = SimpleDateFormat("dd-MM-yyy")
        val dataNasc = cliente.data_de_nascimento
        val data = dateFormat.format(dataNasc)
        binding.textViewDataNascClienteEli.text = data
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_eliminar -> {
                confirmaEliminaCliente()
                true
            }
            R.id.action_cancelar -> {
                voltaListaClientes()
                true
            }
            else -> false
        }
    }

    private fun confirmaEliminaCliente() {
        val alert = AlertDialog.Builder(requireContext())
        alert.setTitle("Apagar Cliente")
        alert.setMessage("Deseja apagar o cliente?")
        alert.setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialog, which ->  })
        alert.setPositiveButton(R.string.eliminar, DialogInterface.OnClickListener { dialog, which -> eliminaCliente() })
        alert.show()
    }

    private fun eliminaCliente() {
        val enderecoClienteApagar = Uri.withAppendedPath(ContentProviderLojaJogos.ENDERECO_CLIENTES, "${cliente.id}")

        val registosEliminados = requireActivity().contentResolver.delete(enderecoClienteApagar, null, null)

        if (registosEliminados == 1) {
            Toast.makeText(requireContext(), "Cliente eliminado com sucesso", Toast.LENGTH_LONG).show()
            voltaListaClientes()
        } else {
            Snackbar.make(binding.textViewNomeClienteEli, "Erro ao eliminar Cliente", Snackbar.LENGTH_INDEFINITE).show()
        }
    }

    private fun voltaListaClientes() {
        val acao = FragmentEliminarClienteDirections.actionFragmentEliminarClienteToFragmentVerClientes()
        findNavController().navigate(acao)
    }
}