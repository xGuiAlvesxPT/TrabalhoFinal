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
import pt.ipg.trabalhofinal.databinding.FragmentEliminarJogoBinding


class FragmentEliminarJogo : Fragment() {

    private lateinit var jogo: Jogo
    private var _binding: FragmentEliminarJogoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarJogoBinding.inflate(inflater, container, false)
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

        jogo = FragmentEliminarJogoArgs.fromBundle(arguments!!).jogo

        binding.textViewNomeJogoEli.text = jogo.nome
        binding.textViewPrecoJogoEli.text = jogo.preco.toString()
        binding.textViewGeneroJogoEli.text = jogo.genero
        binding.textViewPublicadoraJogoEli.text = jogo.publicadora
        binding.textViewDataLanJogoEli.text = jogo.data_de_lancamento
        binding.textViewPlataformaJogoEli.text = jogo.plataforma.nome
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_eliminar -> {
                confirmaEliminaJogo()
                true
            }
            R.id.action_cancelar -> {
                voltaListaJogos()
                true
            }
            else -> false
        }
    }

    private fun confirmaEliminaJogo() {
        val alert = AlertDialog.Builder(requireContext())
        alert.setTitle("Apagar jogo")
        alert.setMessage("Deseja apagar o jogo?")
        alert.setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialog, which ->  })
        alert.setPositiveButton(R.string.eliminar, DialogInterface.OnClickListener { dialog, which -> eliminaJogo() })
        alert.show()
    }

    private fun eliminaJogo() {
        val enderecoJogoApagar = Uri.withAppendedPath(ContentProviderLojaJogos.ENDERECO_JOGOS, "${jogo.id}")

        val registosEliminados = requireActivity().contentResolver.delete(enderecoJogoApagar, null, null)

        if (registosEliminados == 1) {
            Toast.makeText(requireContext(), "Jogo eliminado com sucesso", Toast.LENGTH_LONG).show()
            voltaListaJogos()
        } else {
            Snackbar.make(binding.textViewNomeJogoEli, "Erro ao eliminar jogo", Snackbar.LENGTH_INDEFINITE).show()
        }
    }

    private fun voltaListaJogos() {
        val acao = FragmentEliminarJogoDirections.actionFragmentEliminarJogoToFragmentVerJogos()
        findNavController().navigate(acao)
    }
}